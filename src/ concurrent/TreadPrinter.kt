package concurrent

import java.util.concurrent.Semaphore
import java.util.concurrent.locks.*


/**
 * create by xuexuan
 * time 2021/1/10 3:52 下午
 *
 * 线程 A 打印A，线程B打印B ，线程C 打印C，依次启动 C B A,打印出A B C
 *
 */


//独占锁，多个进程 ，只能有一个进程持有锁，其他进程再想锁，就会被阻塞，所以会出现死锁

//顺序启动  顺序打印
//   共需要3把锁
// a 检查上一个锁1是否解锁，加锁 打印 解锁
// b 检查上一个锁a是否解锁，加锁 打印 解锁
// c 检查上一个锁b是否解锁，加锁 打印 解锁


// 逆序启动，顺序打印，此方法无法实现

// c 检查上一个锁1是否解锁，加锁 打印 解锁
// b 检查上一个锁a是否解锁，加锁 打印 解锁
// a 检查上一个锁b是否解锁，加锁 打印 解锁


// 如果是按照启动顺序打印，该方法是可行的，
// 但是如果按照启动逆序打印，该方法不可行。因为最后启动的线程执行完后，是否对应锁，后一个的期待线程，判断该锁是否释放，Y-》加锁 打印 解锁，N-》循环
// 问题在于，先启动c，c需要等待的是b，b的对应锁，不能事先上锁，因为是独占锁。加锁解锁只能在同一线程。（由此想到使用共享锁，见方法二）


private val lockA: Lock = ReentrantLock()
private val lockB: Lock = ReentrantLock()
private val lockC: Lock = ReentrantLock()

//方案一 不可行 ，独占锁，只能有一个进程持有锁，其他进程再想锁，就会被阻塞，所以会出现死锁
class ThreadPrinter constructor(name: String, lockPre: Lock, lockSelf: Lock) : Runnable {
    private val name: String
    private val lockPre: Lock
    private val lockSelf: Lock
    override fun run() {
//            System.out.print("开始执行 "+name +" ");

        while (true) {

            println("thread = ${this.name} lockPre = $lockPre  ,tryLock = ${lockPre.tryLock()}")

            if (lockPre.tryLock()) {
                try {
                    println("thread = ${this.name} 上锁lockSelf = $lockSelf")
                    lockSelf.lock()
                    println("------$name-----")
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    lockSelf.unlock()
                }
                return
            }
        }
    }

    init {
        this.name = name
        this.lockPre = lockPre
        this.lockSelf = lockSelf
    }
}

fun perform() {

//方案一
// 独占锁，多个进程 ，只能有一个进程持有锁，其他进程再想锁，就会被阻塞，
// 如果在主线程 锁住，那么想在其他线程再次尝试加锁，就会阻塞，可能会出现死锁
    lockA.lock()
    lockB.lock()
    lockC.lock();

    println("lockA = $lockA  ")
    println("lockB = $lockB  ")
    println("lockC = $lockC  ")
    val pc = ThreadPrinter("C", lockB, lockC)
    val pb = ThreadPrinter("B", lockA, lockB)
    val pa = ThreadPrinter("A", lockC, lockA)
    val tc = Thread(pc)
    tc.name = "C"
    tc.start()

    Thread.sleep(10)

    val tb = Thread(pb)
    tb.name = "B"
    tb.start()

    Thread.sleep(10)

    val ta = Thread(pa)
    ta.name = "A"
    ta.start()

//    如果是独占锁，因为加锁可能会出现死锁
    println("lockA unlock  ")
    lockA.unlock()

    println("lockB unlock  ")
    lockB.unlock()

    println("lockC unlock  ")
    lockC.unlock()
}


//使用读锁  共享锁，多个进程，可使用同一个锁，不会出现死锁，
//lockPre 解锁，子线程也可以进入（因为锁是共享的，所以即使在主线程 锁住），但是当前运行的是哪个线程是不确定的，所以打印顺序不可预期
//这里即使使用 公平锁&共享锁 也是不行的，公平锁是保证
//因为是共享锁，在某线程未加锁时，调用tryLock 始终是true，
private val readLockA = ReentrantReadWriteLock(true).readLock()
private val readLockB = ReentrantReadWriteLock(true).readLock()
private val readLockC = ReentrantReadWriteLock(true).readLock()

class ThreadPrinter2 constructor(name: String, lockPre: Lock, lockSelf: Lock) : Runnable {
    private val name: String
    private val lockPre: Lock
    private val lockSelf: Lock
    override fun run() {

        while (true) {

            println("thread = ${this.name} lockSelf = $lockSelf  ,tryLock = ${lockSelf.tryLock()}")

            if (lockSelf.tryLock()) {
                try {
                    println("thread = ${this.name} 上锁lockSelf = $lockSelf")
                    lockSelf.lock()
                    println("------$name-----")
                    break
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    lockSelf.unlock()
                }

            }
        }
    }

