package com.anatawa12.asm

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by anatawa12 on 2019/12/22.
 */
class MethodTypeTest {
    @Test
    fun getReturnType() {
        assertEquals(Type("V"), MethodType("()V").returnType)
        assertEquals(Type("V"), MethodType("(Ljava/lang/String;)V").returnType)
        assertEquals(Type("Ljava/lang/String;"), MethodType("()Ljava/lang/String;").returnType)
        assertEquals(Type("Ljava/lang/String;"), MethodType("(Ljava/lang/String;)Ljava/lang/String;").returnType)
    }

    @Test
    fun getArgumentTypes() {
        assertEquals(listOf<Type>(), MethodType("()V").argumentTypes.toList())
        assertEquals(
            listOf<Type>(Type("Ljava/lang/String;")),
            MethodType("(Ljava/lang/String;)V").argumentTypes.toList()
        )
        assertEquals(listOf<Type>(), MethodType("()Ljava/lang/String;").argumentTypes.toList())
        assertEquals(
            listOf<Type>(Type("Ljava/lang/String;")),
            MethodType("(Ljava/lang/String;)Ljava/lang/String;").argumentTypes.toList()
        )
        assertEquals(listOf<Type>(Type("I")), MethodType("(I)Ljava/lang/String;").argumentTypes.toList())
    }
}
