@file:Suppress("EXPERIMENTAL_UNSIGNED_LITERALS")

package com.anatawa12.asm

/**
 * Created by anatawa12 on 2019/12/21.
 */
/**
 * Type annotation target. see [jvm specification 4.7.20](https://docs.oracle.com/javase/specs/jvms/se13/html/jvms-4.html#jvms-4.7.20)
 */
sealed class TypeAnnotationTarget(val tag: UByte)
sealed class CodeTarget(tag: UByte) : TypeAnnotationTarget(tag)

sealed class TypeParameterTarget(val index: UByte, tag: UByte) : TypeAnnotationTarget(tag)
sealed class SuperTypeTarget(val index: UShort, tag: UByte) : TypeAnnotationTarget(tag)
sealed class TypeParameterBoundTarget(val typeParameterIndex: UByte, val boundIndex: UByte, tag: UByte) :
    TypeAnnotationTarget(tag)

sealed class EmptyTarget(tag: UByte) : TypeAnnotationTarget(tag)
sealed class FormalParameterTarget(val index: UByte, tag: UByte) : TypeAnnotationTarget(tag)
sealed class ThrowsTarget(val index: UShort, tag: UByte) : TypeAnnotationTarget(tag)
sealed class LocalVarTarget(val table: List<LocalVarTargetTableElement>, tag: UByte) : TypeAnnotationTarget(tag)
sealed class CatchTarget(val index: UShort, tag: UByte) : TypeAnnotationTarget(tag)
sealed class OffsetTarget(tag: UByte) : CodeTarget(tag)
sealed class TypeArgumentTarget(val typeArgumentIndex: UByte, tag: UByte) :
    CodeTarget(tag)

class LocalVarTargetTableElement(val start: UShort, val length: UShort, val index: UShort)

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
 * type in local variable declaration
 */
class MethodLocalVarTarget(table: List<LocalVarTargetTableElement>) : LocalVarTarget(table, 0x40u)

/**
 * type in resource variable declaration
 */
class ResourceLocalVarTarget(table: List<LocalVarTargetTableElement>) : LocalVarTarget(table, 0x41u)

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
