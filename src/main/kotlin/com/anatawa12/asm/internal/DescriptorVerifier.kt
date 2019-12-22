package com.anatawa12.asm.internal

import java.lang.Exception

internal class DescriptorVerifier(val string: String) {
    var index = 0

    fun read() = string[index++]
    fun peek() = string[index]

    fun verifyType(): Boolean {
        try {
            index = 0
            readType()
            return index == string.length
        } catch (e: IndexOutOfBoundsException) {
            return false
        } catch (e: Invalid) {
            return false
        }
    }

    fun verifyMethod(): Boolean {
        try {
            if (read() != '(') throw Invalid()
            while (true) {
                if (peek() == ')') break
                readArgType()
            }
            read()
            readType()
            return index == string.length
        } catch (e: IndexOutOfBoundsException) {
            return false
        } catch (e: Invalid) {
            return false
        }
    }

    private fun readArgType() {
        if (peek() == 'V') throw Invalid()
        readType()
    }

    private fun readType() {
        when (read()) {
            in PRIMITIVE_DESCRIPTORS -> return
            'L' -> {
                while (true) {
                    when (read()) {
                        in unusableChars -> throw Invalid()
                        ';' -> return
                    }
                }
            }
            '[' -> readType()
            else -> throw Invalid()
        }
    }

    private class Invalid : Exception()

    companion object {
        private const val unusableChars = ".;[<>"
        private const val PRIMITIVE_DESCRIPTORS = "ZCBSIFJD"
    }
}
