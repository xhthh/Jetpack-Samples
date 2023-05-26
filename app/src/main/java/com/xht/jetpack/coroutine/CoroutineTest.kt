package com.xht.jetpack.coroutine

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {
    //test1()
    //test2()
    //test3()
    //test4()
    //test5()
    //test6()
    test7()
}

private fun test7() {
    val time = measureTimeMillis {
        runBlocking {
            val asyncA = async {
                delay(3000)
                1
            }
            val asyncB = async {
                delay(4000)
                2
            }
            log(asyncA.await() + asyncB.await())
        }
    }
    log(time)
}

/**
 * launch 是一个作用于 CoroutineScope 的扩展函数，用于在不阻塞当前线程的情况下启动一个协程，并返回对该协程任务的引用，即 Job 对象。
 *
 */
private fun test6() {
    val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
        for (i in 0..100) {
            //每循环一次均延迟一百毫秒
            delay(100)
        }
    }
    job.invokeOnCompletion {
        log("invokeOnCompletion：$it")
    }
    log("1. job.isActive：${job.isActive}")
    log("1. job.isCancelled：${job.isCancelled}")
    log("1. job.isCompleted：${job.isCompleted}")

    job.start()

    log("2. job.isActive：${job.isActive}")
    log("2. job.isCancelled：${job.isCancelled}")
    log("2. job.isCompleted：${job.isCompleted}")

    //休眠四百毫秒后再主动取消协程
    Thread.sleep(400)
    job.cancel(CancellationException("test"))

    //休眠四百毫秒防止JVM过快停止导致 invokeOnCompletion 来不及回调
    Thread.sleep(400)

    log("3. job.isActive：${job.isActive}")
    log("3. job.isCancelled：${job.isCancelled}")
    log("3. job.isCompleted：${job.isCompleted}")
}

/**
 * supervisorScope 函数用于创建一个使用了 SupervisorJob 的 coroutineScope，
 * 该作用域的特点就是抛出的异常不会连锁取消同级协程和父协程。
 */
private fun test5() {
    runBlocking {
        launch {
            delay(100)
            log("Task from runBlocking")
        }
        supervisorScope {
            launch {
                delay(500)
                log("Task throw Exception")
                throw Exception("failed")
            }
            launch {
                delay(600)
                log("Task from nested launch")
            }
        }
        log("Coroutine scope is over")
    }
}

/**
 * coroutineScope 函数用于创建一个独立的协程作用域，直到所有启动的协程都完成后才结束自身。
 * runBlocking 和 coroutineScope 看起来很像，因为它们都需要等待其内部所有相同作用域的协程结束后才会结束自己。
 * 两者的主要区别在于 runBlocking 方法会阻塞当前线程，而 coroutineScope不会，而是会挂起并释放底层线程以供其它协程使用。
 * 基于这个差别，runBlocking 是一个普通函数，而 coroutineScope 是一个挂起函数。
 */
private fun test4() {
    runBlocking {
        launch {
            delay(100)
            log("Task from runBlocking")
        }
        coroutineScope {
            launch {
                delay(500)
                log("Task from nested launch")
            }
            delay(50)
            log("Task from coroutine scope")
        }
        log("Coroutine scope is over")
    }
}

/**
 * runBlocking。一个顶层函数，和 GlobalScope 不一样，它会阻塞当前线程直到其内部所有相同作用域的协程执行结束。
 * runBlocking 函数的第二个参数即协程的执行体，该参数被声明为 CoroutineScope 的扩展函数，
 * 因此执行体就包含了一个隐式的 CoroutineScope，所以在 runBlocking 内部可以来直接启动协程。
 * 只有当内部相同作用域的所有协程都运行结束后，声明在 runBlocking 之后的代码才能执行，即 runBlocking 会阻塞其所在线程。
 * runBlocking 本身带有阻塞线程的意味，但其内部运行的协程又是非阻塞的。
 */
private fun test3() {
    log("start")
    runBlocking {
        launch {
            repeat(3) {
                delay(100)
                log("launchA - $it")
            }
        }
        launch {
            repeat(3) {
                delay(100)
                log("launchB - $it")
            }
        }
        GlobalScope.launch {
            repeat(3) {
                delay(120)
                log("GlobalScope - $it")
            }
        }
    }
    log("end")
}

private fun test2() {
    log("start")
    GlobalScope.launch {
        launch {
            delay(400)
            log("launch A")
        }
        launch {
            delay(300)
            log("launch B")
        }
        log("GlobalScope")
    }
    log("end")
    Thread.sleep(500)
}

/**
 * GlobalScope。即全局协程作用域，在这个范围内启动的协程可以一直运行直到应用停止运行。
 * GlobalScope 本身不会阻塞当前线程，且启动的协程相当于守护线程，不会阻止 JVM 结束运行。
 */
private fun test1() {
    GlobalScope.launch(context = Dispatchers.IO) {
        delay(1000)
        log("launch")
    }
    //主动休眠两秒，防止 JVM 过快退出
    Thread.sleep(2000)
    log("end")
}

/**
 * 挂起函数不会阻塞其所在线程，而是会将协程挂起，在特定的时候才再恢复执行
 */
suspend fun fetchDocs() {                             // Dispatchers.Main
    val result = get("https://developer.android.com") // Dispatchers.IO for `get`
    log(result)                                      // Dispatchers.Main
}

suspend fun get(url: String) = withContext(Dispatchers.IO) {
    //耗时操作，获取接口收据
    "接口数据返回"
}


fun log(msg: Any?) = println("[${Thread.currentThread().name}] $msg")