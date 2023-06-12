package com.xht.jetpack.coroutine

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

fun main() {
    //test1()
    //test2()
    //test3()
    //test4()
    //test5()
    //test6()
    //test7()
//    runBlocking {
//        val fetchTwoDocs = fetchTwoDocs()
//        log(fetchTwoDocs)
//    }
    //test8()
    //test9()
    //test10()
    //test11()
    //test12()
    //test13()
    //test14()
    //test15()
    //test16()
    //test17()
    test18()

}

/**
 * 可以使用 SupervisorJob 来实现上述效果，取消操作只会向下传播，一个子协程的运行失败不会影响到同级协程和父协程
 * 例如，以下示例中 firstChild 抛出的异常不会导致 secondChild 被取消，但当 supervisor 被取消时 secondChild 也被同时取消了
 *
 * 但是，如果异常没有被处理且 CoroutineContext 没有包含一个 CoroutineExceptionHandler 的话，异常会到达默认线程的 ExceptionHandler。
 * 在 JVM 中，异常会被打印在控制台；而在 Android 中，无论异常在那个 Dispatcher 中发生，都会直接导致应用崩溃。
 * 所以如果上述例子中移除了 firstChild 包含的 CoroutineExceptionHandler 的话，就会导致 Android 应用崩溃。
 */
fun test18() {
    runBlocking {
        val supervisorJob = SupervisorJob()
        with(CoroutineScope(coroutineContext + supervisorJob)) {
            val firstChild = launch(CoroutineExceptionHandler { _, _ -> }) {
                log("First child is failing")
                throw AssertionError("First child is cancelled")
            }
            val secondChild = launch {
                firstChild.join()
                log("First child is cancelled: ${firstChild.isCancelled}, but second one is still active")
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    log("Second child is cancelled because supervisor is cancelled")
                }
            }
            firstChild.join()
            log("Cancelling supervisor")
            //取消所有协程
            supervisorJob.cancel()
            secondChild.join()
        }
    }
}

/**
 * 如果想主动捕获异常信息，可以使用 CoroutineExceptionHandler 作为协程的上下文元素之一，在这里进行自定义日志记录或异常处理，
 * 它类似于对线程使用 Thread.uncaughtExceptionHandler。但是，CoroutineExceptionHandler 只会在预计不会由用户处理的异常上调用，
 * 因此在 async 中使用它没有任何效果，当 async 内部发生了异常且没有捕获时，那么调用 async.await() 依然会导致应用崩溃。
 * 以下代码只会捕获到 launch 抛出的异常
 */
fun test17() {
    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            log("Caught $exception")
        }
        val job = GlobalScope.launch(handler) {
            throw AssertionError()
        }

        val deferred = GlobalScope.async(handler) {
            throw ArithmeticException()
        }
        joinAll(job, deferred)
    }
}

/**
 * 一般情况下，协程的取消操作会通过协程的层次结构来进行传播：
 *          如果取消父协程或者父协程抛出异常，那么子协程都会被取消；
 *          而如果子协程被取消，则不会影响同级协程和父协程，但如果子协程抛出异常则也会导致同级协程和父协程被取消
 */
fun test16() {
    runBlocking {
        val request = launch {
            val job1 = launch {
                repeat(10) {
                    delay(300)
                    log("job1: $it")
                    if (it == 2) {
                        log("job1 canceled")
                        cancel()
                    }
                }
            }
            val job2 = launch {
                repeat(10) {
                    delay(300)
                    log("job2: $it")
                }
            }
        }
        delay(1600)
        log("parent job canceled")
        request.cancel()
        delay(1000)
    }
}

/**
 * 当一个协程在另外一个协程的协程作用域中启动时，它将通过 CoroutineScope.coroutineContext 继承其上下文，
 * 新启动的协程就被称为子协程，子协程的 Job 将成为父协程 Job 的子 Job。父协程总是会等待其所有子协程都完成后才结束自身，
 * 所以父协程不必显式跟踪它启动的所有子协程，也不必使用 Job.join 在末尾等待子协程完成。
 */
fun test15() {
    runBlocking {
        repeat(3) { i ->
            launch {
                delay((i + 1) * 200L)
                log("Coroutine $i is done")
            }
        }
        log("request: I'm done and I don't explicitly join my children that are still active")
    }
}

/**
 * 在极少数情况下我们需要在取消的协程中再调用挂起函数，此时可以使用 withContext 函数和
 * NonCancellable上下文将相应的代码包装在 withContext(NonCancellable) {...} 代码块中，
 * NonCancellable 就用于创建一个无法取消的协程作用域
 */
fun test14() {
    runBlocking {
        log("start")
        val launchA = launch {
            try {
                repeat(5) {
                    delay(50)
                    log("launchA-$it")
                }
            } finally {
                delay(50)
                log("launchA isCompleted")
            }
        }
        val launchB = launch {
            try {
                repeat(5) {
                    delay(50)
                    log("launchB-$it")
                }
            } finally {
                withContext(NonCancellable) {
                    delay(50)
                    log("launchB isCompleted")
                }
            }
        }
        //延时一百毫秒，保证两个协程都已经被启动了
        delay(200)
        launchA.cancel()
        launchB.cancel()
        log("end")
    }
}

