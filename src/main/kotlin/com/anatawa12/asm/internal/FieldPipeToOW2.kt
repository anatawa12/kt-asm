package com.anatawa12.asm.internal

import com.anatawa12.asm.*
import org.objectweb.asm.FieldVisitor as FieldVisitorOW2

/**
 * Created by anatawa12 on 2019/12/23.
 */
internal class FieldPipeToOW2(
    val ow2: FieldVisitorOW2
) : FieldVisitor {
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

    override fun visitEnd() {
        return ow2.visitEnd()
    }
}
