package com.anatawa12.asm

import org.objectweb.asm.Label as LabelOW2

/**
 * Created by anatawa12 on 2019/12/22.
 */
class Label internal constructor(internal val ow2: LabelOW2) {
    constructor() : this(LabelOW2())
}

