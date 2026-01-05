package src.main.kotlin

fun main() {
    // 演示 ArrayDeque 的循环数组原理
    val demo = ArrayDeque<Int>(4)
    
    println("--- ArrayDeque 循环数组演示 ---")
    demo.addLast(1)
    demo.addLast(2)
    demo.addLast(3)
    // 此时数组: [1, 2, 3, null], head=0, tail=3
    
    demo.removeFirst() 
    // 此时数组: [null, 2, 3, null], head=1, tail=3 (head 向后移动)
    
    demo.addLast(4)
    // 此时数组: [null, 2, 3, 4], head=1, tail=0 (tail 绕回到开头了!)
    
    demo.addLast(5)
    // 触发扩容
    demo.addLast(6)
}

/**
 * ========================================================================
 * 1. 快速排序 (QuickSort) 时间复杂度分析
 * ========================================================================
 * 
 * 核心公式: T(N) = T(i) + T(N-i-1) + O(N)
 * 其中 O(N) 是 Partition (分区) 函数的时间，它需要遍历整个数组。
 * 
 * ### A. 最好情况 (Best Case) - O(N log N)
 * 场景: 每次选的 Pivot (基准值) 都能把数组完美地分成两半。
 * 
 * 层级分析 (Recursion Tree):
 * 第 1 层: N               (1个节点，耗时 N)
 * 第 2 层: N/2, N/2        (2个节点，总耗时 N)
 * 第 3 层: N/4, N/4...     (4个节点，总耗时 N)
 * ...
 * 树的高度: log2(N)
 * 
 * 总时间 = 每层耗时(N) × 层数(log N) = O(N log N)
 * 
 * ### B. 最坏情况 (Worst Case) - O(N^2)
 * 场景: 数组已经是原本有序（或逆序），且每次总是选第一个元素作为 Pivot。
 * 这样每次分区只能分出 0 个元素和 N-1 个元素。
 * 
 * 层级分析:
 * 第 1 层: N               (耗时 N)
 * 第 2 层: N-1             (耗时 N-1)
 * 第 3 层: N-2             (耗时 N-2)
 * ...
 * 
 * 总时间 = N + (N-1) + (N-2) + ... + 1 
 *       = (N * (N+1)) / 2 
 *       = O(N^2)
 * 
 * ### C. 平均情况 - O(N log N)
 * 只要 Pivot 选得不是太偏（比如每次至少能分出 1/10 和 9/10），树的高度依然是 logN 级别的。
 */


/**
 * ========================================================================
 * 2. ArrayDeque 内部原理 (循环数组 / Ring Buffer)
 * ========================================================================
 * 
 * ArrayDeque 不是链表，而是一个 Object[] 数组。
 * 它的核心在于使用两个指针 head 和 tail，并在数组中“转圈”使用空间。
 */
class SimpleArrayDeque<E>(initialCapacity: Int) {
    // 必须是 2 的幂次方，方便位运算优化 (Java 源码的做法)
    private var elements: Array<Any?> = arrayOfNulls(initialCapacity)
    private var head: Int = 0
    private var tail: Int = 0

    // 模拟 addLast
    fun addLast(e: E) {
        elements[tail] = e
        
        // 关键点：计算下一个 tail 的位置
        // (tail + 1) & (elements.length - 1) 等价于 (tail + 1) % length
        // 这种写法只有在 length 是 2 的幂次方时才成立，效率极高
        tail = (tail + 1) and (elements.size - 1)
        
        println("Add $e: head=$head, tail=$tail, array=${elements.contentToString()}")

        // 如果 tail 追上了 head，说明满了，需要扩容
        if (head == tail) {
            doubleCapacity()
        }
    }

    // 模拟 removeFirst
    fun removeFirst(): E? {
        if (head == tail) return null
        
        val result = elements[head] as E
        elements[head] = null // 避免内存泄漏
        
        // 关键点：head 也是循环移动的
        head = (head + 1) and (elements.size - 1)
        
        println("Remove $result: head=$head, tail=$tail, array=${elements.contentToString()}")
        return result
    }

    private fun doubleCapacity() {
        println("!! 数组满了，扩容 !!")
        // 实际扩容逻辑：创建一个两倍大的新数组
        // 将 head 右边的元素拷贝到新数组开头
        // 将 head 左边的元素（因为循环了）拷贝到新数组后面
        // 重置 head = 0, tail = oldSize
    }
}
