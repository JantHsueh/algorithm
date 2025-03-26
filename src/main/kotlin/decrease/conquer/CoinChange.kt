package org.example.decrease.conquer

import kotlin.math.min

/**
 * create by xuexuan
 * time 2020/12/27 7:37 下午
 */
class CoinChange {


    /**
     * 零钱兑换
     *
     * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
     * 你可以认为每种硬币的数量是无限的。
     */
    fun coinChange(coins: IntArray, amount: Int): Int {


        //注意这里的默认值是amout +1，是因为最后会判断计算的硬币数，是否大于amount，如果大于说明不能凑出，就返回-1
        //赋最大值，为了min函数取值
        var dp = Array(amount + 1) { amount+1 }

        dp[0] = 0

        for (i in 0..amount) {
            for (element in coins) {
                if (element <= i) {
                    //使用当前不使用硬币dp[i] 和 使用当前硬币dp[i-element]+1，两种情况中选一个最小值
                    dp[i] = min(dp[i],dp[i-element]+1)
                }
            }

        }



        return if (dp[amount]>amount) -1 else  dp[amount]
    }

}

fun main() {
    val cc = CoinChange()
//    val intArray = arrayListOf(2, 2, 3, 5)
    val intArray = arrayListOf(2,5,10,1)

    print("$intArray ,零钱兑换 ${cc.coinChange(intArray.toIntArray(),27)}")

}