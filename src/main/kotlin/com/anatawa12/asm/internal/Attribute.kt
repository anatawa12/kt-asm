package com.anatawa12.asm.internal

import com.anatawa12.asm.Attribute
import org.objectweb.asm.ByteVector
import org.objectweb.asm.ClassWriter

/**
 * Created by anatawa12 on 2019/12/23.
 */

internal val Attribute.ow2: org.objectweb.asm.Attribute?
    get() = object : org.objectweb.asm.Attribute(type) {
        override fun write(
            classWriter: ClassWriter?,
            code: ByteArray?,
            codeLength: Int,
            maxStack: Int,
            maxLocals: Int
        ): ByteVector {
            return ByteVector(content.size).apply { putByteArray(content, 0, content.size) }
        }
    }
