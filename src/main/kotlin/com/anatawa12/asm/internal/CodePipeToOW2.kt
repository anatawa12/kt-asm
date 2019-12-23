package com.anatawa12.asm.internal

import com.anatawa12.asm.*
import org.objectweb.asm.MethodVisitor

internal class CodePipeToOW2(
    val ow2: MethodVisitor
) : CodeVisitor {
    override fun visitFrame(frame: StackMapFrame) {
        ow2.visitFrame(
            frame.type,
            frame.numLocal,
            frame.localValues.mapArray { it.ow2 },
            frame.numStack,
            frame.stackValues.mapArray { it.ow2 })
    }

    override fun visitLabel(label: Label) {
        ow2.visitLabel(label.ow2)
    }

    override fun visitInsn(insn: Insn) = insn.run {
        when (this) {
            is IntInsn -> ow2.visitIntInsn(op.toInt(), operand.toInt())
            is VarInsn -> ow2.visitVarInsn(op.toInt(), variable.toInt())
            is JumpInsn -> ow2.visitJumpInsn(op.toInt(), label.ow2)
            is FieldInsn -> ow2.visitFieldInsn(op.toInt(), owner.internalName, name, descriptor.descriptor)
            is MethodInsn -> ow2.visitMethodInsn(
                op.toInt(),
                owner.internalName,
                name,
                descriptor.descriptor,
                isInterface
            )
            is TypeInsn -> ow2.visitTypeInsn(op.toInt(), type.internalName)
            is LdcInsn -> ow2.visitLdcInsn(value)
            is IincInsn -> ow2.visitIincInsn(varsible.toInt(), increment.toInt())
            is TableswitchInsn -> ow2.visitTableSwitchInsn(min, max, default.ow2, *labels.mapArray { it.ow2 })
            is LookupswitchInsn -> ow2.visitLookupSwitchInsn(
                default.ow2,
                pairs.mapArrayInt { it.first },
                pairs.mapArray { it.second.ow2 })
            is InvokedynamicInsn -> ow2.visitInvokeDynamicInsn(name, descriptor.descriptor, handle.ow2, *arguments)
            is MultianewarrayInsn -> ow2.visitMultiANewArrayInsn(descriptor.descriptor, dimensions)
            is SimpleInsn -> ow2.visitInsn(op.toInt())
        }
    }

    override fun visitInsnAnnotation(
        typeRef: CodeTarget,
        typePath: TypePath,
        descriptor: Type,
        visible: Boolean
    ): AnnotationVisitor? {
        return ow2.visitInsnAnnotation(typeRef.ow2, typePath.ow2, descriptor.descriptor, visible)
            ?.let(::AnnotationPipeToOW2)
    }

    override fun visitTryCatchBlock(start: Label, end: Label, handler: Label, type: Type?) {
        ow2.visitTryCatchBlock(start.ow2, end.ow2, handler.ow2, type?.internalName)
    }

    override fun visitTryCatchAnnotation(
        typeRef: CodeTarget,
        typePath: TypePath,
        descriptor: Type,
        visible: Boolean
    ): AnnotationVisitor? {
        return ow2.visitTryCatchAnnotation(typeRef.ow2, typePath.ow2, descriptor.descriptor, visible)
            ?.let(::AnnotationPipeToOW2)
    }

    override fun visitLocalVariable(
        name: String,
        descriptor: Type,
        signature: Signature?,
        start: Label,
        end: Label,
        index: Int
    ) {
        ow2.visitLocalVariable(name, descriptor.descriptor, signature?.sig, start.ow2, end.ow2, index)
    }

    override fun visitLocalVariableAnnotation(
        typeRef: CodeTarget,
        typePath: TypePath,
        ragnes: List<Pair<Pair<Label, Label>, Int>>,
        descriptor: Type,
        visible: Boolean
    ): AnnotationVisitor? {
        return ow2.visitLocalVariableAnnotation(
            typeRef.ow2,
            typePath.ow2,
            ragnes.mapArray { it.first.first.ow2 },
            ragnes.mapArray { it.first.second.ow2 },
            ragnes.mapArrayInt { it.second },
            descriptor.descriptor,
            visible
        )?.let(::AnnotationPipeToOW2)
    }

    override fun visitLineNumber(line: Int, start: Label) {
        ow2.visitLineNumber(line, start.ow2)
    }

    override fun visitMaxs(maxStack: UShort, maxLocal: UShort) {
        ow2.visitMaxs(maxStack.toInt(), maxLocal.toInt())
    }

}
