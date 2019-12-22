package com.anatawa12.asm

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by anatawa12 on 2019/12/22.
 */
class MethodTypeTest {
    private val getReturnTypeTests = listOf<Pair<Type, MethodType>>(
        Type("V") to MethodType("()V"),
        Type("V") to MethodType("(Ljava/lang/String;)V"),
        Type("Ljava/lang/String;") to MethodType("()Ljava/lang/String;"),
        Type("Ljava/lang/String;") to MethodType("(Ljava/lang/String;)Ljava/lang/String;")
    )

    @Test
    fun getReturnType() {
        for ((type, methodType) in getReturnTypeTests) {
            assertEquals(type, methodType.returnType)
        }
    }

    private val getArgumentTypesTests = listOf<Pair<List<Type>, MethodType>>(
        listOf<Type>() to MethodType("()V"),
        listOf<Type>(Type("Ljava/lang/String;")) to MethodType("(Ljava/lang/String;)V"),
        listOf<Type>() to MethodType("()Ljava/lang/String;"),
        listOf<Type>(Type("Ljava/lang/String;")) to MethodType("(Ljava/lang/String;)Ljava/lang/String;"),
        listOf<Type>(Type("I")) to MethodType("(I)Ljava/lang/String;")
    )

    @Test
    fun getArgumentTypes() {
        for ((arguments, methodType) in getArgumentTypesTests) {
            assertEquals(arguments, methodType.argumentTypes.toList())
        }
    }
}
