@file:Suppress("EXPERIMENTAL_UNSIGNED_LITERALS")
package com.anatawa12.asm

/**
 * Created by anatawa12 on 2019/12/22.
 */
/**
 * a instruction with operand
 */
sealed class Insn(val op: UByte)

sealed class SimpleInsn(op: UByte) : Insn(op)
sealed class IntInsn(val operand: UShort, op: UByte) : Insn(op)
sealed class VarInsn(val variable: UShort, op: UByte) : Insn(op)
sealed class JumpInsn(val label: Label, op: UByte) : Insn(op)
sealed class FieldInsn(val owner: Type, val name: String, val descriptor: Type, op: UByte) : Insn(op)
sealed class MethodInsn(
    val owner: Type,
    val name: String,
    val descriptor: MethodType,
    val isInterface: Boolean,
    op: UByte
) : Insn(op)

sealed class TypeInsn(val type: Type, op: UByte) : Insn(op)

object NopInsn : SimpleInsn(0u) // visitInsn
object AconstNullInsn : SimpleInsn(1u) // visitInsn
object IconstM1Insn : SimpleInsn(2u) // visitInsn
object Iconst0Insn : SimpleInsn(3u) // visitInsn
object Iconst1Insn : SimpleInsn(4u) // visitInsn
object Iconst2Insn : SimpleInsn(5u) // visitInsn
object Iconst3Insn : SimpleInsn(6u) // visitInsn
object Iconst4Insn : SimpleInsn(7u) // visitInsn
object Iconst5Insn : SimpleInsn(8u) // visitInsn
object Lconst0Insn : SimpleInsn(9u) // visitInsn
object Lconst1Insn : SimpleInsn(10u) // visitInsn
object Fconst0Insn : SimpleInsn(11u) // visitInsn
object Fconst1Insn : SimpleInsn(12u) // visitInsn
object Fconst2Insn : SimpleInsn(13u) // visitInsn
object Dconst0Insn : SimpleInsn(14u) // visitInsn
object Dconst1Insn : SimpleInsn(15u) // visitInsn
class BipushInsn(value: Byte) : IntInsn(value.toUShort(), 16u) // visitIntInsn
class SipushInsn(value: Short) : IntInsn(value.toUShort(), 17u) // visitIntInsn
class LdcInsn(val value: Any?) : Insn(18u) // visitLdcInsn
class IloadInsn(variable: UShort) : VarInsn(variable, 21u) // visitVarInsn
class LloadInsn(variable: UShort) : VarInsn(variable, 22u) // visitVarInsn
class FloadInsn(variable: UShort) : VarInsn(variable, 23u) // visitVarInsn
class DloadInsn(variable: UShort) : VarInsn(variable, 24u) // visitVarInsn
class AloadInsn(variable: UShort) : VarInsn(variable, 25u) // visitVarInsn
object IaloadInsn : SimpleInsn(46u) // visitInsn
object LaloadInsn : SimpleInsn(47u) // visitInsn
object FaloadInsn : SimpleInsn(48u) // visitInsn
object DaloadInsn : SimpleInsn(49u) // visitInsn
object AaloadInsn : SimpleInsn(50u) // visitInsn
object BaloadInsn : SimpleInsn(51u) // visitInsn
object CaloadInsn : SimpleInsn(52u) // visitInsn
object SaloadInsn : SimpleInsn(53u) // visitInsn
class IstoreInsn(variable: UShort) : VarInsn(variable, 54u) // visitVarInsn
class LstoreInsn(variable: UShort) : VarInsn(variable, 55u) // visitVarInsn
class FstoreInsn(variable: UShort) : VarInsn(variable, 56u) // visitVarInsn
class DstoreInsn(variable: UShort) : VarInsn(variable, 57u) // visitVarInsn
class AstoreInsn(variable: UShort) : VarInsn(variable, 58u) // visitVarInsn
object IastoreInsn : SimpleInsn(79u) // visitInsn
object LastoreInsn : SimpleInsn(80u) // visitInsn
object FastoreInsn : SimpleInsn(81u) // visitInsn
object DastoreInsn : SimpleInsn(82u) // visitInsn
object AastoreInsn : SimpleInsn(83u) // visitInsn
object BastoreInsn : SimpleInsn(84u) // visitInsn
object CastoreInsn : SimpleInsn(85u) // visitInsn
object SastoreInsn : SimpleInsn(86u) // visitInsn
object PopInsn : SimpleInsn(87u) // visitInsn
object Pop2Insn : SimpleInsn(88u) // visitInsn
object DupInsn : SimpleInsn(89u) // visitInsn
object DupX1Insn : SimpleInsn(90u) // visitInsn
object DupX2Insn : SimpleInsn(91u) // visitInsn
object Dup2Insn : SimpleInsn(92u) // visitInsn
object Dup2X1Insn : SimpleInsn(93u) // visitInsn
object Dup2X2Insn : SimpleInsn(94u) // visitInsn
object SwapInsn : SimpleInsn(95u) // visitInsn
object IaddInsn : SimpleInsn(96u) // visitInsn
object LaddInsn : SimpleInsn(97u) // visitInsn
object FaddInsn : SimpleInsn(98u) // visitInsn
object DaddInsn : SimpleInsn(99u) // visitInsn
object IsubInsn : SimpleInsn(100u) // visitInsn
object LsubInsn : SimpleInsn(101u) // visitInsn
object FsubInsn : SimpleInsn(102u) // visitInsn
object DsubInsn : SimpleInsn(103u) // visitInsn
object ImulInsn : SimpleInsn(104u) // visitInsn
object LmulInsn : SimpleInsn(105u) // visitInsn
object FmulInsn : SimpleInsn(106u) // visitInsn
object DmulInsn : SimpleInsn(107u) // visitInsn
object IdivInsn : SimpleInsn(108u) // visitInsn
object LdivInsn : SimpleInsn(109u) // visitInsn
object FdivInsn : SimpleInsn(110u) // visitInsn
object DdivInsn : SimpleInsn(111u) // visitInsn
object IremInsn : SimpleInsn(112u) // visitInsn
object LremInsn : SimpleInsn(113u) // visitInsn
object FremInsn : SimpleInsn(114u) // visitInsn
object DremInsn : SimpleInsn(115u) // visitInsn
object InegInsn : SimpleInsn(116u) // visitInsn
object LnegInsn : SimpleInsn(117u) // visitInsn
object FnegInsn : SimpleInsn(118u) // visitInsn
object DnegInsn : SimpleInsn(119u) // visitInsn
object IshlInsn : SimpleInsn(120u) // visitInsn
object LshlInsn : SimpleInsn(121u) // visitInsn
object IshrInsn : SimpleInsn(122u) // visitInsn
object LshrInsn : SimpleInsn(123u) // visitInsn
object IushrInsn : SimpleInsn(124u) // visitInsn
object LushrInsn : SimpleInsn(125u) // visitInsn
object IandInsn : SimpleInsn(126u) // visitInsn
object LandInsn : SimpleInsn(127u) // visitInsn
object IorInsn : SimpleInsn(128u) // visitInsn
object LorInsn : SimpleInsn(129u) // visitInsn
object IxorInsn : SimpleInsn(130u) // visitInsn
object LxorInsn : SimpleInsn(131u) // visitInsn
class IincInsn(val varsible: UShort, val increment: UShort) : Insn(132u) // visitIincInsn
object I2lInsn : SimpleInsn(133u) // visitInsn
object I2fInsn : SimpleInsn(134u) // visitInsn
object I2dInsn : SimpleInsn(135u) // visitInsn
object L2iInsn : SimpleInsn(136u) // visitInsn
object L2fInsn : SimpleInsn(137u) // visitInsn
object L2dInsn : SimpleInsn(138u) // visitInsn
object F2iInsn : SimpleInsn(139u) // visitInsn
object F2lInsn : SimpleInsn(140u) // visitInsn
object F2dInsn : SimpleInsn(141u) // visitInsn
object D2iInsn : SimpleInsn(142u) // visitInsn
object D2lInsn : SimpleInsn(143u) // visitInsn
object D2fInsn : SimpleInsn(144u) // visitInsn
object I2bInsn : SimpleInsn(145u) // visitInsn
object I2cInsn : SimpleInsn(146u) // visitInsn
object I2sInsn : SimpleInsn(147u) // visitInsn
object LcmpInsn : SimpleInsn(148u) // visitInsn
object FcmplInsn : SimpleInsn(149u) // visitInsn
object FcmpgInsn : SimpleInsn(150u) // visitInsn
object DcmplInsn : SimpleInsn(151u) // visitInsn
object DcmpgInsn : SimpleInsn(152u) // visitInsn
class IfeqInsn(label: Label) : JumpInsn(label, 153u) // visitJumpInsn
class IfneInsn(label: Label) : JumpInsn(label, 154u) // visitJumpInsn
class IfltInsn(label: Label) : JumpInsn(label, 155u) // visitJumpInsn
class IfgeInsn(label: Label) : JumpInsn(label, 156u) // visitJumpInsn
class IfgtInsn(label: Label) : JumpInsn(label, 157u) // visitJumpInsn
class IfleInsn(label: Label) : JumpInsn(label, 158u) // visitJumpInsn
class IfIcmpeqInsn(label: Label) : JumpInsn(label, 159u) // visitJumpInsn
class IfIcmpneInsn(label: Label) : JumpInsn(label, 160u) // visitJumpInsn
class IfIcmpltInsn(label: Label) : JumpInsn(label, 161u) // visitJumpInsn
class IfIcmpgeInsn(label: Label) : JumpInsn(label, 162u) // visitJumpInsn
class IfIcmpgtInsn(label: Label) : JumpInsn(label, 163u) // visitJumpInsn
class IfIcmpleInsn(label: Label) : JumpInsn(label, 164u) // visitJumpInsn
class IfAcmpeqInsn(label: Label) : JumpInsn(label, 165u) // visitJumpInsn
class IfAcmpneInsn(label: Label) : JumpInsn(label, 166u) // visitJumpInsn
class GotoInsn(label: Label) : JumpInsn(label, 167u) // visitJumpInsn
class JsrInsn(label: Label) : JumpInsn(label, 168u) // visitJumpInsn
class RetInsn(variable: UShort) : VarInsn(variable, 169u) // visitVarInsn
class TableswitchInsn(val min: Int, val max: Int, val default: Label, vararg val labels: Label) :
    Insn(170u) // visiTableSwitchInsn

