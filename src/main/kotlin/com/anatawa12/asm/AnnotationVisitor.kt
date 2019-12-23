package com.anatawa12.asm

/**
 * Created by anatawa12 on 2019/12/21.
 */

/**
 * A visitor to visit a annotation.
 * The methods of this class must be called in the following order:
 * ( [visit] | [visitEnum] | [visitAnnotation] | [visitArray] )* [visitEnd]
 */
interface AnnotationVisitor {
    /**
     * visits primitive or [String] value.
     * @param name the value name or empty if this visitor is visiting array.
     * @param value the value. the values type must be primitive or [String].
     */
    fun visit(name: String, value: Any)

    /**
     * visits an enum value.
     * @param name the value name or empty if this visitor is visiting array.
     * @param descriptor the class descriptor of enum class.
     * @param value the enum value name.
     */
    fun visitEnum(name: String, descriptor: Type, value: String)

    /**
     * visits a nested annotation value.
     * @param name the value name or empty if this visitor is visiting array.
     * @param descriptor the class descriptor of the nested annotation class.
     * @return a visitor to visit nested annotation value.
     */
    fun visitAnnotation(name: String, descriptor: Type): AnnotationVisitor?

    /**
     * visits an array value.
     * @param name the value name or empty if this visitor is visiting array.
     * @return a visitor to visit array value.
     */
    fun visitArray(name: String): AnnotationVisitor?

    /**
     * Visits the end of the annotation or array.
     */
    fun visitEnd()
}
