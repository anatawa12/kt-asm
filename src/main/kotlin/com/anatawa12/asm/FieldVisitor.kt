package com.anatawa12.asm

/**
 * Created by anatawa12 on 2019/12/21.
 */

/**
 * A visitor to visit field.
 * The methods of this class must be called in the following order:
 * ( [visitAnnotation] | [visitTypeAnnotation] | [visitAttribute] )* [visitEnd]
 */
interface FieldVisitor {

    /**
     * Visits an annotation of the field.
     *
     * @param descriptor the class descriptor of the annotation class.
     * @param visible true if the annotation is visible at runtime.
     * @return a visitor to visit the annotation.
     */
    fun visitAnnotation(descriptor: Type, visible: Boolean): AnnotationVisitor?

    /**
     * Visits an annotation on the type of the field.
     *
     * @param descriptor the class descriptor of the annotation class.
     * @param visible true if the annotation is visible at runtime.
     * @return a visitor to visit the annotation.
     */
    fun visitTypeAnnotation(
        typeRef: TypeAnnotationTarget,
        typePath: TypePath,
        descriptor: Type,
        visible: Boolean
    ): AnnotationVisitor?

    /**
     * Visits a non standard attribute of the field.
     *
     * @param attribute an attribute.
     */
    fun visitAttribute(attribute: Attribute)

    /**
     * Visits the end of the field.
     */
    fun visitEnd()
}
