import kotlin.math.max

/**
 * create by xuexuan
 * time 2025/4/18 15:31
 */


class Knapsack {

    fun knapsack(weights: IntArray, values: IntArray, capacity: Int): Int {
        val n = weights.size
        // n + 1, 是因为第一个物品，需要从一个初始态转换过来
        // capacity + 1, 是因为第一个物品，需要从一个初始态转换过来,
        val dp = Array(n +1) { IntArray(capacity + 1) }

        // 因此在循环的时候，需要从索引1 开始，但是在获取物品的重量和价值的时候，还是从索引0 开始，所以使用 goodsIndex -1，表示的是当前的物品索引
        // dp 的数组维度，比weights 多一，循环的时候，需要从索引1 开始，也就是说， dp[1] 对应 weights[0]，dp[2] 对应 weights[1]
        for (goodsIndex in 0 until n) {

            for (knapsackCap in 0..capacity) {
                // weights[goodsIndex -1] , 表示当前的物品的重量
                // dp[goodsIndex]  表示当前物品  计算后价值
                if (weights[goodsIndex] <= knapsackCap) {

                    dp[goodsIndex+1][knapsackCap] = maxOf(dp[goodsIndex][knapsackCap], dp[goodsIndex][knapsackCap - weights[goodsIndex]] + values[goodsIndex])

                } else {
                    dp[goodsIndex+1][knapsackCap] = dp[goodsIndex][knapsackCap]
                }
            }
        }

        return dp[n][capacity]
    }


    fun knapsack1(weights: IntArray, values: IntArray, capacity: Int): Int {

        var dp = IntArray(capacity + 1)
        var dpNext = IntArray(capacity + 1)

        for (i in weights.indices) {
            for (c in 0..capacity) {

                if (weights[i] <= c) {

                    dpNext[c] = max(dp[c], (dp[c - weights[i]] + values[i]))
                } else {
                    dpNext[c] = dp[c]
                }

            }
            dp = dpNext
            dpNext =  IntArray(capacity + 1)

        }

        return dp[capacity]
    }


}


fun main() {


    val weights = intArrayOf(1, 2, 3, 4, 5)
    val values = intArrayOf(5, 11, 15, 25, 40)

    val capacity = 5
    val knapsack = Knapsack()
    val maxValue = knapsack.knapsack(weights, values, capacity)
//    val maxValue = knapsack.knapsack1(weights, values, capacity)
    println("Maximum value in knapsack: $maxValue")

}