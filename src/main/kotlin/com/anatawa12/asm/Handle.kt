package com.anatawa12.asm

import org.objectweb.asm.Opcodes

/**
 * Created by anatawa12 on 2019/12/22.
 */
/**
 * Handle method for invokedynamic.
 */
class Handle private constructor(
    val kind: Kind,
    val owner: InternalName,
    val name1: String,
    val descriptor: Descriptor,
    val isInterface: Boolean
) {
    companion object {
        fun getField(owner: InternalName, name: String, descriptor: Descriptor): Handle
                = Handle(Kind.GetField, owner, name, descriptor, false)
        fun putField(owner: InternalName, name: String, descriptor: Descriptor): Handle
                = Handle(Kind.PutField, owner, name, descriptor, false)
        fun getStatic(owner: InternalName, name: String, descriptor: Descriptor): Handle
                = Handle(Kind.GetStatic, owner, name, descriptor, false)
        fun putStatic(owner: InternalName, name: String, descriptor: Descriptor): Handle
                = Handle(Kind.PutStatic, owner, name, descriptor, false)

        fun invokeVirtual(owner: InternalName, name: String, descriptor: Descriptor): Handle
                = Handle(Kind.InvokeVirtual, owner, name, descriptor, false)
        fun invokeNewSpecial(owner: InternalName, name: String, descriptor: Descriptor): Handle
                = Handle(Kind.NewInvokeSpecial, owner, name, descriptor, false)

        fun invokeStatic(owner: InternalName, name: String, descriptor: Descriptor, isInterface: Boolean): Handle
                = Handle(Kind.InvokeStatic, owner, name, descriptor, isInterface)
        fun invokeSpecial(owner: InternalName, name: String, descriptor: Descriptor, isInterface: Boolean): Handle
                = Handle(Kind.InvokeSpecial, owner, name, descriptor, isInterface)

        fun invokeInterface(owner: InternalName, name: String, descriptor: Descriptor): Handle
                = Handle(Kind.InvokeInterface, owner, name, descriptor, true)
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
    }
}
