
fun <T> getAllCombinations(list: List<T>): List<List<T>> {
    val result = mutableListOf<List<T>>()
    
    fun backtrack(start: Int, current: MutableList<T>) {
        // Add the current combination to the result
        // Note: We create a new list to avoid reference issues
        if (current.isNotEmpty()) {
            result.add(ArrayList(current))
        }
        
        for (i in start until list.size) {
            current.add(list[i])
            backtrack(i + 1, current)
            current.removeAt(current.size - 1)
        }
    }
    
    backtrack(0, mutableListOf())
    return result
}


fun <T>getAllCombinations1(list: List<T>): List<List<T>> {


    var result = mutableListOf<List<T>>()

    val bit = 1 shl (list.size)

    for (i in 0 until bit) {

        val subset = arrayListOf<T>()

        for (j in list.indices) {

            if ((i shr (j)) and (1) == 1) {
                subset.add(list.get(j))
            }

        }
        result.add(subset)

    }
    return result

}


fun main() {
    val nums = listOf(6, 7, 8, 9)
//    val combinations = getAllCombinations(nums)
    val combinations = getAllCombinations1(nums)

    println("Input: $nums")
    println("Total combinations: ${combinations.size}")
    combinations.forEach { println(it) }
}
