package com.anatawa12.asm

import com.anatawa12.asm.internal.DescriptorVerifier

class Type(val descriptor: String) {
    init {
        require(DescriptorVerifier(descriptor).verifyType()) { "invalid descriptor: $descriptor" }
    }

    val kind: Kind
        get() = when (descriptor[0]) {
            '[' -> Kind.Array
            'L' -> Kind.Class
            else -> Kind.Primitive
        }

    val internalName: InternalName
        get() = when (kind) {
            Kind.Class -> descriptor.run { substring(1, length - 1) }
            else -> descriptor
        }

    val elementType: Type
        get() {
            require(kind == Kind.Array) { "this is not array" }
            return Type(descriptor.substring(1))
        }

    val dimensions: Int
        get() {
            for (dimension in descriptor.indices) {
                if (descriptor[dimension] != '[') return dimension
            }
            throw AssertionError()
        }

    val bottomElementType: Type
        get() = Type(descriptor.substring(dimensions))

    val size: Int
        get() = when (descriptor[0]) {
            'V' -> 0
            'D', 'J' -> 2
            else -> 1
        }

    enum class Kind {
        Class,
        Primitive,
        Array
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Type

        if (descriptor != other.descriptor) return false

        return true
    }

    override fun hashCode(): Int {
        return descriptor.hashCode()
    }

    override fun toString(): String = descriptor

    companion object {
        val Void = Type("V")
        val Boolean = Type("Z")
        val Char = Type("C")
        val Byte = Type("B")
        val Short = Type("S")
        val Int = Type("I")
        val Float = Type("F")
        val Long = Type("J")
        val Double = Type("D")

        fun fromInternalName(internalName: String) =
            if (internalName[0] == '[') Type(internalName) else Type("L$internalName;")

        fun Type.array() = Type("[$descriptor")
    }
}