    init {
        this.name = name
        this.lockPre = lockPre
        this.lockSelf = lockSelf
    }
}


fun perform2() {


    readLockA.lock()
    readLockB.lock()
    readLockC.lock();

    println("readLockA = $readLockA  ")
    println("lockB = $readLockB  ")
    println("lockC = $readLockC  ")
    val pc = ThreadPrinter2("C", readLockB, readLockC)
    val pb = ThreadPrinter2("B", readLockA, readLockB)
    val pa = ThreadPrinter2("A", readLockC, readLockA)
    val tc = Thread(pc)
    tc.name = "C"
    tc.start()

    Thread.sleep(10)

    val tb = Thread(pb)
    tb.name = "B"
    tb.start()

    Thread.sleep(10)

    val ta = Thread(pa)
    ta.name = "A"
    ta.start()

//    如果是独占锁，因为加锁可能会出现死锁，所以不加锁，解锁也就不需要了
    println("lockA unlock  ")
    readLockA.unlock();

    println("lockB unlock  ")
    readLockB.unlock();

    println("lockC unlock  ")
    readLockC.unlock();
}


//方案三 可行 ，依赖cpu的线程调度，有一定的运气成分
private val lock: Lock = ReentrantLock()

//引入该变量的目的，相当于创建了3个不同的线程类。只要控制好，该变量的线程安全问题，就能正确大于
private var index: Int = 0


class ThreadPrinter3 constructor(name: String, lock: Lock, i: Int) : Runnable {
    private val name: String
    private val lock: Lock
    private var i: Int? = null
    override fun run() {

        //下面这个方法是可行的，但是会出现线程C 加锁 解锁（没有执行break），下一次还是在运行线程C，导致cpu空转，多次切换，才切换到想要（满足if条件）的线程
        while (true) {
            //非常奇怪的一个问题，加入下面这条代码，多线程 就不会切换线程了，
            //  println("thread = ${this.name} lock = $lock  ,tryLock = ${lock.tryLock()}")
            try {
                lock.lock()
                println("thread = ${this.name} 上锁成功 ")
                if (index == i) {
                    println("------$name-----")
                    index++
                    break
                }

            } finally {
                println("thread = ${this.name} 释放锁 ")
                lock.unlock()

            }
        }
    }

    init {
        this.name = name
        this.lock = lock
        this.i = i

    }
}


fun perform3() {

    val pc = ThreadPrinter3("C", lock, 2)
    val pb = ThreadPrinter3("B", lock, 1)
    val pa = ThreadPrinter3("A", lock, 0)
    val tc = Thread(pc)
    tc.name = "C"
    tc.start()

    val tb = Thread(pb)
    tb.name = "B"
    tb.start()

    val ta = Thread(pa)
    ta.name = "A"
    ta.start()

}


