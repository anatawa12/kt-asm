package com.anatawa12.asm.internal

import com.anatawa12.asm.InternalName
import com.anatawa12.asm.ModuleVisitor
import com.anatawa12.asm.Type
import org.objectweb.asm.Opcodes
import org.objectweb.asm.ModuleVisitor as ModuleVisitorOW2

/**
 * Created by anatawa12 on 2019/12/23.
 */
internal class ModulePipeFromOW2(
    val moduleVisitor: ModuleVisitor
) : ModuleVisitorOW2(Opcodes.ASM7) {
    override fun visitMainClass(mainClass: String) {
        moduleVisitor.visitMainClass(Type.fromInternalName(mainClass))
    }

    override fun visitPackage(packageName: InternalName) {
        moduleVisitor.visitPackage(packageName)
    }

    override fun visitRequire(module: String, access: Int, version: String?) {
        moduleVisitor.visitRequire(module, access, version)
    }

    override fun visitExport(packageName: InternalName, access: Int, vararg modules: String) {
        moduleVisitor.visitExport(packageName, access, *modules)
    }

    override fun visitOpen(packageName: InternalName, access: Int, vararg modules: String) {
        moduleVisitor.visitOpen(packageName, access, *modules)
    }

    override fun visitUse(service: String) {
        moduleVisitor.visitUse(Type.fromInternalName(service))
    }

    override fun visitProvide(service: String, vararg providers: String) {
        moduleVisitor.visitProvide(
            Type.fromInternalName(service),
            *providers.mapArray { Type.fromInternalName(it) })
    }

    override fun visitEnd() {
        moduleVisitor.visitEnd()
    }
}
