package com.anatawa12.asm.internal

import org.junit.Test

import org.junit.Assert.*

/**
 * Created by anatawa12 on 2019/12/22.
 */
class DescriptorVerifierTest {
    @Test
    fun verifyType() {
        // valid

        // primitives
        assertTrue(DescriptorVerifier("V").verifyType())
        assertTrue(DescriptorVerifier("Z").verifyType())
        assertTrue(DescriptorVerifier("C").verifyType())
        assertTrue(DescriptorVerifier("B").verifyType())
        assertTrue(DescriptorVerifier("S").verifyType())
        assertTrue(DescriptorVerifier("I").verifyType())
        assertTrue(DescriptorVerifier("F").verifyType())
        assertTrue(DescriptorVerifier("J").verifyType())
        assertTrue(DescriptorVerifier("D").verifyType())

        // objects
        assertTrue(DescriptorVerifier("Ljava/lang/String;").verifyType())

        // arrays
        assertTrue(DescriptorVerifier("[Ljava/lang/String;").verifyType())
        assertTrue(DescriptorVerifier("[I").verifyType())

        // invalid

        // empty
        assertFalse(DescriptorVerifier("").verifyType())

        // too long
        assertFalse(DescriptorVerifier("D1").verifyType())

        // unknown Tag
        assertFalse(DescriptorVerifier("p").verifyType())
        assertFalse(DescriptorVerifier("l").verifyType())

        // invalid char in object
        assertFalse(DescriptorVerifier("Linvalid/char/here<here;").verifyType())

        // no end object name
        assertFalse(DescriptorVerifier("Linvalid/char").verifyType())
    }

    @Test
    fun verifyMethod() {
        assertTrue(DescriptorVerifier("()V").verifyMethod())
        assertTrue(DescriptorVerifier("(Ljava/lang/String;)Ljava/lang/StringBuilder;").verifyMethod())

        assertFalse(DescriptorVerifier(" I)I").verifyMethod())

        assertFalse(DescriptorVerifier("(V)I").verifyMethod())

        assertFalse(DescriptorVerifier("(I)Ljava").verifyMethod())

        assertFalse(DescriptorVerifier("(Ljava/lang/String;)Ljava/lang/StringBuilder; too long").verifyMethod())
    }
}
