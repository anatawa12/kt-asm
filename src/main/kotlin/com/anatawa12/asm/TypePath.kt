@file:Suppress("EXPERIMENTAL_UNSIGNED_LITERALS")

package com.anatawa12.asm

import org.objectweb.asm.TypePath as TypePathOW2

/**
 * Created by anatawa12 on 2019/12/21.
 */
/**
 * the type path. see [jvm specification 4.7.20.2](https://docs.oracle.com/javase/specs/jvms/se13/html/jvms-4.html#jvms-4.7.20.2)
 */
class TypePath(
    val elements: List<TypePathElement>
) {
    internal val ow2: TypePathOW2 = TypePathOW2.fromString(elementsToString())

    private fun elementsToString(): String = elements.joinToString("") { elem ->
        when (elem.kind) {
            TypePathKind.InArray -> "["
            TypePathKind.InNested -> "."
            TypePathKind.BoundOfWildcard -> "*"
            TypePathKind.ParameterizedType -> "${elem.index};"
        }
    }

    override fun toString(): String = elementsToString()
}

class TypePathElement private constructor(
    val kind: TypePathKind,
    val index: UByte
) {
    companion object {
        private val array = TypePathElement(TypePathKind.InArray, 0u)
        private val nested = TypePathElement(TypePathKind.InNested, 0u)
        private val bound = TypePathElement(TypePathKind.BoundOfWildcard, 0u)
        fun array() = array
        fun nested() = nested
        fun bound() = bound
        fun typeArgument(index: UByte) = TypePathElement(TypePathKind.ParameterizedType, index)
    }
}

enum class TypePathKind {
    InArray,
    InNested,
    BoundOfWildcard,
    ParameterizedType,
}
