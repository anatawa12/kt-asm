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
        name: InternalName,
        signature: Signature?,
        superName: InternalName?,
        interfaces: List<InternalName>
    )

    fun visitSource(source: String?, debug: String?)

    fun visitModule(name: String, flags: ModuleFlags, version: String?): ModuleVisitor?

    fun visitNestHost(nestHost: InternalName)

    fun visitOuterClass(owner: InternalName, name: String?, descriptor: Descriptor)

    fun visitAnnotation(descriptor: Descriptor, visible: Boolean): AnnotationVisitor?

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
        descriptor: Descriptor,
        visible: Boolean
    ): AnnotationVisitor?

    fun visitAttribute(attribute: Attribute)

    fun visitNestMember(nestMember: InternalName)

    fun visitInnerClass(
        name: String,
        outerName: InternalName?,
        innerName: String?,
        access: InnerClassAccessFlags
    )

    fun visitField(
        access: FieldAccessFlags,
        name: String,
        descriptor: Descriptor,
        signature: Signature?,
        value: Any?
    ): FieldVisitor

    fun visitMethod(
        access: MethodAccessFlags,
        name: String,
        descriptor: Descriptor,
        signature: Signature?,
        exceptions: List<InternalName>
    ): MethodVisitor

    fun visitEnd()
}
