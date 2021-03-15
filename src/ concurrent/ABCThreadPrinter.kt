package concurrent.abc



/**
 * create by xuexuan
 * time 2021/3/11 11:41 上午
 */



//顺序启动 ，顺序打印，有问题
//强依赖于 各线程的启动顺序
//线程切换是随机的，可能会出现A 完全执行完，执行B。或者A执行到一半，c执行了一半，A打印完，打印了C。有可能出现 ABCACB

// 并行执行可能会出现死锁，例如：B 拿到objectA的锁，此时切换到A，A拿到objectC的锁，此时切换到C，C拿到objectB的锁，
// 这样线程B 拿不到objectB的锁，线程A 拿不到objectA的锁，线程C 拿不到objectC的锁
class ThreadPrinter constructor(name: String, private val prev: Object, private val self: Object) : Thread() {
    override fun run() {
        var count = 10
        while (count > 0) { // 多线程并发，不能用if，必须使用whil循环
            println("开始 thread = ${this.name} count = ${count}")

            synchronized(prev) {
                println(" thread = ${this.name} preAny = ${prev} 准备进入自己的同步区 ")

                // 先获取 prev 锁
                synchronized(self) {
                    println(" thread = ${this.name} selfAny = ${self} 开始打印 ")

                    // 再获取 self 锁
                    println("---$name----") //打印
                    count--
                    println(" thread = ${this.name} selfAny = ${self} 调用notify ")

                    self.notifyAll() // 唤醒其他线程竞争self锁，注意此时self锁并未立即释放。

                    //在这里调用该语句，会休眠当前线程，并释放prev对象锁，self锁依旧持有，其它线程无法进入对应的synchronized
                    //https://blog.csdn.net/qq_27680317/article/details/78567615
//                    prev.wait() // 立即释放 prev锁，当前线程休眠，等待唤醒

                }
                //此时执行完self的同步块，这时self锁才释放。
                try {
                    println(" thread = ${this.name} selfAny  = ${self}执行完成， preAny = ${prev} 调用 wait ")

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

fun perform() {
    val objectA = Object()
    val objectB = Object()
    val objectC = Object()

    println("objectA = ${objectA}")
    println("objectB = ${objectB}")
    println("objectC = ${objectC}")


    val tc = ThreadPrinter("C", objectC, objectA)
    val tb = ThreadPrinter("B", objectB, objectC)
    val ta = ThreadPrinter("A", objectA, objectB)

    //下面的启动顺序，即使每个线程的start之间有sleep ，也不能
    //sleep只能保证线程的启动顺序，启动了不一定立马执行，是由cpu决定的。

//    Thread.sleep(100)

    tc.name = "C"
    tc.start()
//    Thread.sleep(100)

    tb.name = "B"
    tb.start()

//    Thread.sleep(100)

    ta.name = "A"
    ta.start()
//    Thread.sleep(100)

    synchronized(objectA) {
        //唤醒其它等待在该对象上的线程
        objectA.notify()
    }
}



fun main() {
//    perform()
//    perform3()
    perform()
//    perform5()
//    perform6()
//    perform7()
}