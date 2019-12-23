package com.anatawa12.asm.internal

import com.anatawa12.asm.AnnotationVisitor
import com.anatawa12.asm.Type
import org.objectweb.asm.AnnotationVisitor as AnnotationVisitorOW2

/**
 * Created by anatawa12 on 2019/12/23.
 */
internal class AnnotationPipeToOW2(
    private val ow2: AnnotationVisitorOW2
) : AnnotationVisitor {

    override fun visit(name: String, value: Any) {
        ow2.visit(name, value)
    }

    override fun visitEnum(name: String, descriptor: Type, value: String) {
        ow2.visitEnum(name, descriptor.descriptor, value)
    }

    override fun visitAnnotation(name: String, descriptor: Type): AnnotationVisitor? {
        return ow2.visitAnnotation(name, descriptor.descriptor)?.let(::AnnotationPipeToOW2)
    }

    override fun visitArray(name: String): AnnotationVisitor? {
        return ow2.visitArray(name)?.let(::AnnotationPipeToOW2)
    }

    override fun visitEnd() {
        ow2.visitEnd()
    }
}
