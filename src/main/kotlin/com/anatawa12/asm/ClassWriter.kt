package com.anatawa12.asm

import com.anatawa12.asm.internal.ClassPipeToOW2
import org.objectweb.asm.ClassWriter as ClassWriterOW2

/**
 * Created by anatawa12 on 2019/12/23.
 */
class ClassWriter private constructor(private val writer: ClassWriterOW2) : ClassVisitor by ClassPipeToOW2(writer) {
    constructor() : this(ClassWriterOW2(0))
}
