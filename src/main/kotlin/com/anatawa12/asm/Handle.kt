package com.anatawa12.asm

import org.objectweb.asm.Opcodes
import org.objectweb.asm.Handle as HandleOW2

/**
 * Created by anatawa12 on 2019/12/22.
 */
/**
 * Handle method for invokedynamic.
 */
class Handle internal constructor(
    val kind: Kind,
    val owner: Type,
    val name1: String,
    val descriptor: String,
    val isInterface: Boolean
) {
    val ow2: HandleOW2 get() = HandleOW2(kind.id.toInt(), owner.internalName, name1, descriptor, isInterface)

    companion object {
        fun getField(owner: Type, name: String, descriptor: Type): Handle =
            Handle(Kind.GetField, owner, name, descriptor.descriptor, false)

        fun putField(owner: Type, name: String, descriptor: Type): Handle =
            Handle(Kind.PutField, owner, name, descriptor.descriptor, false)

        fun getStatic(owner: Type, name: String, descriptor: Type): Handle =
            Handle(Kind.GetStatic, owner, name, descriptor.descriptor, false)

        fun putStatic(owner: Type, name: String, descriptor: Type): Handle =
            Handle(Kind.PutStatic, owner, name, descriptor.descriptor, false)

        fun invokeVirtual(owner: Type, name: String, descriptor: MethodType): Handle
                = Handle(Kind.InvokeVirtual, owner, name, descriptor.descriptor, false)

        fun invokeNewSpecial(owner: Type, name: String, descriptor: MethodType): Handle =
            Handle(Kind.NewInvokeSpecial, owner, name, descriptor.descriptor, false)

        fun invokeStatic(owner: Type, name: String, descriptor: MethodType, isInterface: Boolean): Handle =
            Handle(Kind.InvokeStatic, owner, name, descriptor.descriptor, isInterface)

        fun invokeSpecial(owner: Type, name: String, descriptor: MethodType, isInterface: Boolean): Handle =
            Handle(Kind.InvokeSpecial, owner, name, descriptor.descriptor, isInterface)

        fun invokeInterface(owner: Type, name: String, descriptor: MethodType): Handle =
            Handle(Kind.InvokeInterface, owner, name, descriptor.descriptor, true)

        fun fromOW2(handle: HandleOW2): Handle = Handle(
            Kind.fromOW2(handle.tag),
            Type.fromInternalName(handle.owner),
            handle.name,
            handle.desc,
            handle.isInterface
        )
    }

    enum class Kind constructor(internal val id: UByte) {
        GetField(Opcodes.H_GETFIELD.toUByte()),
        PutField(Opcodes.H_PUTFIELD.toUByte()),
        GetStatic(Opcodes.H_GETSTATIC.toUByte()),
        PutStatic(Opcodes.H_PUTSTATIC.toUByte()),
        InvokeVirtual(Opcodes.H_INVOKEVIRTUAL.toUByte()),
        NewInvokeSpecial(Opcodes.H_NEWINVOKESPECIAL.toUByte()),
        InvokeStatic(Opcodes.H_INVOKESTATIC.toUByte()),
        InvokeSpecial(Opcodes.H_INVOKESPECIAL.toUByte()),
        InvokeInterface(Opcodes.H_INVOKEINTERFACE.toUByte()),

        ;

        companion object {
            internal fun fromOW2(id: Int) = when (id) {
                Opcodes.H_GETFIELD -> GetField
                Opcodes.H_PUTFIELD -> PutField
                Opcodes.H_GETSTATIC -> GetStatic
                Opcodes.H_PUTSTATIC -> PutStatic
                Opcodes.H_INVOKEVIRTUAL -> InvokeVirtual
                Opcodes.H_NEWINVOKESPECIAL -> NewInvokeSpecial
                Opcodes.H_INVOKESTATIC -> InvokeStatic
                Opcodes.H_INVOKESPECIAL -> InvokeSpecial
                Opcodes.H_INVOKEINTERFACE -> InvokeInterface
                else -> throw IllegalArgumentException()
            }
        }
    }
}
