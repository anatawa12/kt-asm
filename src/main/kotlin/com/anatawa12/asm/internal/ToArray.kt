package com.anatawa12.asm.internal

/**
 * Created by anatawa12 on 2019/12/23.
 */

inline fun <T, reified R> Collection<T>.mapArray(mapper: (T) -> R): Array<R> {
    val iterator = iterator()
    return Array(size) { mapper(iterator.next()) }
}

fun <T> Collection<T>.mapArrayInt(mapper: (T) -> Int): IntArray {
    val iterator = iterator()
    return IntArray(size) { mapper(iterator.next()) }
}

inline fun <T, reified R> Array<T>.mapArray(mapper: (T) -> R): Array<R> {
    val iterator = iterator()
    return Array(size) { mapper(iterator.next()) }
}

fun <T> Array<T>.mapArrayInt(mapper: (T) -> Int): IntArray {
    val iterator = iterator()
    return IntArray(size) { mapper(iterator.next()) }
}
