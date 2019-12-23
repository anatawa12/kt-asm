package com.anatawa12.asm.internal

import com.anatawa12.asm.FieldVisitor
import com.anatawa12.asm.Type
import com.anatawa12.asm.TypeAnnotationTarget
import com.anatawa12.asm.TypePath
import org.objectweb.asm.Opcodes
import org.objectweb.asm.TypeReference
import org.objectweb.asm.AnnotationVisitor as AnnotationVisitorOW2
import org.objectweb.asm.Attribute as AttributeOW2
import org.objectweb.asm.FieldVisitor as FieldVisitorOW2

/**
 * Created by anatawa12 on 2019/12/23.
 */
class FieldPipeFromOW2(
    val fieldVisitor: FieldVisitor
) : FieldVisitorOW2(Opcodes.ASM7) {
    override fun visitAnnotation(descriptor: String, visible: Boolean): AnnotationVisitorOW2? {
        return fieldVisitor.visitAnnotation(Type(descriptor), visible)?.let(::AnnotationPipeFromOW2)
    }

    override fun visitTypeAnnotation(
        typeRef: Int,
        typePath: org.objectweb.asm.TypePath,
        descriptor: String,
        visible: Boolean
    ): AnnotationVisitorOW2? {
        return fieldVisitor.visitTypeAnnotation(
            TypeAnnotationTarget.fromASM(TypeReference(typeRef)),
            TypePath.fromASM(typePath),
            Type(descriptor),
            visible
        )?.let(::AnnotationPipeFromOW2)
    }

    override fun visitAttribute(attribute: AttributeOW2) {
        // NOT SUPPORTED
    }

    override fun visitEnd() {
        fieldVisitor.visitEnd()
    }
}
