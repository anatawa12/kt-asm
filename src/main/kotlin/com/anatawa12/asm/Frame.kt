@file:Suppress("CanBeParameter")

package com.anatawa12.asm

import org.objectweb.asm.Opcodes

/**
 * Created by anatawa12 on 2019/12/22.
 */

/**
 * a stack frame.
 */
sealed class StackMapFrame(
    internal val type: Int,
    internal val numLocal: Int = 0,
    internal val localValues: List<VariableType> = listOf(),
    internal val numStack: Int = 0,
    internal val stackValues: List<VariableType> = listOf()
)

/**
 * representing complete frame data for internal.
 */
class NewFrame(val locals: List<VariableType>, val stack: List<VariableType>)
    : StackMapFrame(Opcodes.F_NEW, locals.size, locals, stack.size, stack)

/**
 * representing frame with exactly the same locals as the previous frame and with the empty stack.
 */
object SameFrame : StackMapFrame(Opcodes.F_SAME)

/**
 * representing frame with exactly the same locals as the previous frame and with single value on the stack.
 */
class Same1Frame(val stack: VariableType) : StackMapFrame(Opcodes.F_SAME1, numStack = 1, stackValues = listOf(stack))

/**
 * representing frame with current locals are the same as the locals in the previous frame, except that additional locals are defined and with the empty stack.
 */
class AppendFrame : StackMapFrame {
    val addLocals: List<VariableType>

    /**
     * @param addLocals appending local variables. number of [addLocals] must be in 1..3
     */
    constructor(addLocals: List<VariableType>) : super(Opcodes.F_SAME1, addLocals.size, addLocals) {
        check(addLocals.size in 1..3) { "addLocals too long or empty" }
        this.addLocals = addLocals
    }

    /**
     * @param addLocals appending local variables. number of [addLocals] must be in 1..3
     */
    constructor(vararg addLocals: VariableType) : this(addLocals.toList())
}

/**
 * representing frame with current locals are the same as the locals in the previous frame,
 *    except that the last 1-3 locals are absent and with the empty stack.
 */
class ChopFrame(val numberOfLocal: Int) : StackMapFrame(Opcodes.F_CHOP, numLocal = numberOfLocal)

/**
 * representing complete frame data.
 */
class FullFrame(val locals: List<VariableType>, val stack: List<VariableType>)
    : StackMapFrame(Opcodes.F_FULL, locals.size, locals, stack.size, stack)

/**
 * The type of local variable or stack element. Long and Double are represented by a single element.
 */
sealed class VariableType(internal val ow2: Any) {
    /**
     * the top type of all Variable
     */
    object Top : VariableType(Opcodes.TOP)

    object Integer : VariableType(Opcodes.INTEGER)
    object Float : VariableType(Opcodes.FLOAT)
    object Double : VariableType(Opcodes.DOUBLE)
    object Long : VariableType(Opcodes.LONG)
    object Null : VariableType(Opcodes.NULL)
    object UninitializedThis : VariableType(Opcodes.UNINITIALIZED_THIS)

    class Object(val name: Type) : VariableType(name.internalName)

    /**
     * uninitialized value created at [at].
     */
    class Uninitialized(val at: Label) : VariableType(at.ow2)
}
