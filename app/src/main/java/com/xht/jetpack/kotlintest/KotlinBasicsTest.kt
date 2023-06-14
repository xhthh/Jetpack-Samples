package com.xht.jetpack.kotlintest

fun main() {
    val derived = Derived("xi", "ha")
    println(derived.size)
    //foo2()
}

fun foo2() {
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return@forEach // 局部返回到该 lambda 表达式的调⽤者，即 forEach 循环
        print(it)
    }
    print(" done with implicit label")
}

/**
 * 1245this point is unreachable
 */
fun foo1() {
    listOf(1, 2, 3, 4, 5).forEach list@{
        if (it == 3) return@list
        print(it)
    }
    println("this point is unreachable")
}

/**
 * 12
 */
fun foo() {
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return // ⾮局部直接返回到 foo() 的调⽤者
        print(it)
    }
    println("this point is unreachable")
}

open class Base(val name: String) {
    init {
        println("Initializing Base")
    }

    open val size: Int =
        name.length.also { println("Initializing size in Base: $it") }
}

class Derived(
    name: String,
    val lastName: String,
) : Base(name.capitalize().also { println("Argument for Base: $it") }) {
    init {
        println("Initializing Derived")
    }

    override val size: Int =
        (super.size + lastName.length).also { println("Initializing size in Derived: $it") }
}