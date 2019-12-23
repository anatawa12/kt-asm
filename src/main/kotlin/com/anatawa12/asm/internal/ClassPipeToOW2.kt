package com.anatawa12.asm.internal

import com.anatawa12.asm.*
import org.objectweb.asm.ClassVisitor as ClassVisitorOW2

/**
 * Created by anatawa12 on 2019/12/23.
 */
internal open class ClassPipeToOW2(
    val ow2: ClassVisitorOW2
) : ClassVisitor {
    override fun visit(
        version: ClassVersion,
        access: ClassAccessFlags,
        name: Type,
        signature: Signature?,
        superName: Type?,
        interfaces: List<Type>
    ) {
        ow2.visit(
            version.id.toInt(),
            access.flags.toInt(),
            name.internalName,
            signature?.sig,
            superName?.internalName,
            interfaces.mapArray { it.internalName }
        )
    }

    override fun visitSource(source: String?, debug: String?) {
        ow2.visitSource(source, debug)
    }

    override fun visitModule(name: String, flags: ModuleFlags, version: String?): ModuleVisitor? {
        return ow2.visitModule(name, flags.flags.toInt(), version)?.let(::ModulePipeToOW2)
    }

    override fun visitNestHost(nestHost: Type) {
        ow2.visitNestHost(nestHost.internalName)
    }

    override fun visitOuterClass(owner: Type, name: String?, descriptor: MethodType?) {
        ow2.visitOuterClass(owner.internalName, name, descriptor?.descriptor)
    }

    override fun visitAnnotation(descriptor: Type, visible: Boolean): AnnotationVisitor? {
        return ow2.visitAnnotation(descriptor.descriptor, visible)?.let(::AnnotationPipeToOW2)
    }

    override fun visitTypeAnnotation(
        typeRef: TypeAnnotationTarget,
        typePath: TypePath,
        descriptor: Type,
        visible: Boolean
    ): AnnotationVisitor? {
        return ow2.visitTypeAnnotation(typeRef.ow2, typePath.ow2, descriptor.descriptor, visible)
            ?.let(::AnnotationPipeToOW2)
    }

    override fun visitAttribute(attribute: Attribute) {
        return ow2.visitAttribute(attribute.ow2)
    }

    override fun visitNestMember(nestMember: Type) {
        ow2.visitNestMember(nestMember.internalName)
    }

    override fun visitInnerClass(name: Type, outerName: Type?, innerName: String?, access: InnerClassAccessFlags) {
        ow2.visitInnerClass(name.internalName, outerName?.internalName, innerName, access.flags.toInt())
    }

    override fun visitField(
        access: FieldAccessFlags,
        name: String,
        descriptor: Type,
        signature: Signature?,
        value: Any?
    ): FieldVisitor {
        return ow2.visitField(access.flags.toInt(), name, descriptor.descriptor, signature?.sig, value)
            .let(::FieldPipeToOW2)
    }

    override fun visitMethod(
        access: MethodAccessFlags,
        name: String,
        descriptor: MethodType,
        signature: Signature?,
        exceptions: List<Type>
    ): MethodVisitor {
        return ow2.visitMethod(
            access.flags.toInt(),
            name,
            descriptor.descriptor,
            signature?.sig,
            exceptions.mapArray { it.internalName }).let(::MethodPipeToOW2)
    }

    override fun visitEnd() {
        return ow2.visitEnd()
    }
}
