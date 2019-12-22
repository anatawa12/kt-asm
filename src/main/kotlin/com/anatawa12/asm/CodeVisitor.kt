package com.anatawa12.asm

/**
 * Created by anatawa12 on 2019/12/21.
 */

/**
 * visitor to visit code attribute.
 * The methods of this class must be called in the following order:
 *   ( [visitFrame] | [visitInsn] | [visitLabel] | [visitInsnAnnotation] | [visitTryCatchBlock] | [visitTryCatchAnnotation] | [visitLocalVariable] | [visitLocalVariableAnnotation] | [visitLineNumber] )* [visitMaxs]
 */
interface CodeVisitor {
    /**
     * visits a frame. This must be called just before instruction.
     */
    fun visitFrame(frame: StackMapFrame)

    /**
     * visits a label. This must be called just before instruction.
     */
    fun visitLabel(label: Label)

    /**
     * visits a instruction
     */
    fun visitInsn(insn: Insn)

    /**
     * Visits an annotation on a instruction. This must be called just after annotated instruction.
     *
     * @param descriptor the class descriptor of the annotation class.
     * @param visible true if the annotation is visible at runtime.
     * @return a visitor to visit the annotation.
     */
    fun visitInsnAnnotation(
        typeRef: CodeTarget,
        typePath: TypePath,
        descriptor: Descriptor,
        visible: Boolean
    ): AnnotationVisitor

    /**
     * visits a try catch block
     *
     * @param start start of try block (inclusive)
     * @param end end of try block (exclusive)
     * @param handler start of the handler
     * @param type the type of exception handled by the handler or `null` to catch any exception (like finally block).
     *
     */
    fun visitTryCatchBlock(
        start: Label,
        end: Label,
        handler: Label,
        type: InternalName?
    )

    /**
     * Visits an annotation on a type to handle. This must be called just after [visitTryCatchBlock].
     *
     * @param descriptor the class descriptor of the annotation class.
     * @param visible true if the annotation is visible at runtime.
     * @return a visitor to visit the annotation.
     */
    fun visitTryCatchAnnotation(
        typeRef: CodeTarget,
        typePath: TypePath,
        descriptor: Descriptor,
        visible: Boolean
    ): AnnotationVisitor

    /**
     * visits a local variable.
     *
     * @param name name of the variable
     * @param descriptor descriptor of the variable
     * @param signature signature of the variable.
     * @param start start of the scope of this local variable (inclusive)
     * @param end end of the scope of this local variable (exclusive)
     */
    fun visitLocalVariable(
        name: String,
        descriptor: Descriptor,
        signature: Signature?,
        start: Label,
        end: Label,
        index: Int
    )

    /**
     * visits an annotation on a local variable type.
     *
     * @param descriptor the class descriptor of the annotation class.
     * @param visible true if the annotation is visible at runtime.
     * @return a visitor to visit the annotation.
     */
    fun visitLocalVariableAnnotation(
        typeRef: CodeTarget,
        typePath: TypePath,
        descriptor: Descriptor,
        visible: Boolean
    ): AnnotationVisitor

    /**
     * visits a line number declaration.
     *
     * @param line the line number.
     * @param start the start instruction of this line.
     */
    fun visitLineNumber(line: Int, start: Label)

    /**
     * visits the maximum stack size and number if local variable if tge method.
     */
    fun visitMaxs(
        maxStack: UShort,
        maxLocal: UShort
    )
}
