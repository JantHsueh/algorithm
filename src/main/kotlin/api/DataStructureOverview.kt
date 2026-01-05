package src.main.kotlin

import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Kotlin/Java 常用数据结构类汇总
 */
fun main() {
    // ==========================================
    // 1. 列表 (List) - 有序集合
    // ==========================================
    println("--- Lists ---")
    
    // ArrayList (最常用)
    // 特点: 基于动态数组，随机访问快 O(1)，增删慢 O(N)
    val arrayList = ArrayList<String>()
    
    // LinkedList
    // 特点: 基于双向链表，增删快 O(1) (如果已知节点)，随机访问慢 O(N)
    // 也是 Queue 和 Deque 的实现
    val linkedList = LinkedList<String>()
    
    // CopyOnWriteArrayList (线程安全)
    // 特点: 读操作无锁，写操作复制整个数组。适合读多写少的并发场景。
    val cowList = CopyOnWriteArrayList<String>()

    // ==========================================
    // 2. 集合 (Set) - 元素唯一
    // ==========================================
    println("\n--- Sets ---")
    
    // HashSet (最常用)
    // 特点: 基于哈希表，无序，存取 O(1)
    val hashSet = HashSet<Int>()
    
    // LinkedHashSet
    // 特点: 维护插入顺序，性能略低于 HashSet
    val linkedHashSet = LinkedHashSet<Int>()
    
    // TreeSet
    // 特点: 基于红黑树，元素自动排序 (自然顺序或自定义 Comparator)，存取 O(log N)
    val treeSet = TreeSet<Int>() // 结果会自动排序: [1, 2, 5, 10]

    // ==========================================
    // 3. 映射 (Map) - 键值对
    // ==========================================
    println("\n--- Maps ---")
    
    // HashMap (最常用)
    // 特点: 基于哈希表，Key 无序，存取 O(1)
    val hashMap = HashMap<String, Int>()
    
    // LinkedHashMap
    // 特点: 维护 Key 的插入顺序 (或访问顺序，可用于实现 LRU Cache)
    val linkedHashMap = LinkedHashMap<String, Int>()
    
    // TreeMap
    // 特点: 基于红黑树，Key 自动排序，存取 O(log N)
    val treeMap = TreeMap<String, Int>()
    
    // ConcurrentHashMap (线程安全)
    // 特点: 分段锁/CAS 机制，高并发性能极佳，推荐替代 Hashtable
    val concurrentMap = ConcurrentHashMap<String, Int>()

    // ==========================================
    // 4. 队列 (Queue) & 双端队列 (Deque)
    // ==========================================
    println("\n--- Queues & Deques ---")
    
    // ArrayDeque (推荐)
    // 特点: 基于循环数组，双端操作 O(1)。
    // 用作 Stack (栈) 或 Queue (队列) 时，性能通常优于 LinkedList 和 Stack 类
    val arrayDeque = ArrayDeque<Int>()
    arrayDeque.addFirst(1) // 栈操作: push
    arrayDeque.addLast(2)  // 队列操作: offer
    
    // PriorityQueue (优先队列)
    // 特点: 基于二叉堆 (Binary Heap)，每次 poll() 取出的是最小(或最大)元素 O(log N)
    val priorityQueue = PriorityQueue<Int>() 
    
    // BlockingQueue (接口，常用于线程池/生产者消费者)
    // 实现类: ArrayBlockingQueue, LinkedBlockingQueue, PriorityBlockingQueue
    // val blockingQueue = java.util.concurrent.LinkedBlockingQueue<Int>()

    // ==========================================
    // 5. 其他特殊结构
    // ==========================================
    println("\n--- Others ---")
    
    // BitSet (位图)
    // 特点: 高效存储布尔值，按位操作。适合海量数据去重/统计。
    val bitSet = BitSet(1024)
    bitSet.set(10)
    
    // Stack (遗留类 - 不推荐)
    // Java 的 Stack 类继承自 Vector，全是同步锁，性能差。
    // 推荐使用 ArrayDeque 代替 Stack
    val legacyStack = Stack<Int>() 
    
    // IdentityHashMap
    // 特点: 使用 == (引用相等) 而不是 equals() 来比较 Key
    val identityMap = IdentityHashMap<String, Int>()
    
    // WeakHashMap
    // 特点: Key 是弱引用，如果 Key 没有其他引用，会被 GC 回收。常用于缓存。
    val weakMap = WeakHashMap<Any, String>()
}
