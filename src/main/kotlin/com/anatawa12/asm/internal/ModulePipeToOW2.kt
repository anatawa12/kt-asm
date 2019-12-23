package com.anatawa12.asm.internal

import com.anatawa12.asm.InternalName
import com.anatawa12.asm.ModuleVisitor
import com.anatawa12.asm.Type
import org.objectweb.asm.ModuleVisitor as ModuleVisitorOW2

/**
 * Created by anatawa12 on 2019/12/23.
 */
class ModulePipeToOW2(
    val ow2: ModuleVisitorOW2
) : ModuleVisitor {
    override fun visitMainClass(mainClass: Type) {
        ow2.visitMainClass(mainClass.internalName)
    }

    override fun visitPackage(packageName: InternalName) {
        ow2.visitPackage(packageName)
    }

    override fun visitRequire(module: String, access: Int, version: String?) {
        ow2.visitRequire(module, access, version)
    }

    override fun visitExport(packageName: InternalName, access: Int, vararg modules: String) {
        ow2.visitExport(packageName, access, *modules)
    }

    override fun visitOpen(packageName: InternalName, access: Int, vararg modules: String) {
        ow2.visitOpen(packageName, access, *modules)
    }

    override fun visitUse(service: Type) {
        ow2.visitUse(service.internalName)
    }

    override fun visitProvide(service: Type, vararg providers: Type) {
        ow2.visitProvide(service.internalName, *providers.mapArray { it.internalName })
    }

    override fun visitEnd() {
        ow2.visitEnd()
    }
}
