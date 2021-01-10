package concurrent

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.locks.ReentrantReadWriteLock


/**
 * create by xuexuan
 * time 2021/1/10 3:52 下午
 *
 * 线程 A 打印A，线程B打印B ，线程C 打印C，依次启动 C B A,打印出A B C
 *
 */


//独占锁，多个进程 ，只能有一个进程持有锁，其他进程再想锁，就会被阻塞，所以会出现死锁


//第一种方式
//   共需要3把锁
// a 检查上一个锁1是否解锁，加锁 打印 解锁
// b 检查上一个锁a是否解锁，加锁 打印 解锁
// c 检查上一个锁b是否解锁，加锁 打印 解锁

// 如果是按照启动顺序打印，该方法是可行的，
// 但是如果按照启动逆序打印，该方法不可行。因为最后启动的线程执行完后，是否对应锁，后一个的期待线程，判断该锁是否释放，Y-》加锁 打印 解锁，N-》循环
//问题在于，先启动c，c需要等待的是b，b的对应锁，不能事先上锁，因为是独占锁。加锁解锁只能在同一线程

//   共需要3把锁
// c 检查上一个锁1是否解锁，加锁 打印 解锁
// b 检查上一个锁a是否解锁，加锁 打印 解锁
// a 检查上一个锁b是否解锁，加锁 打印 解锁


//private val lockA: Lock = ReentrantLock()
//private val lockB: Lock = ReentrantLock()
//private val lockC: Lock = ReentrantLock()

//使用读锁  共享锁，多个进程，可使用同一个锁，不会出现死锁，
//lockPre 解锁，子线程也可以进入（因为锁是共享的，所以即使在主线程 锁住），但是当前运行的是哪个线程是不确定的，所以打印顺序不可预期
//这里即使使用 公平锁&共享锁 也是不行的，公平锁是保证
private val lockA = ReentrantReadWriteLock(true).readLock()
private val lockB = ReentrantReadWriteLock(true).readLock()
private val lockC = ReentrantReadWriteLock(true).readLock()

//方案一 不可行 ，因为
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

//fun main() {
//
////方案一
//// 独占锁，多个进程 ，只能有一个进程持有锁，其他进程再想锁，就会被阻塞，
//// 如果在主线程 锁住，那么想在其他线程再次尝试加锁，就会阻塞，可能会出现死锁
//    lockA.lock()
//    lockB.lock()
//    lockC.lock();
//
//    println("lockA = $lockA  ")
//    println("lockB = $lockB  ")
//    println("lockC = $lockC  ")
//    val pc = ThreadPrinter("C", lockB, lockC)
//    val pb = ThreadPrinter("B", lockA, lockB)
//    val pa = ThreadPrinter("A", lockC, lockA)
//    val tc = Thread(pc)
//    tc.name = "C"
//    tc.start()
//
//    Thread.sleep(10)
//
//    val tb = Thread(pb)
//    tb.name = "B"
//    tb.start()
//
//    Thread.sleep(10)
//
//    val ta = Thread(pa)
//    ta.name = "A"
//    ta.start()
//
////    如果是独占锁，因为加锁可能会出现死锁，所以不加锁，解锁也就不需要了
//    println("lockA unlock  ")
//    lockA.unlock();
//
//    println("lockB unlock  ")
//    lockB.unlock();
//
//    println("lockC unlock  ")
//    lockC.unlock();
//}



class ThreadPrinter2 constructor(name: String, lockPre: Lock, lockSelf: Lock) : Runnable {
    private val name: String
    private val lockPre: Lock
    private val lockSelf: Lock
    override fun run() {
//            System.out.print("开始执行 "+name +" ");

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



fun main() {


    lockA.lock()
    lockB.lock()
    lockC.lock();

    println("lockA = $lockA  ")
    println("lockB = $lockB  ")
    println("lockC = $lockC  ")
    val pc = ThreadPrinter2("C", lockB, lockC)
    val pb = ThreadPrinter2("B", lockA, lockB)
    val pa = ThreadPrinter2("A", lockC, lockA)
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
    lockA.unlock();

    println("lockB unlock  ")
    lockB.unlock();

    println("lockC unlock  ")
    lockC.unlock();
}






private val lock: Lock = ReentrantLock()
private var state: Int = 0


//方案二 可行 ，依赖cpu的线程调度，有一定的运气成分
class ThreadPrinter3 constructor(name: String, lock: Lock, i: Int) : Runnable {
    private val name: String
    private val lock: Lock
    private var i: Int? = null
    override fun run() {

        //下面这个方法是可行的，但是会出现线程C 加锁 解锁（没有执行break），下一次还是在运行线程C，导致cpu空转，多次切换，才切换到想要（满足if条件）的线程
        while (true) {
            //非常奇怪的一个问题，加入下面这条代码，多线程 就不会切换线程了，
//            println("thread = ${this.name} lock = $lock  ,tryLock = ${lock.tryLock()}")
            try {
                lock.lock()
                println("thread = ${this.name} 上锁成功 ")
                if (state == i) {
                    println("------$name-----")
                    state++
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


private val conditionA = lock.newCondition()
private val conditionB = lock.newCondition()
private val conditionC = lock.newCondition()


//fun main() {



    //方案二

//    val pc = ThreadPrinter2("C", lock, 2)
//    val pb = ThreadPrinter2("B", lock, 1)
//    val pa = ThreadPrinter2("A", lock, 0)
//    val tc = Thread(pc)
//    tc.name = "C"
//    tc.start()
//
//
//    val tb = Thread(pb)
//    tb.name = "B"
//    tb.start()
//
//    val ta = Thread(pa)
//    ta.name = "A"
//    ta.start()
//
//}