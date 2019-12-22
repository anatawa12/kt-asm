package com.anatawa12.asm.internal

internal class DescriptorVerifier(private val string: String) {
    private var index = 0

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
                        ';' -> return
                        in unusableChars -> throw Invalid()
                    }
                }
            }
            '[' -> readType()
            else -> throw Invalid()
        }
    }

    private class Invalid : Exception()

    companion object {
        const val unusableChars = ".;[<>"
        const val PRIMITIVE_DESCRIPTORS = "VZCBSIFJD"
    }
}
