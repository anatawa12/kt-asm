package com.anatawa12.asm

import com.anatawa12.asm.internal.DescriptorVerifier

class MethodType(val descriptor: String) {
    init {
        require(DescriptorVerifier(descriptor).verifyMethod()) { "invalid descriptor: $descriptor" }
    }

    val returnType: Type
        get() = Type(descriptor.substring(descriptor.indexOf(')') + 1))

    val argumentTypes: Sequence<Type>
        get() = sequence {
            val descriptor = descriptor
            var index = 1
            while (true) {
                when (descriptor[index]) {
                    in descriptor -> {
                        yield(Type(descriptor.substring(index, index)))
                    }
                    'L' -> {
                        val start = index
                        index = descriptor.indexOf(';', index)
                        yield(Type(descriptor.substring(start, index)))
                    }
                    ')' -> return@sequence
                    else -> throw AssertionError()
                }
                index++
            }
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MethodType

        if (descriptor != other.descriptor) return false

        return true
    }

    override fun hashCode(): Int {
        return descriptor.hashCode()
    }

    override fun toString(): String = descriptor
}
