package concurrent.ab

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock


/**
 * create by xuexuan
 * time 2021/3/11 11:41 上午
 *
 * 线程 A 打印单数，线程B打印双数 ，A B 交替打印出  A-1 b-2 a-3 b-4
 */


//交替打印，有问题
//强依赖于 各线程的启动顺序，间隔时间。两线程 并行执行可能出现 死锁
class ThreadPrinter constructor(name: String, private val prev: Object, private val self: Object) : Thread() {
    override fun run() {

        while (i <= 100) { // 多线程并发，不能用if，必须使用whil循环
//            println("当前 thread = ${this.name} count = ${count}")

            synchronized(prev) {
                println("当前 thread = ${this.name} preAny = ${prev} 准备进入自己的同步区 ")

                // 先获取 prev 锁
                synchronized(self) {
                    println("当前 thread = ${this.name} selfAny = ${self} 开始打印 ")

                    // 再获取 self 锁
                    println("---$name----${i++}") //打印
//                    count--
                    println("当前 thread = ${this.name} selfAny = ${self} 调用notify ")

                    self.notifyAll() // 唤醒其他线程竞争self锁，注意此时self锁并未立即释放。

                    //在这里调用该语句，会休眠当前线程，并释放prev对象锁，self锁依旧持有，其它线程无法进入对应的synchronized
                    //https://blog.csdn.net/qq_27680317/article/details/78567615
//                    prev.wait() // 立即释放 prev锁，当前线程休眠，等待唤醒

                }
                //此时执行完self的同步块，这时self锁才释放。
                try {
                    println("当前 thread = ${this.name} selfAny  = ${self}执行完成， preAny = ${prev} 调用 wait ")

                    prev.wait() // 立即释放 prev锁，当前线程休眠，等待唤醒
                    /**
                     * JVM会在wait()对象锁的线程中随机选取一线程，赋予其对象锁，唤醒线程，继续执行。
                     */
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}

private var i = 0

fun perform() {
    val objectA = Object()
    val objectB = Object()

    println("objectA = ${objectA}")
    println("objectB = ${objectB}")


    val ta = ThreadPrinter("A", objectB, objectA)
    val tb = ThreadPrinter("B", objectA, objectB)

    //下面的启动顺序，即使每个线程的start之间有sleep ，也不能
    //sleep只能保证线程的启动顺序，启动了不一定立马执行，是由cpu决定的。

//    Thread.sleep(100)

    ta.name = "A"
    ta.start()
//    Thread.sleep(100)

    tb.name = "B"
    tb.start()

    //这里不能进行唤醒，否则会出现 ，A B 死锁的问题。
    //例如： A 执行完一次，调用prev.wait() ，然后又被下面的语句唤醒，执行synchronized(prev = b)
    //此时 其它cpu上的B 进程 执行synchronized(prev = a)，此时就都会阻塞在synchronized(self)
//    synchronized(objectB) {
//        //唤醒其它等待在该对象上的线程
//        objectB.notify()
//    }
}

//方案1 可行
class ABThreadPrinter1 constructor(val self: Object, val next: Object) : Thread() {

    override fun run() {
        super.run()
        while (i <= 100) {
            synchronized(self) {
                self.wait()
                println("---$name----${i++}") //打印
                synchronized(next) {

                    next.notify()
                }
            }
        }
    }

}


fun perform1() {
    val objectA = Object()
    val objectB = Object()

    println("objectA = ${objectA}")
    println("objectB = ${objectB}")


    val ta = ABThreadPrinter1(objectB, objectA)
    val tb = ABThreadPrinter1(objectA, objectB)

    //下面的启动顺序，即使每个线程的start之间有sleep ，也不能
    //sleep只能保证线程的启动顺序，启动了不一定立马执行，是由cpu决定的。

//    Thread.sleep(100)

    ta.name = "A"
    ta.start()
    Thread.sleep(100)

    tb.name = "B"
    tb.start()

    //这里不能进行唤醒，否则会出现 ，A B 死锁的问题。
    //例如： A 执行完一次，调用prev.wait() ，然后又被下面的语句唤醒，执行synchronized(prev = b)
    //此时 其它cpu上的B 进程 执行synchronized(prev = a)，此时就都会阻塞在synchronized(self)
    synchronized(objectB) {
        //唤醒其它等待在该对象上的线程
        objectB.notify()
    }
}

//方案二 可行
private var lock = ReentrantLock()
class ABThreadPrinter2 constructor(val lock :ReentrantLock,var seko:Int ):Thread(){

    override fun run() {
        super.run()
        while (i < 100){

            try {
                lock.lock()
                if (i % 2 == seko){
                    println("---$name----${i++}") //打印
                }
            } finally {
                lock.unlock()
            }
        }
    }
}


fun perform2() {

    val ta = ABThreadPrinter2(lock, 0)
    val tb = ABThreadPrinter2(lock, 1)


    ta.name = "A"
    ta.start()
    Thread.sleep(100)

    tb.name = "B"
    tb.start()

}



fun main() {


//    perform()
    perform2()
//    perform1()
//    perform6()
//    perform7()
}