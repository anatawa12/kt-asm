package com.anatawa12.asm

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by anatawa12 on 2019/12/22.
 */
class TypeTest {
    @Test
    fun getKind() {
        assertEquals(Type.Kind.Class, Type("Ljava/lang/String;").kind)
        assertEquals(Type.Kind.Array, Type("[Ljava/lang/String;").kind)
        assertEquals(Type.Kind.Primitive, Type("I").kind)
        assertEquals(Type.Kind.Primitive, Type("V").kind)
    }

    @Test
    fun getInternalName() {
        assertEquals("java/lang/String", Type("Ljava/lang/String;").internalName)
        assertEquals("[Ljava/lang/String;", Type("[Ljava/lang/String;").internalName)
        assertEquals("I", Type("I").internalName)
    }

    @Test
    fun getElementType() {
        assertEquals(Type("Ljava/lang/String;"), Type("[Ljava/lang/String;").elementType)
        assertEquals(Type("[Ljava/lang/String;"), Type("[[Ljava/lang/String;").elementType)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getElementTypeClass() {
        Type("Ljava/lang/String;").elementType
    }

    @Test(expected = IllegalArgumentException::class)
    fun getElementTypePrimitive() {
        Type("V").elementType
    }

    @Test
    fun getDimensions() {
        assertEquals(0, Type("Ljava/lang/String;").dimensions)
        assertEquals(1, Type("[Ljava/lang/String;").dimensions)
        assertEquals(2, Type("[[Ljava/lang/String;").dimensions)
    }

    @Test
    fun getBottomElementType() {
        assertEquals(Type("Ljava/lang/String;"), Type("Ljava/lang/String;").bottomElementType)
        assertEquals(Type("Ljava/lang/String;"), Type("[Ljava/lang/String;").bottomElementType)
        assertEquals(Type("Ljava/lang/String;"), Type("[[Ljava/lang/String;").bottomElementType)
    }

    @Test
    fun getSize() {
        assertEquals(0, Type("V").size)
        assertEquals(1, Type("Ljava/lang/String;").size)
        assertEquals(1, Type("[Ljava/lang/String;").size)
        assertEquals(1, Type("I").size)
        assertEquals(2, Type("J").size)
        assertEquals(2, Type("D").size)
    }
}
