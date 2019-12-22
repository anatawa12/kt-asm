package com.anatawa12.asm

/**
 * Created by anatawa12 on 2019/12/21.
 */

/**
 * A visitor to visit a class.
 * The methods of this class must be called in the following order:
 * [visit] [ [visitSource] ] [ [visitModule] ] [ [visitNestHost] ] [ [visitOuterClass] ]
 *   ( [visitAnnotation] | [visitTypeAnnotation] | [visitAttribute] )*
 *   ( [visitNestMember] | [visitInnerClass] | [visitField] | [visitMethod] )* [visitEnd]
 */
interface ClassVisitor {
    fun visit(
        version: ClassVersion,
        access: ClassAccessFlags,
        name: Type,
        signature: Signature?,
        superName: Type?,
        interfaces: List<Type>
    )

    /**
     * vusuts source file name and debug info.
     */
    fun visitSource(source: String?, debug: String?)

    /**
     * visits module
     * @param name module name
     * @param flags module's flags
     * @param version version name
     */
    fun visitModule(name: String, flags: ModuleFlags, version: String?): ModuleVisitor?

    /**
     * visits nest host name
     */
    fun visitNestHost(nestHost: Type)

    /**
     * visits outer class and method.
     *
     * @param owner owner of the method
     * @param name name of the method
     * @param descriptor descriptor of the method
     */
    fun visitOuterClass(owner: Type, name: String?, descriptor: MethodType?)

    /**
     * Visits an annotation on a type in the class signature.
     *
     * @param descriptor the class descriptor of the annotation class.
     * @param visible true if the annotation is visible at runtime.
     * @return a visitor to visit the annotation.
     */
    fun visitAnnotation(descriptor: Type, visible: Boolean): AnnotationVisitor?

    /**
     * Visits an annotation on a type in the class signature.
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
     * visits unknown attribute
     */
    fun visitAttribute(attribute: Attribute)

    /**
     * visits nest member name
     * @param nestMember nested member name
     */
    fun visitNestMember(nestMember: Type)

    /**
     * visits inner class
     *
     * @param name inner class internal name
     * @param outerName outer class name
     * @param innerName simple name
     * @param access inner class' access flag
     */
    fun visitInnerClass(
        name: Type,
        outerName: Type?,
        innerName: String?,
        access: InnerClassAccessFlags
    )

    /**
     * visits field.
     *
     * @param access access flags for method
     * @param name name of the method
     * @param descriptor descriptor of the method
     * @param signature signature of the method
     */
    fun visitField(
        access: FieldAccessFlags,
        name: String,
        descriptor: Type,
        signature: Signature?,
        value: Any?
    ): FieldVisitor

    /**
     * visits method.
     *
     * @param access access flags for method
     * @param name name of the method
     * @param descriptor descriptor of the method
     * @param signature signature of the method
     * @param exceptions throws exceptions
     */
    fun visitMethod(
        access: MethodAccessFlags,
        name: String,
        descriptor: MethodType,
        signature: Signature?,
        exceptions: List<Type>
    ): MethodVisitor

    fun visitEnd()
}
