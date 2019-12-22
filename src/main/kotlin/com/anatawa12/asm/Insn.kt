@file:Suppress("EXPERIMENTAL_UNSIGNED_LITERALS")
package com.anatawa12.asm

import javax.naming.Name

/**
 * Created by anatawa12 on 2019/12/22.
 */
/**
 * a instruction with operand
 */
sealed class Insn (val op: UByte)
sealed class IntInsn (val operand: UShort, op: UByte) : Insn(op)
sealed class VarInsn (val variable: UShort, op: UByte) : Insn(op)
sealed class JumpInsn (val label: Label, op: UByte) : Insn(op)
sealed class FieldInsn (val owner: Type, val name: String, val descriptor: Type, op: UByte) : Insn(op)
sealed class MethodInsn (val owner: Type, val name: String, val descriptor: MethodType, val isInterface: Boolean, op: UByte) : Insn(op)
sealed class TypeInsn (val type: Type, op: UByte) : Insn(op)

object NopInsn : Insn(0u) // visitInsn
object AconstNullInsn : Insn(1u) // visitInsn
object IconstM1Insn : Insn(2u) // visitInsn
object Iconst0Insn : Insn(3u) // visitInsn
object Iconst1Insn : Insn(4u) // visitInsn
object Iconst2Insn : Insn(5u) // visitInsn
object Iconst3Insn : Insn(6u) // visitInsn
object Iconst4Insn : Insn(7u) // visitInsn
object Iconst5Insn : Insn(8u) // visitInsn
object Lconst0Insn : Insn(9u) // visitInsn
object Lconst1Insn : Insn(10u) // visitInsn
object Fconst0Insn : Insn(11u) // visitInsn
object Fconst1Insn : Insn(12u) // visitInsn
object Fconst2Insn : Insn(13u) // visitInsn
object Dconst0Insn : Insn(14u) // visitInsn
object Dconst1Insn : Insn(15u) // visitInsn
class BipushInsn(value: Byte) : IntInsn(value.toUShort(), 16u) // visitIntInsn
class SipushInsn(value: Short) : IntInsn(value.toUShort(), 17u) // visitIntInsn
class LdcInsn(val value: Any?) : Insn(18u) // visitLdcInsn
class IloadInsn(variable: UShort) : VarInsn(variable, 21u) // visitVarInsn
class LloadInsn(variable: UShort) : VarInsn(variable, 22u) // visitVarInsn
class FloadInsn(variable: UShort) : VarInsn(variable, 23u) // visitVarInsn
class DloadInsn(variable: UShort) : VarInsn(variable, 24u) // visitVarInsn
class AloadInsn(variable: UShort) : VarInsn(variable, 25u) // visitVarInsn
object IaloadInsn : Insn(46u) // visitInsn
object LaloadInsn : Insn(47u) // visitInsn
object FaloadInsn : Insn(48u) // visitInsn
object DaloadInsn : Insn(49u) // visitInsn
object AaloadInsn : Insn(50u) // visitInsn
object BaloadInsn : Insn(51u) // visitInsn
object CaloadInsn : Insn(52u) // visitInsn
object SaloadInsn : Insn(53u) // visitInsn
class IstoreInsn(variable: UShort) : VarInsn(variable, 54u) // visitVarInsn
class LstoreInsn(variable: UShort) : VarInsn(variable, 55u) // visitVarInsn
class FstoreInsn(variable: UShort) : VarInsn(variable, 56u) // visitVarInsn
class DstoreInsn(variable: UShort) : VarInsn(variable, 57u) // visitVarInsn
class AstoreInsn(variable: UShort) : VarInsn(variable, 58u) // visitVarInsn
object IastoreInsn : Insn(79u) // visitInsn
object LastoreInsn : Insn(80u) // visitInsn
object FastoreInsn : Insn(81u) // visitInsn
object DastoreInsn : Insn(82u) // visitInsn
object AastoreInsn : Insn(83u) // visitInsn
object BastoreInsn : Insn(84u) // visitInsn
object CastoreInsn : Insn(85u) // visitInsn
object SastoreInsn : Insn(86u) // visitInsn
object PopInsn : Insn(87u) // visitInsn
object Pop2Insn : Insn(88u) // visitInsn
object DupInsn : Insn(89u) // visitInsn
object DupX1Insn : Insn(90u) // visitInsn
object DupX2Insn : Insn(91u) // visitInsn
object Dup2Insn : Insn(92u) // visitInsn
object Dup2X1Insn : Insn(93u) // visitInsn
object Dup2X2Insn : Insn(94u) // visitInsn
object SwapInsn : Insn(95u) // visitInsn
object IaddInsn : Insn(96u) // visitInsn
object LaddInsn : Insn(97u) // visitInsn
object FaddInsn : Insn(98u) // visitInsn
object DaddInsn : Insn(99u) // visitInsn
object IsubInsn : Insn(100u) // visitInsn
object LsubInsn : Insn(101u) // visitInsn
object FsubInsn : Insn(102u) // visitInsn
object DsubInsn : Insn(103u) // visitInsn
object ImulInsn : Insn(104u) // visitInsn
object LmulInsn : Insn(105u) // visitInsn
object FmulInsn : Insn(106u) // visitInsn
object DmulInsn : Insn(107u) // visitInsn
object IdivInsn : Insn(108u) // visitInsn
object LdivInsn : Insn(109u) // visitInsn
object FdivInsn : Insn(110u) // visitInsn
object DdivInsn : Insn(111u) // visitInsn
object IremInsn : Insn(112u) // visitInsn
object LremInsn : Insn(113u) // visitInsn
object FremInsn : Insn(114u) // visitInsn
object DremInsn : Insn(115u) // visitInsn
object InegInsn : Insn(116u) // visitInsn
object LnegInsn : Insn(117u) // visitInsn
object FnegInsn : Insn(118u) // visitInsn
object DnegInsn : Insn(119u) // visitInsn
object IshlInsn : Insn(120u) // visitInsn
object LshlInsn : Insn(121u) // visitInsn
object IshrInsn : Insn(122u) // visitInsn
object LshrInsn : Insn(123u) // visitInsn
object IushrInsn : Insn(124u) // visitInsn
object LushrInsn : Insn(125u) // visitInsn
object IandInsn : Insn(126u) // visitInsn
object LandInsn : Insn(127u) // visitInsn
object IorInsn : Insn(128u) // visitInsn
object LorInsn : Insn(129u) // visitInsn
object IxorInsn : Insn(130u) // visitInsn
object LxorInsn : Insn(131u) // visitInsn
class IincInsn(val varsible: UShort, val increment: UShort) : Insn(132u) // visitIincInsn
object I2lInsn : Insn(133u) // visitInsn
object I2fInsn : Insn(134u) // visitInsn
object I2dInsn : Insn(135u) // visitInsn
object L2iInsn : Insn(136u) // visitInsn
object L2fInsn : Insn(137u) // visitInsn
object L2dInsn : Insn(138u) // visitInsn
object F2iInsn : Insn(139u) // visitInsn
object F2lInsn : Insn(140u) // visitInsn
object F2dInsn : Insn(141u) // visitInsn
object D2iInsn : Insn(142u) // visitInsn
object D2lInsn : Insn(143u) // visitInsn
object D2fInsn : Insn(144u) // visitInsn
object I2bInsn : Insn(145u) // visitInsn
object I2cInsn : Insn(146u) // visitInsn
object I2sInsn : Insn(147u) // visitInsn
object LcmpInsn : Insn(148u) // visitInsn
object FcmplInsn : Insn(149u) // visitInsn
object FcmpgInsn : Insn(150u) // visitInsn
object DcmplInsn : Insn(151u) // visitInsn
object DcmpgInsn : Insn(152u) // visitInsn
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
class TableswitchInsn(val min: Int, val max: Int, val default: Label, vararg val labels: Label) : Insn(170u) // visiTableSwitchInsn
class LookupswitchInsn(val default: Label, vararg val pairs: Pair<Int, Label>) : Insn(171u) // visitLookupSwitch
object IreturnInsn : Insn(172u) // visitInsn
object LreturnInsn : Insn(173u) // visitInsn
object FreturnInsn : Insn(174u) // visitInsn
object DreturnInsn : Insn(175u) // visitInsn
object AreturnInsn : Insn(176u) // visitInsn
object ReturnInsn : Insn(177u) // visitInsn
class GetstaticInsn(owner: Type, name: String, descriptor: Type) : FieldInsn(owner, name, descriptor, 178u) // visitFieldInsn
class PutstaticInsn(owner: Type, name: String, descriptor: Type) : FieldInsn(owner, name, descriptor, 179u) // visitFieldInsn
class GetfieldInsn(owner: Type, name: String, descriptor: Type) : FieldInsn(owner, name, descriptor, 180u) // visitFieldInsn
class PutfieldInsn(owner: Type, name: String, descriptor: Type) : FieldInsn(owner, name, descriptor, 181u) // visitFieldInsn
class InvokevirtualInsn(owner: Type, name: String, descriptor: MethodType, isInterface: Boolean) : MethodInsn(owner, name, descriptor, isInterface, 182u) // visitMethodInsn
class InvokespecialInsn(owner: Type, name: String, descriptor: MethodType, isInterface: Boolean) : MethodInsn(owner, name, descriptor, isInterface, 183u) // visitMethodInsn
class InvokestaticInsn(owner: Type, name: String, descriptor: MethodType, isInterface: Boolean) : MethodInsn(owner, name, descriptor, isInterface, 184u) // visitMethodInsn
class InvokeinterfaceInsn(owner: Type, name: String, descriptor: MethodType, isInterface: Boolean) : MethodInsn(owner, name, descriptor, isInterface, 185u) // visitMethodInsn
class InvokedynamicInsn(val name: Name, val descriptor: MethodType, val handle: Handle, vararg val arguments: Any?) : Insn(186u) // visitInvokeDynamicInsn
class NewInsn(type: Type) : TypeInsn(type, 187u) // visitTypeInsn
class NewarrayInsn(type: PrimitiveType) : IntInsn(type.value.toUShort(), 188u) // visitIntInsn
class AnewarrayInsn(type: Type) : TypeInsn(type, 189u) // visitTypeInsn
object ArraylengthInsn : Insn(190u) // visitInsn
object AthrowInsn : Insn(191u) // visitInsn
class CheckcastInsn(type: Type) : TypeInsn(type, 192u) // visitTypeInsn
class InstanceofInsn(type: Type) : TypeInsn(type, 193u) // visitTypeInsn
object MonitorenterInsn : Insn(194u) // visitInsn
object MonitorexitInsn : Insn(195u) // visitInsn
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
