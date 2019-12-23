@file:Suppress("EXPERIMENTAL_UNSIGNED_LITERALS")

package com.anatawa12.asm

import org.objectweb.asm.TypeReference

/**
 * Created by anatawa12 on 2019/12/21.
 */
/**
 * Type annotation target. see [jvm specification 4.7.20](https://docs.oracle.com/javase/specs/jvms/se13/html/jvms-4.html#jvms-4.7.20)
 */
sealed class TypeAnnotationTarget(val tag: UByte) {
    internal abstract val ow2: Int

    companion object {
        internal fun fromASM(typeReference: TypeReference): TypeAnnotationTarget = typeReference.run {
            when (sort) {
                TypeReference.CLASS_TYPE_PARAMETER -> ClassTypeParameterTarget(typeParameterIndex.toUByte())
                TypeReference.METHOD_TYPE_PARAMETER -> MethodTypeParameterTarget(typeParameterIndex.toUByte())
                TypeReference.CLASS_EXTENDS -> ClassSuperTypeTarget(superTypeIndex.toUShort())
                TypeReference.CLASS_TYPE_PARAMETER_BOUND -> ClassTypeParameterBoundTarget(
                    typeParameterIndex.toUByte(),
                    typeParameterBoundIndex.toUByte()
                )
                TypeReference.METHOD_TYPE_PARAMETER_BOUND -> MethodTypeParameterBoundTarget(
                    typeParameterIndex.toUByte(),
                    typeParameterBoundIndex.toUByte()
                )
                TypeReference.FIELD -> FieldEmptyTarget
                TypeReference.METHOD_RETURN -> ReturnEmptyTarget
                TypeReference.METHOD_RECEIVER -> ReceiverEmptyTarget
                TypeReference.METHOD_FORMAL_PARAMETER -> MethodFormalParameterTarget(formalParameterIndex.toUByte())
                TypeReference.THROWS -> MethodThrowsTarget(exceptionIndex.toUShort())
                //TypeReference.LOCAL_VARIABLE -> {}
                //TypeReference.RESOURCE_VARIABLE -> {}
                TypeReference.EXCEPTION_PARAMETER -> MethodCatchTarget(tryCatchBlockIndex.toUShort())
                TypeReference.INSTANCEOF -> InstanceOfOffsetTarget
                TypeReference.NEW -> NewOffsetTarget
                TypeReference.CONSTRUCTOR_REFERENCE -> ConstructorReferenceOffsetTarget
                TypeReference.METHOD_REFERENCE -> MethodReferenceOffsetTarget
                TypeReference.CAST -> CastTypeArgumentTarget(typeArgumentIndex.toUByte())
                TypeReference.CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT -> GenericConstructorTypeArgumentTarget(
                    typeArgumentIndex.toUByte()
                )
                TypeReference.METHOD_INVOCATION_TYPE_ARGUMENT -> MethodInvocationTypeArgumentTarget(typeArgumentIndex.toUByte())
                TypeReference.CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT -> ConstructorReferenceTypeArgumentTarget(
                    typeArgumentIndex.toUByte()
                )
                TypeReference.METHOD_REFERENCE_TYPE_ARGUMENT -> MethodReferenceTypeArgumentTarget(typeArgumentIndex.toUByte())
                else -> throw IllegalArgumentException("unsupported sort: 0x${sort.toString(16).padStart(2, ' ')}")
            }
        }
    }
}

sealed class CodeTarget(tag: UByte) : TypeAnnotationTarget(tag)

sealed class TypeParameterTarget(val index: UByte, tag: UByte) : TypeAnnotationTarget(tag) {
    override val ow2: Int get() = TypeReference.newTypeParameterReference(tag.toInt(), index.toInt()).value
}

sealed class SuperTypeTarget(val index: UShort, tag: UByte) : TypeAnnotationTarget(tag) {
    override val ow2: Int get() = TypeReference.newSuperTypeReference(index.toInt()).value
}

sealed class TypeParameterBoundTarget(val typeParameterIndex: UByte, val boundIndex: UByte, tag: UByte) :
    TypeAnnotationTarget(tag) {
    override val ow2: Int
        get() = TypeReference.newTypeParameterBoundReference(
            tag.toInt(),
            typeParameterIndex.toInt(),
            boundIndex.toInt()
        ).value
}

sealed class EmptyTarget(tag: UByte) : TypeAnnotationTarget(tag) {
    override val ow2: Int get() = TypeReference.newTypeReference(tag.toInt()).value
}

