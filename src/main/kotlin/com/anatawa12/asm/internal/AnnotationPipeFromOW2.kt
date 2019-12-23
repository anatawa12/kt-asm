package com.anatawa12.asm.internal

import com.anatawa12.asm.AnnotationVisitor
import com.anatawa12.asm.Type
import org.objectweb.asm.Opcodes
import org.objectweb.asm.AnnotationVisitor as AnnotationVisitorOW2

/**
 * Created by anatawa12 on 2019/12/23.
 */
class AnnotationPipeFromOW2(
    val annotationVisitor: AnnotationVisitor
) : AnnotationVisitorOW2(Opcodes.ASM7) {
    override fun visit(name: String?, value: Any) {
        annotationVisitor.visit(name.orEmpty(), value)
    }

    override fun visitEnum(name: String?, descriptor: String, value: String) {
        annotationVisitor.visitEnum(name.orEmpty(), Type(descriptor), value)
    }

    override fun visitAnnotation(name: String?, descriptor: String): AnnotationVisitorOW2? {
        return annotationVisitor.visitAnnotation(name.orEmpty(), Type(descriptor))?.let(::AnnotationPipeFromOW2)
    }

    override fun visitArray(name: String?): AnnotationVisitorOW2? {
        return annotationVisitor.visitArray(name.orEmpty())?.let(::AnnotationPipeFromOW2)
    }

    override fun visitEnd() {
        annotationVisitor.visitEnd()
    }
}
