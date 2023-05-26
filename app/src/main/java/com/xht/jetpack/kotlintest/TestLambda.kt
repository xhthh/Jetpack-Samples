package com.xht.jetpack.kotlintest

class A {
    val aa = "NAME A\n"

    fun doSomething() {
        print("A do something\n")
    }
}

class B {
    val bb = "NAME B\n"

    fun doSomething() {
        print("B do something\n")
    }
}

private fun test3() {
    demoOne {
        it.doSomething()
    }
    demoTwo {
        doSomething()
        this.doSomething()
    }
}

fun main() {
    //test1()
    //test2()
    test3()
}

fun demoOne(block: (A) -> Unit) {
    block.invoke(A())
    print("demoOne Done\n")
}

fun demoTwo(block: A.() -> Unit) {
//    block(A())
    block.invoke(A())
//    A().block()
//    A().apply(block)
//    A().apply {
//        block(this)
//    }
//    A().apply {
//        this.block()
//    }
    print("demoTwo Done\n")
}

private fun test1() {
    //这个lambda变量，接收一个A类型的参数，并执行一些操作
    val block: (A) -> Unit = { objectA: A ->
        objectA.doSomething()
    }

//    val block: (A) -> Unit = {
//        it.doSomething()
//    }
//
//    demoOne(block)

    val b = B()

    demoOne {
        print(b.bb)
        b.doSomething()
        print(it.aa)
        it.doSomething()
    }
}

private fun test2() {
//    val block: A.() -> Unit = { Afunc: A.() ->
//            Afunc()
//    }
//    val block: A.() -> Unit = { Afunc: A ->
//        Afunc()
//    }
//    val block: A.() -> Unit = {
//        //todo
//    }
//    demoTwo(block)

    fun doSomething() {
        print("main do something\n")
    }

    var b = B()

    demoTwo {
        //同样注意，这里我们是直接访问的，说明this指针指向A
        print(aa)
        doSomething()
        this.doSomething()
        print(b.bb)
        b.doSomething()
    }
}