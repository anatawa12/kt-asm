package com.anatawa12.asm.internal

import com.anatawa12.asm.*
import com.anatawa12.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.TypeReference
import org.objectweb.asm.AnnotationVisitor as AnnotationVisitorOW2
import org.objectweb.asm.Attribute as AttributeOW2
import org.objectweb.asm.Handle as HandleOW2
import org.objectweb.asm.Label as LabelOW2
import org.objectweb.asm.MethodVisitor as FieldVisitorOW2
import org.objectweb.asm.TypePath as TypePathOW2

/**
 * Created by anatawa12 on 2019/12/23.
 */
class MethodPipeFromOW2(
    val methodVisitor: MethodVisitor
) : FieldVisitorOW2(Opcodes.ASM7) {
    override fun visitParameter(name: String, access: Int) {
        methodVisitor.visitParameter(name, AccessFlags(access.toUShort()))
    }

    override fun visitAnnotationDefault(): AnnotationVisitorOW2? {
        return methodVisitor.visitAnnotationDefault()?.let(::AnnotationPipeFromOW2)
    }

    override fun visitAnnotation(descriptor: String, visible: Boolean): AnnotationVisitorOW2? {
        return methodVisitor.visitAnnotation(Type(descriptor), visible)?.let(::AnnotationPipeFromOW2)
    }

    override fun visitTypeAnnotation(
        typeRef: Int,
        typePath: TypePathOW2,
        descriptor: String,
        visible: Boolean
    ): AnnotationVisitorOW2? {
        return methodVisitor.visitTypeAnnotation(
            TypeAnnotationTarget.fromASM(TypeReference(typeRef)),
            TypePath.fromASM(typePath),
            Type(descriptor),
            visible
        )?.let(::AnnotationPipeFromOW2)
    }

    override fun visitAnnotableParameterCount(parameterCount: Int, visible: Boolean) {
        methodVisitor.visitAnnotableParameterCount(parameterCount, visible)
    }

    override fun visitParameterAnnotation(parameter: Int, descriptor: String, visible: Boolean): AnnotationVisitorOW2? {
        return methodVisitor.visitParameterAnnotation(parameter, Type(descriptor), visible)
            ?.let(::AnnotationPipeFromOW2)
    }

    override fun visitAttribute(attribute: AttributeOW2) {
        // NOT SUPPORTED
    }

    override fun visitCode() {
        codeVisitor = methodVisitor.visitCode()
    }

    override fun visitEnd() {
        methodVisitor.visitEnd()
    }

    ////// code

    var codeVisitor: CodeVisitor? = null


    private fun anyReplacer(value: Any) = when (value) {
        is Short, is Boolean, is Float, is Long, is Double,
        is String, is Char, is Int, is Byte -> {
            value
        }
        is org.objectweb.asm.Type -> {
            val type = value
            val typeSort = type.sort
            if (typeSort == org.objectweb.asm.Type.METHOD) {
                MethodType(type.descriptor)
            } else { // type is a primitive or array type.
                Type(type.descriptor)
            }
        }
        is HandleOW2 -> {
            Handle.fromOW2(value)
        }
        else -> {
            throw IllegalArgumentException("value $value")
        }
    }

    override fun visitFrame(
        type: Int,
        numLocal: Int,
        local: Array<Any>?,
        numStack: Int,
        stack: Array<Any>?
    ) {
        val frame = when (type) {
            Opcodes.F_NEW -> NewFrame(
                local!!.map { VariableType.fromOW2(it) },
                stack!!.map { VariableType.fromOW2(it) })
            Opcodes.F_SAME -> SameFrame
            Opcodes.F_SAME1 -> Same1Frame(VariableType.fromOW2(stack!![0]))
            Opcodes.F_APPEND -> AppendFrame(local!!.map { VariableType.fromOW2(it) })
            Opcodes.F_CHOP -> ChopFrame(numLocal)
            Opcodes.F_FULL -> FullFrame(
                local!!.map { VariableType.fromOW2(it) },
                stack!!.map { VariableType.fromOW2(it) })
            else -> throw IllegalArgumentException()
        }

        codeVisitor?.visitFrame(frame)
    }

    override fun visitInsn(opcode: Int) {
        val insn = when (opcode) {
            Opcodes.NOP -> NopInsn
            Opcodes.ACONST_NULL -> AconstNullInsn
            Opcodes.ICONST_M1 -> IconstM1Insn
            Opcodes.ICONST_0 -> Iconst0Insn
            Opcodes.ICONST_1 -> Iconst1Insn
            Opcodes.ICONST_2 -> Iconst2Insn
            Opcodes.ICONST_3 -> Iconst3Insn
            Opcodes.ICONST_4 -> Iconst4Insn
            Opcodes.ICONST_5 -> Iconst5Insn
            Opcodes.LCONST_0 -> Lconst0Insn
            Opcodes.LCONST_1 -> Lconst1Insn
            Opcodes.FCONST_0 -> Fconst0Insn
            Opcodes.FCONST_1 -> Fconst1Insn
            Opcodes.FCONST_2 -> Fconst2Insn
            Opcodes.DCONST_0 -> Dconst0Insn
            Opcodes.DCONST_1 -> Dconst1Insn
            Opcodes.IALOAD -> IaloadInsn
            Opcodes.LALOAD -> LaloadInsn
            Opcodes.FALOAD -> FaloadInsn
            Opcodes.DALOAD -> DaloadInsn
            Opcodes.AALOAD -> AaloadInsn
            Opcodes.BALOAD -> BaloadInsn
            Opcodes.CALOAD -> CaloadInsn
            Opcodes.SALOAD -> SaloadInsn
            Opcodes.IASTORE -> IastoreInsn
            Opcodes.LASTORE -> LastoreInsn
            Opcodes.FASTORE -> FastoreInsn
            Opcodes.DASTORE -> DastoreInsn
            Opcodes.AASTORE -> AastoreInsn
            Opcodes.BASTORE -> BastoreInsn
            Opcodes.CASTORE -> CastoreInsn
            Opcodes.SASTORE -> SastoreInsn
            Opcodes.POP -> PopInsn
            Opcodes.POP2 -> Pop2Insn
            Opcodes.DUP -> DupInsn
            Opcodes.DUP_X1 -> DupX1Insn
            Opcodes.DUP_X2 -> DupX2Insn
            Opcodes.DUP2 -> Dup2Insn
            Opcodes.DUP2_X1 -> Dup2X1Insn
            Opcodes.DUP2_X2 -> Dup2X2Insn
            Opcodes.SWAP -> SwapInsn
            Opcodes.IADD -> IaddInsn
            Opcodes.LADD -> LaddInsn
            Opcodes.FADD -> FaddInsn
            Opcodes.DADD -> DaddInsn
            Opcodes.ISUB -> IsubInsn
            Opcodes.LSUB -> LsubInsn
            Opcodes.FSUB -> FsubInsn
            Opcodes.DSUB -> DsubInsn
            Opcodes.IMUL -> ImulInsn
            Opcodes.LMUL -> LmulInsn
            Opcodes.FMUL -> FmulInsn
            Opcodes.DMUL -> DmulInsn
            Opcodes.IDIV -> IdivInsn
            Opcodes.LDIV -> LdivInsn
            Opcodes.FDIV -> FdivInsn
            Opcodes.DDIV -> DdivInsn
            Opcodes.IREM -> IremInsn
            Opcodes.LREM -> LremInsn
            Opcodes.FREM -> FremInsn
            Opcodes.DREM -> DremInsn
            Opcodes.INEG -> InegInsn
            Opcodes.LNEG -> LnegInsn
            Opcodes.FNEG -> FnegInsn
            Opcodes.DNEG -> DnegInsn
            Opcodes.ISHL -> IshlInsn
            Opcodes.LSHL -> LshlInsn
            Opcodes.ISHR -> IshrInsn
            Opcodes.LSHR -> LshrInsn
            Opcodes.IUSHR -> IushrInsn
            Opcodes.LUSHR -> LushrInsn
            Opcodes.IAND -> IandInsn
            Opcodes.LAND -> LandInsn
            Opcodes.IOR -> IorInsn
            Opcodes.LOR -> LorInsn
            Opcodes.IXOR -> IxorInsn
            Opcodes.LXOR -> LxorInsn
            Opcodes.I2L -> I2lInsn
            Opcodes.I2F -> I2fInsn
            Opcodes.I2D -> I2dInsn
            Opcodes.L2I -> L2iInsn
            Opcodes.L2F -> L2fInsn
            Opcodes.L2D -> L2dInsn
            Opcodes.F2I -> F2iInsn
            Opcodes.F2L -> F2lInsn
            Opcodes.F2D -> F2dInsn
            Opcodes.D2I -> D2iInsn
            Opcodes.D2L -> D2lInsn
            Opcodes.D2F -> D2fInsn
            Opcodes.I2B -> I2bInsn
            Opcodes.I2C -> I2cInsn
            Opcodes.I2S -> I2sInsn
            Opcodes.LCMP -> LcmpInsn
            Opcodes.FCMPL -> FcmplInsn
            Opcodes.FCMPG -> FcmpgInsn
            Opcodes.DCMPL -> DcmplInsn
            Opcodes.DCMPG -> DcmpgInsn
            Opcodes.IRETURN -> IreturnInsn
            Opcodes.LRETURN -> LreturnInsn
            Opcodes.FRETURN -> FreturnInsn
            Opcodes.DRETURN -> DreturnInsn
            Opcodes.ARETURN -> AreturnInsn
            Opcodes.RETURN -> ReturnInsn
            Opcodes.ARRAYLENGTH -> ArraylengthInsn
            Opcodes.ATHROW -> AthrowInsn
            Opcodes.MONITORENTER -> MonitorenterInsn
            Opcodes.MONITOREXIT -> MonitorexitInsn
            else -> throw IllegalArgumentException()
        }

        codeVisitor?.visitInsn(insn)
    }


    override fun visitIntInsn(opcode: Int, operand: Int) {
        val insn = when (opcode) {
            Opcodes.BIPUSH -> BipushInsn(operand.toByte())
            Opcodes.SIPUSH -> SipushInsn(operand.toShort())
            Opcodes.NEWARRAY -> NewarrayInsn(PrimitiveType.getByValue(operand))
            else -> throw IllegalArgumentException()
        }

        codeVisitor?.visitInsn(insn)
    }


    override fun visitVarInsn(opcode: Int, `var`: Int) {
        val insn = when (opcode) {
            Opcodes.ILOAD -> IloadInsn(`var`.toUShort())
            Opcodes.LLOAD -> LloadInsn(`var`.toUShort())
            Opcodes.FLOAD -> FloadInsn(`var`.toUShort())
            Opcodes.DLOAD -> DloadInsn(`var`.toUShort())
            Opcodes.ALOAD -> AloadInsn(`var`.toUShort())
            Opcodes.ISTORE -> IstoreInsn(`var`.toUShort())
            Opcodes.LSTORE -> LstoreInsn(`var`.toUShort())
            Opcodes.FSTORE -> FstoreInsn(`var`.toUShort())
            Opcodes.DSTORE -> DstoreInsn(`var`.toUShort())
            Opcodes.ASTORE -> AstoreInsn(`var`.toUShort())
            Opcodes.RET -> RetInsn(`var`.toUShort())
            else -> throw IllegalArgumentException()
        }

        codeVisitor?.visitInsn(insn)
    }


    override fun visitTypeInsn(opcode: Int, type: String) {
        val typeOut = Type.fromInternalName(type)

        val insn = when (opcode) {
            Opcodes.NEW -> NewInsn(typeOut)
            Opcodes.ANEWARRAY -> AnewarrayInsn(typeOut)
            Opcodes.CHECKCAST -> CheckcastInsn(typeOut)
            Opcodes.INSTANCEOF -> InstanceofInsn(typeOut)
            else -> throw IllegalArgumentException()
        }

        codeVisitor?.visitInsn(insn)
    }


    override fun visitFieldInsn(
        opcode: Int, owner: String, name: String, descriptor: String
    ) {
        val ownerType = Type.fromInternalName(owner)
        val type = Type(descriptor)

        val insn = when (opcode) {
            Opcodes.GETSTATIC -> GetstaticInsn(ownerType, name, type)
            Opcodes.PUTSTATIC -> PutstaticInsn(ownerType, name, type)
            Opcodes.GETFIELD -> GetfieldInsn(ownerType, name, type)
            Opcodes.PUTFIELD -> PutfieldInsn(ownerType, name, type)
            else -> throw IllegalArgumentException()
        }

        codeVisitor?.visitInsn(insn)
    }

    override fun visitMethodInsn(
        opcode: Int,
        owner: String,
        name: String,
        descriptor: String,
        isInterface: Boolean
    ) {
        val ownerType = Type.fromInternalName(owner)
        val methodType = MethodType(descriptor)

        val insn = when (opcode) {
            Opcodes.INVOKEVIRTUAL -> InvokevirtualInsn(ownerType, name, methodType, isInterface)
            Opcodes.INVOKESPECIAL -> InvokespecialInsn(ownerType, name, methodType, isInterface)
            Opcodes.INVOKESTATIC -> InvokestaticInsn(ownerType, name, methodType, isInterface)
            Opcodes.INVOKEINTERFACE -> InvokeinterfaceInsn(ownerType, name, methodType, isInterface)
            else -> throw IllegalArgumentException()
        }

        codeVisitor?.visitInsn(insn)
    }


    override fun visitInvokeDynamicInsn(
        name: String,
        descriptor: String,
        bootstrapMethodHandle: HandleOW2,
        vararg bootstrapMethodArguments: Any
    ) {
        codeVisitor?.visitInsn(
            InvokedynamicInsn(
                name,
                MethodType(descriptor),
                Handle.fromOW2(bootstrapMethodHandle),
                *bootstrapMethodArguments.mapArray(::anyReplacer)
            )
        )
    }


    override fun visitJumpInsn(opcode: Int, label: LabelOW2) {
        val labelV = Label(label)

        val insn = when (opcode) {
            Opcodes.IFEQ -> IfeqInsn(labelV)
            Opcodes.IFNE -> IfneInsn(labelV)
            Opcodes.IFLT -> IfltInsn(labelV)
            Opcodes.IFGE -> IfgeInsn(labelV)
            Opcodes.IFGT -> IfgtInsn(labelV)
            Opcodes.IFLE -> IfleInsn(labelV)
            Opcodes.IF_ICMPEQ -> IfIcmpeqInsn(labelV)
            Opcodes.IF_ICMPNE -> IfIcmpneInsn(labelV)
            Opcodes.IF_ICMPLT -> IfIcmpltInsn(labelV)
            Opcodes.IF_ICMPGE -> IfIcmpgeInsn(labelV)
            Opcodes.IF_ICMPGT -> IfIcmpgtInsn(labelV)
            Opcodes.IF_ICMPLE -> IfIcmpleInsn(labelV)
            Opcodes.IF_ACMPEQ -> IfAcmpeqInsn(labelV)
            Opcodes.IF_ACMPNE -> IfAcmpneInsn(labelV)
            Opcodes.GOTO -> GotoInsn(labelV)
            Opcodes.JSR -> JsrInsn(labelV)
            Opcodes.IFNULL -> IfnullInsn(labelV)
            Opcodes.IFNONNULL -> IfnonnullInsn(labelV)
            else -> throw IllegalArgumentException()
        }

        codeVisitor?.visitInsn(insn)
    }


    override fun visitLabel(label: LabelOW2) {
        codeVisitor?.visitLabel(Label(label))
    }

    override fun visitLdcInsn(value: Any) {
        codeVisitor?.visitInsn(LdcInsn(anyReplacer(value)))
    }


    override fun visitIincInsn(`var`: Int, increment: Int) {
        codeVisitor?.visitInsn(IincInsn(`var`.toUShort(), increment.toUShort()))
    }


    override fun visitTableSwitchInsn(
        min: Int, max: Int, dflt: LabelOW2, vararg labels: LabelOW2
    ) {
        codeVisitor?.visitInsn(TableswitchInsn(min, max, Label(dflt), *labels.mapArray(::Label)))
    }


    override fun visitLookupSwitchInsn(
        dflt: LabelOW2,
        keys: IntArray,
        labels: Array<LabelOW2>
    ) {
        codeVisitor?.visitInsn(LookupswitchInsn(Label(dflt), *keys.zip(labels.map(::Label)).toTypedArray()))
    }


    override fun visitMultiANewArrayInsn(descriptor: String, numDimensions: Int) {
        codeVisitor?.visitInsn(MultianewarrayInsn(Type(descriptor), numDimensions))
    }


    override fun visitInsnAnnotation(
        typeRef: Int, typePath: TypePathOW2, descriptor: String, visible: Boolean
    ): org.objectweb.asm.AnnotationVisitor? {
        return codeVisitor?.visitTryCatchAnnotation(
            TypeAnnotationTarget.fromASM(TypeReference(typeRef)) as CodeTarget,
            TypePath.fromASM(typePath),
            Type(descriptor),
            visible
        )?.let(::AnnotationPipeFromOW2)
    }

    override fun visitTryCatchBlock(
        start: LabelOW2,
        end: LabelOW2,
        handler: LabelOW2,
        type: String?
    ) {
        codeVisitor?.visitTryCatchBlock(
            Label(start),
            Label(end),
            Label(handler),
            type?.let(Type.Companion::fromInternalName)
        )
    }


    override fun visitTryCatchAnnotation(
        typeRef: Int, typePath: TypePathOW2, descriptor: String, visible: Boolean
    ): AnnotationVisitorOW2? {
        return codeVisitor?.visitTryCatchAnnotation(
            TypeAnnotationTarget.fromASM(TypeReference(typeRef)) as CodeTarget,
            TypePath.fromASM(typePath),
            Type(descriptor),
            visible
        )?.let(::AnnotationPipeFromOW2)
    }


    override fun visitLocalVariable(
        name: String,
        descriptor: String,
        signature: String?,
        start: LabelOW2,
        end: LabelOW2,
        index: Int
    ) {
        codeVisitor?.visitLocalVariable(
            name,
            Type(descriptor),
            signature?.let(::Signature),
            Label(start),
            Label(end),
            index
        )
    }


    override fun visitLocalVariableAnnotation(
        typeRef: Int,
        typePath: TypePathOW2,
        start: Array<LabelOW2>,
        end: Array<LabelOW2>,
        index: IntArray,
        descriptor: String,
        visible: Boolean
    ): AnnotationVisitorOW2? {
        return codeVisitor?.visitLocalVariableAnnotation(
            TypeAnnotationTarget.fromASM(TypeReference(typeRef)) as CodeTarget,
            TypePath.fromASM(typePath),
            start.map(::Label).zip(end.map(::Label)).zip(index.toTypedArray()),
            Type(descriptor),
            visible
        )?.let(::AnnotationPipeFromOW2)
    }


    override fun visitLineNumber(line: Int, start: LabelOW2) {
        codeVisitor?.visitLineNumber(line, Label(start))
    }

    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
        codeVisitor?.visitMaxs(maxStack.toUShort(), maxLocals.toUShort())
    }

}