class LookupswitchInsn(val default: Label, vararg val pairs: Pair<Int, Label>) : Insn(171u) // visitLookupSwitch
object IreturnInsn : SimpleInsn(172u) // visitInsn
object LreturnInsn : SimpleInsn(173u) // visitInsn
object FreturnInsn : SimpleInsn(174u) // visitInsn
object DreturnInsn : SimpleInsn(175u) // visitInsn
object AreturnInsn : SimpleInsn(176u) // visitInsn
object ReturnInsn : SimpleInsn(177u) // visitInsn
class GetstaticInsn(owner: Type, name: String, descriptor: Type) :
    FieldInsn(owner, name, descriptor, 178u) // visitFieldInsn

class PutstaticInsn(owner: Type, name: String, descriptor: Type) :
    FieldInsn(owner, name, descriptor, 179u) // visitFieldInsn

class GetfieldInsn(owner: Type, name: String, descriptor: Type) :
    FieldInsn(owner, name, descriptor, 180u) // visitFieldInsn

class PutfieldInsn(owner: Type, name: String, descriptor: Type) :
    FieldInsn(owner, name, descriptor, 181u) // visitFieldInsn

class InvokevirtualInsn(owner: Type, name: String, descriptor: MethodType, isInterface: Boolean) :
    MethodInsn(owner, name, descriptor, isInterface, 182u) // visitMethodInsn

