package com.anatawa12.asm.internal

import com.anatawa12.asm.*
import org.objectweb.asm.Opcodes
import org.objectweb.asm.TypeReference
import org.objectweb.asm.AnnotationVisitor as AnnotationVisitorOW2
import org.objectweb.asm.Attribute as AttributeOW2
import org.objectweb.asm.ClassVisitor as ClassVisitorOW2
import org.objectweb.asm.FieldVisitor as FieldVisitorOW2
import org.objectweb.asm.MethodVisitor as MethodVisitorOW2
import org.objectweb.asm.ModuleVisitor as ModuleVisitorOW2
import org.objectweb.asm.TypePath as TypePathOW2

/**
 * Created by anatawa12 on 2019/12/23.
 */

internal class ClassPipeFromOW2(private val classVisitor: ClassVisitor) : ClassVisitorOW2(Opcodes.ASM7) {
    override fun visit(
        version: Int,
        access: Int,
        name: String,
        signature: String?,
        superName: String?,
        vararg interfaces: String
    ) {
        classVisitor.visit(
            ClassVersion(version.toUInt()),
            ClassAccessFlags(access.toUShort()),
            Type.objectFromInternalName(name),
            signature?.let(::Signature),
            superName?.let(Type.Companion::objectFromInternalName),
            interfaces.map(Type.Companion::objectFromInternalName)
        )
    }

    override fun visitModule(name: String, flags: Int, version: String?): ModuleVisitorOW2? {
        return classVisitor.visitModule(name, ModuleFlags(flags.toUShort()), version)?.let(::ModulePipeFromOW2)
    }

    override fun visitNestHost(nestHost: String) {
        classVisitor.visitNestHost(Type.objectFromInternalName(nestHost))
    }

    override fun visitOuterClass(owner: String, name: String?, descriptor: String?) {
        classVisitor.visitOuterClass(Type.objectFromInternalName(owner), name, descriptor?.let(::MethodType))
    }

    override fun visitAnnotation(descriptor: String, visible: Boolean): AnnotationVisitorOW2? {
        return classVisitor.visitAnnotation(Type(descriptor), visible)?.let(::AnnotationPipeFromOW2)
    }

    override fun visitTypeAnnotation(
        typeRef: Int,
        typePath: TypePathOW2,
        descriptor: String,
        visible: Boolean
    ): AnnotationVisitorOW2? {
        return classVisitor.visitTypeAnnotation(
            TypeAnnotationTarget.fromASM(TypeReference(typeRef)),
            TypePath.fromASM(typePath),
            Type(descriptor),
            visible
        )?.let(::AnnotationPipeFromOW2)
    }

    override fun visitAttribute(attribute: AttributeOW2) {
        // NOT SUPPORTED
    }

    override fun visitNestMember(nestMember: String) {
        classVisitor.visitNestMember(Type.objectFromInternalName(nestMember))
    }

    override fun visitInnerClass(name: String, outerName: String?, innerName: String?, access: Int) {
        classVisitor.visitInnerClass(
            Type.objectFromInternalName(name),
            outerName?.let(Type.Companion::objectFromInternalName),
            innerName,
            InnerClassAccessFlags(access.toUShort())
        )
    }

    override fun visitField(
        access: Int,
        name: String,
        descriptor: String,
        signature: String?,
        value: Any?
    ): FieldVisitorOW2 {
        return classVisitor.visitField(
            FieldAccessFlags(access.toUShort()),
            name,
            Type(descriptor),
            signature?.let(::Signature),
            value
        ).let(::FieldPipeFromOW2)
    }

    override fun visitMethod(
        access: Int,
        name: String,
        descriptor: String,
        signature: String?,
        exceptions: Array<out String>
    ): MethodVisitorOW2 {
        return classVisitor.visitMethod(
            MethodAccessFlags(access.toUShort()),
            name,
            MethodType(descriptor),
            signature?.let(::Signature),
            exceptions.map { Type(it) }
        ).let(::MethodPipeFromOW2)
    }
}
