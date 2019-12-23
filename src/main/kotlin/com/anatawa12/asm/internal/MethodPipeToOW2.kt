package com.anatawa12.asm.internal

import com.anatawa12.asm.*
import org.objectweb.asm.MethodVisitor as MethodVisitorOW2

/**
 * Created by anatawa12 on 2019/12/23.
 */
internal class MethodPipeToOW2(
    val ow2: MethodVisitorOW2
) : MethodVisitor {
    override fun visitParameter(name: String, access: AccessFlags) {
        return ow2.visitParameter(name, access.flags.toInt())
    }

    override fun visitAnnotationDefault(): AnnotationVisitor? {
        return ow2.visitAnnotationDefault()?.let(::AnnotationPipeToOW2)
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

    override fun visitAnnotableParameterCount(parameterCount: Int, visible: Boolean) {
        return ow2.visitAnnotableParameterCount(parameterCount, visible)
    }

    override fun visitParameterAnnotation(parameter: Int, descriptor: Type, visible: Boolean): AnnotationVisitor? {
        return ow2.visitParameterAnnotation(parameter, descriptor.descriptor, visible)?.let(::AnnotationPipeToOW2)
    }

    override fun visitAttribute(attribute: Attribute) {
        return ow2.visitAttribute(attribute.ow2)
    }

    override fun visitCode(): CodeVisitor? {
        ow2.visitCode()
        return CodePipeToOW2(ow2)
    }

    override fun visitEnd() {
        return ow2.visitEnd()
    }
}
