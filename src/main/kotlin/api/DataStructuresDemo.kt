package src.main.kotlin

import java.util.LinkedList
import java.util.ArrayDeque

fun main() {
    // ==========================================
    // 1. 数组 (Array)
    // ==========================================
    println("--- Arrays ---")
    
    // 1.1 使用 arrayOf 初始化固定元素
    val arr1 = arrayOf(1, 2, 3)
    println("arrayOf: ${arr1.contentToString()}")

    // 1.2 创建指定大小的数组，默认值为 null (需要可空类型)
    val arr2 = arrayOfNulls<Int>(5)
    println("arrayOfNulls: ${arr2.contentToString()}")

    // 1.3 创建指定大小的数组，并使用 lambda 初始化每个元素
    // Array(size) { index -> value }
    val arr3 = Array(5) { i -> i * 2 } 
    println("Array constructor: ${arr3.contentToString()}")

    // 1.4 原生类型数组 (避免装箱拆箱，性能更好)
    val intArray = intArrayOf(1, 2, 3)
    val doubleArray = DoubleArray(3) { 0.0 } // 大小为3，全为0.0
    println("intArray: ${intArray.contentToString()}")

    // ==========================================
    // 2. 列表 (List)
    // ==========================================
    println("\n--- Lists ---")

    // 2.1 不可变列表 (Immutable List) - 最常用
    val list1 = listOf("A", "B", "C")
    println("listOf: $list1")

    // 2.2 可变列表 (Mutable List)
    val list2 = mutableListOf(1, 2, 3)
    list2.add(4)
    println("mutableListOf: $list2")

    // 2.3 ArrayList 构造函数
    val list3 = ArrayList<String>()
    list3.add("Hello")
    
    // 2.4 带初始化逻辑的 List (类似 Array)
    val list4 = List(5) { index -> "Item $index" }
    println("List constructor: $list4")
    
    // 2.5 空列表
    val empty = emptyList<String>()

    // ==========================================
    // 3. 链表 (LinkedList) & 队列/栈
    // ==========================================
    println("\n--- LinkedList / Queue / Stack ---")

    // 3.1 LinkedList (Java 标准库)
    // Kotlin 没有专门的 LinkedList 语法糖，直接用 Java 的
    val linkedList = LinkedList<String>()
    linkedList.add("First")
    linkedList.addFirst("Head") // 头部添加
    linkedList.addLast("Tail")  // 尾部添加
    println("LinkedList: $linkedList")

    // 3.2 双端队列 (ArrayDeque) - 推荐用于替代 Stack 和 LinkedList
    // 性能通常优于 LinkedList
    val deque = ArrayDeque<Int>()
    deque.addFirst(1)
    deque.addLast(2)
    println("ArrayDeque: $deque")

    // ==========================================
    // 4. 集合 (Set) & 映射 (Map)
    // ==========================================
    println("\n--- Set & Map ---")

    // 4.1 Set (去重)
    val set1 = setOf(1, 2, 2, 3) // 结果: [1, 2, 3]
    val mutableSet = mutableSetOf<Int>()
    println("setOf: $set1")

    // 4.2 Map (键值对)
    // 使用 'to' 中缀函数创建 Pair
    val map1 = mapOf("key1" to 1, "key2" to 2)
    println("mapOf: $map1")

    val mutableMap = mutableMapOf<String, Int>()
    mutableMap["key3"] = 3
    
    // ==========================================
    // 5. 转换操作
    // ==========================================
    println("\n--- Conversions ---")
    val sourceArr = arrayOf(1, 2, 3)
    val toList = sourceArr.toList()
    val toSet = sourceArr.toSet()
    val toIntArray = list2.toIntArray() // List<Int> -> IntArray
    
    println("Array -> List: $toList")
}
