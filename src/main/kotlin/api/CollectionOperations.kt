package src.main.kotlin

fun main() {
    // ==========================================
    // 1. 准备数据
    // ==========================================
    val numbersList = listOf(10, 5, 20, 3, 8)
    val numbersArray = intArrayOf(10, 5, 20, 3, 8)
    val emptyList = emptyList<Int>()

    println("Data: $numbersList")

    // ==========================================
    // 2. 获取最大值 (Max)
    // ==========================================
    println("\n--- Max Element ---")
    
    // maxOrNull: 如果集合为空返回 null (推荐使用，安全)
    println("maxOrNull: ${numbersList.maxOrNull()}") 
    
    // max(): 在 Kotlin 1.4+ 已弃用，建议用 maxOrNull
    // maxOf { ... }: 根据选择器找最大值
    val people = listOf(Person("Alice", 25), Person("Bob", 30))
    println("maxOf (age): ${people.maxOf { it.age }}")
    
    // maxByOrNull { ... }: 返回拥有最大值的那个对象
    println("maxByOrNull (age): ${people.maxByOrNull { it.age }}")

    // ==========================================
    // 3. 求和 (Sum)
    // ==========================================
    println("\n--- Sum ---")
    
    // sum(): 对数值类型的集合直接求和
    println("sum (List): ${numbersList.sum()}")
    println("sum (Array): ${numbersArray.sum()}")
    
    // sumOf { ... }: 对非数值集合，提取数值后求和
    println("sumOf (ages): ${people.sumOf { it.age }}")

    // ==========================================
    // 4. 累计操作 (Reduce & Fold)
    // ==========================================
    println("\n--- Accumulation (Reduce & Fold) ---")
    
    // reduce: 从第一个元素开始累积。如果集合为空会抛异常。
    // 过程: 10+5=15 -> 15+20=35 -> 35+3=38 -> 38+8=46
    val sumReduce = numbersList.reduce { acc, i -> acc + i }
    println("reduce sum: $sumReduce")

    // fold: 需要一个初始值。如果集合为空，返回初始值。
    // 过程: 100(初始) + 10 = 110 -> 115 -> ...
    val sumFold = numbersList.fold(100) { acc, i -> acc + i }
    println("fold sum (start with 100): $sumFold")
    
    // runningFold (scan): 返回每一步的累计结果列表 (前缀和)
    // 结果: [0, 10, 15, 35, 38, 46]
    val prefixSum = numbersList.runningFold(0) { acc, i -> acc + i }
    println("runningFold (Prefix Sum): $prefixSum")
}

data class Person(val name: String, val age: Int)