//顺序启动 ，顺序打印，有问题
//线程切换是随机的，可能会出现A 完全执行完，执行B。或者A执行到一半，c执行了一半，A打印完，打印了C。有可能出现 ABCACB
class ThreadPrinter41 constructor(name: String, private val prev: Object, private val self: Object) : Thread() {
    override fun run() {
        var count = 10
        while (count > 0) { // 多线程并发，不能用if，必须使用whil循环
            println("当前 thread = ${this.name} count = ${count}")

            synchronized(prev) {
                println("当前 thread = ${this.name} preAny = ${prev} 准备进入自己的同步区 ")

                // 先获取 prev 锁
                synchronized(self) {
                    println("当前 thread = ${this.name} selfAny = ${self} 开始打印 ")

                    // 再获取 self 锁
                    println("---$name----") //打印
                    count--
                    println("当前 thread = ${this.name} selfAny = ${self} 调用notify ")

                    self.notifyAll() // 唤醒其他线程竞争self锁，注意此时self锁并未立即释放。
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


//方案四 可行 ，其原理和方法五一样，因为先运行的线程，极大可能先执行，此时两个synchronized都是可进入的
//顺序启动，逆序打印的。因为这种方法的本质就是，按照 先后 顺序来加锁的，只要能获得锁，就马上获得，所以先启动的线程（后打印），需要等待，
//所以线程启动完成后，触发第一个想打印的线程
class ThreadPrinter4 constructor(name: String, selfAny: Object, nextAny: Object) : Thread() {

    private var nextAny: Object
    private var selfAny: Object

    init {
        this.name = name
        this.nextAny = nextAny
        this.selfAny = selfAny
    }

    override fun run() {

        println("当前 thread = ${this.name} ")
        synchronized(selfAny) {

            println("当前 thread = ${this.name} selfAny = ${selfAny}调用wait ")
            selfAny.wait()

            println("------$name-----")
            synchronized(nextAny) {

                println("当前 thread = ${this.name}  进入 nextAny = ${nextAny} ")
                nextAny.notify()

            }
//            println("当前 thread = ${this.name} preAny = ${nextAny} 准备进入自己的同步区 ")

//            preAny.notify()
        }

    }

    fun myNotify() {
        //在非synchronized(selfAny) 包裹的情况下，调用该函数，报异常java.lang.IllegalMonitorStateException
        selfAny.notify()
    }
}


fun perform4() {
    val objectA = Object()
    val objectB = Object()
    val objectC = Object()

    println("objectA = ${objectA}")
    println("objectB = ${objectB}")
    println("objectC = ${objectC}")

    // 可能会出现死锁，例如：B 拿到objectA的锁，此时切换到A，A拿到objectC的锁，此时切换到C，C拿到objectB的锁，
    // 这样线程B 拿不到objectB的锁，线程A 拿不到objectA的锁，线程C 拿不到objectC的锁
    val tc = ThreadPrinter4("C", objectC, objectA)
    val tb = ThreadPrinter4("B", objectB, objectC)
    val ta = ThreadPrinter4("A", objectA, objectB)

    //下面的启动顺序，即使每个线程的start之间有sleep ，也不能
    //sleep只能保证线程的启动顺序，启动了不一定立马执行，是由cpu决定的。

//    Thread.sleep(100)

    tc.name = "C"
    tc.start()
    Thread.sleep(100)

    tb.name = "B"
    tb.start()

    Thread.sleep(100)

    ta.name = "A"
    ta.start()
    Thread.sleep(100)


    synchronized(objectA) {
        //唤醒其它等待在该对象上的线程
        objectA.notify()
    }
}


//方案五 可行 ，通过锁的Condition 的 等待和触发线程
class ThreadPrinter5 constructor(name: String, self: Condition, next: Condition) : Thread() {

    private var next: Condition
    private var self: Condition

    init {
        this.name = name
        this.next = next
        this.self = self

    }

    override fun run() {

        try {
            //必须获取到锁，才能使用await，signal。否则会报异常java.lang.IllegalMonitorStateException
            lock.lock()
            println("当前 thread = ${this.name} 加锁成功 self = ${self} 执行等待")
            self.await()
            println("------$name-----")
            println("当前 thread = ${this.name} , 被唤醒, 触发信号 next = ${next}")
            next.signal()

        } catch (e: Exception) {
            println(e.toString())
        } finally {
            lock.unlock()

        }
    }

}

fun perform5() {
    val conditionA = lock.newCondition()
    val conditionB = lock.newCondition()
    val conditionC = lock.newCondition()
    val tc = ThreadPrinter5("C", conditionC, conditionA)
    val tb = ThreadPrinter5("B", conditionB, conditionC)
    val ta = ThreadPrinter5("A", conditionA, conditionB)

    tc.name = "C"
    tc.start()

    tb.name = "B"
    tb.start()

    ta.name = "A"
    ta.start()

    //这里需要等到，线程a的信号出于等到状态，下面的信号触发 才有效
    Thread.sleep(1000)

    lock.lock()
    conditionA.signal()
    lock.unlock()
}


//方案六 可行 ，使用信号量，原理和方案五 差不多
class ThreadPrinter6 constructor(name: String, self: Semaphore, next: Semaphore) : Thread() {

    private var next: Semaphore
    private var self: Semaphore

    init {
        this.name = name
        this.next = next
        this.self = self

    }

    override fun run() {

        try {
            println("当前 thread = ${this.name} 消耗一个信号 self = ${self} ")
            self.acquire()
            println("------$name-----")
            println("当前 thread = ${this.name} , 被唤醒, 增加一个信号 next = ${next}")
            next.release()
        } catch (e: Exception) {
            println(e.toString())
        } finally {


        }
    }
}

fun perform6() {
    val sa = Semaphore(1)
    val sb = Semaphore(0)
    val sc = Semaphore(0)

    val tc = ThreadPrinter6("C", sc, sa)
    val tb = ThreadPrinter6("B", sb, sc)
    val ta = ThreadPrinter6("A", sa, sb)

    tc.name = "C"
    tc.start()

    tb.name = "B"
    tb.start()

    ta.name = "A"
    ta.start()
}



//方案七 可行 ，使用，原理和方案五 差不多
class ThreadPrinter7 constructor(name: String, next: Thread?) : Thread() {

    private var next: Thread?

    init {
        this.name = name
        this.next = next

    }

    override fun run() {

        try {
            println("当前 thread = ${this.name} 调用park")
            LockSupport.park()
            println("------$name-----")
            println("当前 thread = ${this.name} , 唤醒下一个 线程 next = ${next?.name}")
            LockSupport.unpark(next)
        } catch (e: Exception) {
            println(e.toString())
        } finally {


        }
    }
}

fun perform7() {


    val tc = ThreadPrinter7("C", null)
    val tb = ThreadPrinter7("B", tc)
    val ta = ThreadPrinter7("A", tb)

    tc.start()
    tb.start()
    ta.start()

    LockSupport.unpark(ta)
}



fun main() {

//    perform3()
//    perform4()
//    perform5()
//    perform6()
    perform7()
}
