package com.anatawa12.asm

/**
 * Created by anatawa12 on 2019/12/21.
 */
interface ModuleVisitor {

    /**
     * Visit the main class of the current module.
     *
     * @param mainClass the internal name of the main class of the current module.
     */
    fun visitMainClass(mainClass: InternalName)

    /**
     * Visit a package of the current module.
     *
     * @param packageName the internal name of a package.
     */
    fun visitPackage(packageName: InternalName)

    /**
     * Visits a dependence of the current module.
     *
     * @param module the fully qualified name (using dots) of the dependence.
     * @param access the access flag of the dependence among `ACC_TRANSITIVE`, `ACC_STATIC_PHASE`, `ACC_SYNTHETIC` and `ACC_MANDATED`.
     * @param version the module version at compile time, or null.
     */
    fun visitRequire(module: String, access: Int, version: String?)

    /**
     * Visit an exported package of the current module.
     *
     * @param packageName the internal name of the exported package.
     * @param access the access flag of the exported package, valid values are among `ACC_SYNTHETIC` and `ACC_MANDATED`.
     * @param modules the fully qualified names (using dots) of the modules that can access the public
     * classes of the exported package, or null.
     */
    fun visitExport(packageName: InternalName, access: Int, vararg modules: String)

    /**
     * Visit an open package of the current module.
     *
     * @param packageName the internal name of the opened package.
     * @param access the access flag of the opened package, valid values are among `ACC_SYNTHETIC` and `ACC_MANDATED`.
     * @param modules the fully qualified names (using dots) of the modules that can use deep
     * reflection to the classes of the open package, or null.
     */
    fun visitOpen(packageName: InternalName, access: Int, vararg modules: String)

    /**
     * Visit a service used by the current module. The name must be the internal name of an interface
     * or a class.
     *
     * @param service the internal name of the service.
     */
    fun visitUse(service: InternalName)

    /**
     * Visit an implementation of a service.
     *
     * @param service the internal name of the service.
     * @param providers the internal names of the implementations of the service (there is at least
     * one provider).
     */
    @Suppress("FORBIDDEN_VARARG_PARAMETER_TYPE")
    fun visitProvide(service: InternalName, vararg providers: InternalName)

    /**
     * Visits the end of the module.
     */
    fun visitEnd()
}
