package com.anatawa12.asm

/**
 * Created by anatawa12 on 2019/12/21.
 */
/**
 * A visitor to visit a Java method. The methods of this class must be called in the following order:
 * ( [visitParameter] )* [ [visitAnnotationDefault] ] ( [visitAnnotation] | [visitAnnotableParameterCount] | [visitParameterAnnotation] [visitTypeAnnotation] | [visitAttribute] )* [ [visitCode] ] [visitEnd]
 */
interface MethodVisitor {

    /**
     * Visits a parameter of this method.
     *
     * @param name parameter name or null if none is provided.
     * @param access the parameter's access flags, only `ACC_FINAL`, `ACC_SYNTHETIC` or/and `ACC_MANDATED` are allowed.
     */
    fun visitParameter(name: String, access: AccessFlags)

    /**
     * Visits the default value of this annotation interface method.
     *
     * @return a visitor to the visit the actual default value of this annotation interface method, or
     * null if this visitor is not interested in visiting this default value. The
     * 'name' parameters passed to the methods of this annotation visitor are ignored. Moreover,
     * exacly one visit method must be called on this annotation visitor, followed by visitEnd.
     */
    fun visitAnnotationDefault(): AnnotationVisitor?

    /**
     * Visits an annotation of this method.
     *
     * @param descriptor the class descriptor of the annotation class.
     * @param visible true if the annotation is visible at runtime.
     * @return a visitor to visit the annotation.
     */
    fun visitAnnotation(descriptor: Type, visible: Boolean): AnnotationVisitor?

    /**
     * Visits an annotation on a type in the method signature.
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
     * Visits the number of method parameters that can have annotations. By default (i.e. when this
     * method is not called), all the method parameters defined by the method descriptor can have
     * annotations.
     *
     * @param parameterCount the number of method parameters than can have annotations.
     * @param visible is this for visible annotations
     */
    fun visitAnnotableParameterCount(parameterCount: Int, visible: Boolean)

    /**
     * Visits an annotation of a parameter this method.
     *
     * @param parameter the parameter index.
     * @param descriptor the class descriptor of the annotation class.
     * @param visible true if the annotation is visible at runtime.
     * @return a visitor to visit the annotation values
     */
    fun visitParameterAnnotation(
        parameter: Int, descriptor: Type, visible: Boolean
    ): AnnotationVisitor?

    /**
     * Visits a non standard attribute of this method.
     *
     * @param attribute an attribute.
     */
    fun visitAttribute(attribute: Attribute)

    /**
     * visits code.
     */
    fun visitCode(): CodeVisitor?

    /**
     * Visits the end of the method. This method, which is the last one to be called, is used to
     * inform the visitor that all the annotations and attributes of the method have been visited.
     */
    fun visitEnd()
}
