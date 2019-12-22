package com.anatawa12.asm

class Type(val descriptor: Descriptor) {
    val kind: Kind
        get() = when (descriptor.descriptor[0]) {
            '[' -> Kind.Array
            'L' -> Kind.Class
            else -> Kind.Primitive
        }
    val internalName: InternalName
        get() = when (kind) {
            Kind.Class -> InternalName(descriptor.descriptor.run { substring(1, length - 1) })
            else -> InternalName(descriptor.descriptor)
        }

    val elementType: Type
        get() {
            require(kind == Kind.Array) { "this is not array" }
            return Type(Descriptor(descriptor.descriptor.substring(1)))
        }

    val dimensions: Int
        get() {
            for (dimension in descriptor.descriptor.indices) {
                if (descriptor.descriptor[dimension] != '[') return dimension
            }
            return descriptor.descriptor.lastIndex
        }

    val bottomElementType: Type
        get() = Type(Descriptor(descriptor.descriptor.substring(dimensions)))

    val size: Int
        get() = when (descriptor.descriptor[0]) {
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
}