class InvokespecialInsn(owner: Type, name: String, descriptor: MethodType, isInterface: Boolean) :
    MethodInsn(owner, name, descriptor, isInterface, 183u) // visitMethodInsn

class InvokestaticInsn(owner: Type, name: String, descriptor: MethodType, isInterface: Boolean) :
    MethodInsn(owner, name, descriptor, isInterface, 184u) // visitMethodInsn

class InvokeinterfaceInsn(owner: Type, name: String, descriptor: MethodType, isInterface: Boolean) :
    MethodInsn(owner, name, descriptor, isInterface, 185u) // visitMethodInsn

class InvokedynamicInsn(val name: String, val descriptor: MethodType, val handle: Handle, vararg val arguments: Any?) :
    Insn(186u) // visitInvokeDynamicInsn

class NewInsn(type: Type) : TypeInsn(type, 187u) // visitTypeInsn
class NewarrayInsn(type: PrimitiveType) : IntInsn(type.value.toUShort(), 188u) // visitIntInsn
class AnewarrayInsn(type: Type) : TypeInsn(type, 189u) // visitTypeInsn
object ArraylengthInsn : SimpleInsn(190u) // visitInsn
object AthrowInsn : SimpleInsn(191u) // visitInsn
class CheckcastInsn(type: Type) : TypeInsn(type, 192u) // visitTypeInsn
class InstanceofInsn(type: Type) : TypeInsn(type, 193u) // visitTypeInsn
object MonitorenterInsn : SimpleInsn(194u) // visitInsn
object MonitorexitInsn : SimpleInsn(195u) // visitInsn
class MultianewarrayInsn(val descriptor: Type, val dimensions: Int) : Insn(197u) // visitMultiANewArrayInsn
class IfnullInsn(label: Label) : JumpInsn(label, 198u) // visitJumpInsn
class IfnonnullInsn(label: Label) : JumpInsn(label, 199u) // visitJumpInsn

enum class PrimitiveType(val value: UByte) {
    Boolean(4u),
    Char(5u),
    Float(6u),
    Double(7u),
    Byte(8u),
    Short(9u),
    Int(10u),
    Long(11u),
}