/**
 * 可取消的挂起函数在取消时会抛出 CancellationException，
 * 可以依靠try {...} finally {...} 或者 Kotlin 的 use 函数在取消协程后释放持有的资源。
 */
fun test13() {
    runBlocking {
        val job = launch {
            try {
                repeat(1000) { i ->
                    log("job: I'm sleeping $i ...")
                    delay(500L)
                }
            } catch (e: Throwable) {
                log(e.message)
            } finally {
                log("job: I'm running finally")
            }
        }
        delay(1300L)
        log("main: I'm tired of waiting!")
        job.cancelAndJoin()
        log("main: Now I can quit.")
    }
}

fun test12() {
    runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) {
                if (isActive) {
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        log("job: I'm sleeping ${i++} ...")
                        nextPrintTime += 500L
                    }
                } else {
                    return@launch
                }
            }
        }
        delay(1300L)
        log("main: I'm tired of waiting!")
        job.cancelAndJoin()
        log("main: Now I can quit.")
    }
}

/**
 * 取消协程
 * cancel()
 * join()
 * cancelAndJoin()
 */
fun test11() {
    runBlocking {
        val job = launch {
            repeat(50) { i ->
                log("job: I`m sleeping $i ...")
                delay(500L)
            }
        }
        delay(2300L)
        log("main: I'm tired of waiting!")
        job.cancel()
//        job.join()
//        job.cancelAndJoin()
        log("main: Now I can quit.")
    }
}

/**
 * CoroutineName 用于为协程指定一个名字，方便调试和定位问题
 */
fun test10() {
    runBlocking<Unit>(CoroutineName("RunBlocking")) {
        log("start")
        launch(CoroutineName("MainCoroutine")) {
            launch(CoroutineName("Coroutine#A")) {
                delay(400)
                log("launch A")
            }
            launch(CoroutineName("Coroutine#B")) {
                delay(300)
                log("launch B")
            }
        }
    }
}

/**
 * CoroutineContext 包含一个 CoroutineDispatcher（协程调度器）用于指定执行协程的目标载体，即 运行于哪个线程。
 * Dispatchers.Default。默认调度器，适合用于执行占用大量 CPU 资源的任务。例如：对列表排序和解析 JSON
 * Dispatchers.IO。适合用于执行磁盘或网络 I/O 的任务。例如：使用 Room 组件、读写磁盘文件，执行网络请求
 * Dispatchers.Unconfined。对执行协程的线程不做限制，可以直接在当前调度器所在线程上执行
 * Dispatchers.Main。使用此调度程序可用于在 Android 主线程上运行协程，只能用于与界面交互和执行快速工作，
 *                  例如：更新 UI、调用 LiveData.setValue
 */
private fun test9() {
    runBlocking<Unit> {
        launch {
            log("main runBlocking")
        }
        launch(Dispatchers.Default) {
            log("default")
            launch(Dispatchers.Unconfined) {
                log("Unconfined 1")
            }
        }
        launch(Dispatchers.IO) {
            log("IO")
            launch(Dispatchers.Unconfined) {}
            log("Unconfined 2")
        }
        launch(newSingleThreadContext("MyOwnThread")) {
            log("newSingleThreadContext")
            launch(Dispatchers.Unconfined) {
                log("Unconfined 4")
            }
        }
        launch(Dispatchers.Unconfined) {
            log("Unconfined 3")
        }
        GlobalScope.launch {
            log("GlobalScope")
        }
    }
}

val job = Job()
val scope = CoroutineScope(job + Dispatchers.IO)

private fun test8(): Unit = runBlocking {
    log("job is $job")
    val job = scope.launch {
        try {
            delay(3000)
        } catch (e: CancellationException) {
            log("job is canceled")
            throw e
        }
        log("end")
    }
    delay(1000)
    log("scope job is ${scope.coroutineContext[Job]}")
    scope.coroutineContext[Job]?.cancel()
}

suspend fun fetchTwoDocs() = coroutineScope {
    val deferredOne = async {
        delay(2000)
        fetchDoc(1)
    }
    val deferredTwo = async {
        delay(3000)
        fetchDoc(2)
    }
    val await1 = deferredOne.await()
    val await2 = deferredTwo.await()
    await1 + await2
}

fun fetchDoc(i: Int): String {
    return "接口 $i 返回的数据"
}

/**
 * await 获取结果
 * CoroutineStart.LAZY 不会主动启动协程，而是直到调用async.await()或者async.satrt()后才会启动（即懒加载模式）
 */
private fun test7() {
    val time = measureTimeMillis {
        runBlocking {
            val asyncA = async(start = CoroutineStart.LAZY) {
                delay(3000)
                1
            }
            val asyncB = async(start = CoroutineStart.LAZY) {
                delay(4000)
                2
            }
//            asyncA.start()
//            asyncB.start()
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