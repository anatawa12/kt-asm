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
    val owner: Type,
    val name1: String,
    val descriptor: Descriptor,
    val isInterface: Boolean
) {
    companion object {
        fun getField(owner: Type, name: String, descriptor: Type): Handle
                = Handle(Kind.GetField, owner, name, descriptor.descriptor, false)
        fun putField(owner: Type, name: String, descriptor: Type): Handle
                = Handle(Kind.PutField, owner, name, descriptor.descriptor, false)
        fun getStatic(owner: Type, name: String, descriptor: Type): Handle
                = Handle(Kind.GetStatic, owner, name, descriptor.descriptor, false)
        fun putStatic(owner: Type, name: String, descriptor: Type): Handle
                = Handle(Kind.PutStatic, owner, name, descriptor.descriptor, false)

        fun invokeVirtual(owner: Type, name: String, descriptor: MethodType): Handle
                = Handle(Kind.InvokeVirtual, owner, name, descriptor.descriptor, false)
        fun invokeNewSpecial(owner: Type, name: String, descriptor: MethodType): Handle
                = Handle(Kind.NewInvokeSpecial, owner, name, descriptor.descriptor, false)

        fun invokeStatic(owner: Type, name: String, descriptor: MethodType, isInterface: Boolean): Handle
                = Handle(Kind.InvokeStatic, owner, name, descriptor.descriptor, isInterface)
        fun invokeSpecial(owner: Type, name: String, descriptor: MethodType, isInterface: Boolean): Handle
                = Handle(Kind.InvokeSpecial, owner, name, descriptor.descriptor, isInterface)

        fun invokeInterface(owner: Type, name: String, descriptor: MethodType): Handle
                = Handle(Kind.InvokeInterface, owner, name, descriptor.descriptor, true)
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