sealed class FormalParameterTarget(val index: UByte, tag: UByte) : TypeAnnotationTarget(tag) {
    override val ow2: Int get() = TypeReference.newFormalParameterReference(index.toInt()).value
}

sealed class ThrowsTarget(val index: UShort, tag: UByte) : TypeAnnotationTarget(tag) {
    override val ow2: Int get() = TypeReference.newExceptionReference(index.toInt()).value
}

sealed class CatchTarget(val index: UShort, tag: UByte) : TypeAnnotationTarget(tag) {
    override val ow2: Int get() = TypeReference.newTryCatchReference(index.toInt()).value
}

sealed class OffsetTarget(tag: UByte) : CodeTarget(tag) {
    override val ow2: Int get() = TypeReference.newTypeReference(tag.toInt()).value
}

sealed class TypeArgumentTarget(val typeArgumentIndex: UByte, tag: UByte) : CodeTarget(tag) {
    override val ow2: Int get() = TypeReference.newTypeArgumentReference(tag.toInt(), typeArgumentIndex.toInt()).value
}

/*
OffsetTarget
TypeArgumentTarget
 */

//////////////

/**
 * type parameter declaration of generic class or interface
 */
class ClassTypeParameterTarget(index: UByte) : TypeParameterTarget(index, 0x00u)

/**
 * type parameter declaration of generic method or constructor
 */
class MethodTypeParameterTarget(index: UByte) : TypeParameterTarget(index, 0x01u)

/**
 * type in extends or implements clause of class declaration (including the direct superclass or direct superinterface of an anonymous class declaration), or in extends clause of interface declaration
 */
class ClassSuperTypeTarget(index: UShort) : SuperTypeTarget(index, 0x10u)

/**
 * type in bound of type parameter declaration of generic class or interface
 */
class ClassTypeParameterBoundTarget(typeParameterIndex: UByte, boundIndex: UByte) :
    TypeParameterBoundTarget(typeParameterIndex, boundIndex, 0x11u)

/**
 * type in bound of type parameter declaration of generic method or constructor
 */
class MethodTypeParameterBoundTarget(typeParameterIndex: UByte, boundIndex: UByte) :
    TypeParameterBoundTarget(typeParameterIndex, boundIndex, 0x12u)

/**
 * type in field declaration
 */
object FieldEmptyTarget : EmptyTarget(0x13u)

/**
 * return type of method, or type of newly constructed object
 */
object ReturnEmptyTarget : EmptyTarget(0x14u)

/**
 * receiver type of method or constructor
 */
object ReceiverEmptyTarget : EmptyTarget(0x15u)

/**
 * type in formal parameter declaration of method, constructor, or lambda expression
 */
class MethodFormalParameterTarget(index: UByte) : FormalParameterTarget(index, 0x16u)

/**
 * type in throws clause of method or constructor
 */
class MethodThrowsTarget(index: UShort) : ThrowsTarget(index, 0x17u)

/**
 * type in exception parameter declaration
 */
class MethodCatchTarget(index: UShort) : CatchTarget(index, 0x42u)

/**
 * type in instanceof expression
 */
object InstanceOfOffsetTarget : OffsetTarget(0x43u)

/**
 * type in new expression
 */
object NewOffsetTarget : OffsetTarget(0x44u)

/**
 * type in method reference expression using ::new
 */
object ConstructorReferenceOffsetTarget : OffsetTarget(0x45u)

/**
 * type in method reference expression using ::Identifier
 */
object MethodReferenceOffsetTarget : OffsetTarget(0x46u)

/**
 * type in cast expression
 */
class CastTypeArgumentTarget(typeArgumentIndex: UByte) :
    TypeArgumentTarget(typeArgumentIndex, 0x47u)

/**
 * type argument for generic constructor in new expression or explicit constructor invocation statement
 */
class GenericConstructorTypeArgumentTarget(typeArgumentIndex: UByte) :
    TypeArgumentTarget(typeArgumentIndex, 0x48u)

/**
 * type argument for generic method in method invocation expression
 */
class MethodInvocationTypeArgumentTarget(typeArgumentIndex: UByte) :
    TypeArgumentTarget(typeArgumentIndex, 0x49u)

/**
 * type argument for generic constructor in method reference expression using ::new
 */
class ConstructorReferenceTypeArgumentTarget(typeArgumentIndex: UByte) :
    TypeArgumentTarget(typeArgumentIndex, 0x4Au)

/**
 * type argument for generic method in method reference expression using ::Identifier
 */
class MethodReferenceTypeArgumentTarget(typeArgumentIndex: UByte) :
    TypeArgumentTarget(typeArgumentIndex, 0x4Bu)
