/**
 * create by xuexuan
 * time 2025/4/18 15:31
 */



class Knapsack {

    fun knapsack(weights: IntArray, values: IntArray, capacity: Int): Int {
        val n = weights.size
        // n + 1, 是因为第一个物品，需要从一个初始态转换过来
        // capacity + 1, 是因为第一个物品，需要从一个初始态转换过来,
        val dp = Array(n + 1) { IntArray(capacity + 1 ) }

        // 因此在循环的时候，需要从索引1 开始，但是在获取物品的重量和价值的时候，还是从索引0 开始，所以使用 goodsIndex -1，表示的是当前的物品索引
        // dp 的数组维度，比weights 多一，循环的时候，需要从索引1 开始，也就是说， dp[1] 对应 weights[0]，dp[2] 对应 weights[1]
        for (goodsIndex in 1..n) {

            for (knapsackCap in 1..capacity) {
                // weights[goodsIndex -1] , 表示当前的物品的重量
                // dp[goodsIndex]  表示当前物品  计算后价值
                if (weights[goodsIndex -1] <= knapsackCap) {

                    dp[goodsIndex][knapsackCap] = maxOf(dp[goodsIndex - 1][knapsackCap], dp[goodsIndex - 1][knapsackCap - weights[goodsIndex -1]] + values[goodsIndex -1])

                } else {
                    dp[goodsIndex][knapsackCap] = dp[goodsIndex - 1][knapsackCap]
                }
            }
        }

        return dp[n][capacity]
    }

}


fun main(){


    val weights = intArrayOf(1, 2, 3)
    val values = intArrayOf(5, 11, 15)

    val capacity = 4
    val knapsack = Knapsack()
    val maxValue = knapsack.knapsack(weights, values, capacity)
    println("Maximum value in knapsack: $maxValue")

}