package com.anatawa12.asm

/**
 * Created by anatawa12 on 2019/12/21.
 */
open class Attribute(
    /**
     * type of this attribute
     */
    val type: String,
    /**
     * the attribute content without name index and length
     */
    val content: ByteArray
)
