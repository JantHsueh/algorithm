/**
 * create by xuexuan
 * time 2020/4/2 9:12
 *
 * 746. 使用最小花费爬楼梯
 * 数组的每个索引做为一个阶梯，第 i个阶梯对应着一个非负数的体力花费值 cost[i](索引从0开始)。
 * 每当你爬上一个阶梯你都要花费对应的体力花费值，然后你可以选择继续爬一个阶梯或者爬两个阶梯。
 * 您需要找到达到楼层顶部的最低花费。在开始时，你可以选择从索引为 0 或 1 的元素作为初始阶梯。
 */

class MinCostClimbingStairs {


    /**
     * 动态规划
     */
    fun minCostClimbingStairs(cost1: IntArray): Int {


        if (cost1.size == 1) return 0

        if (cost1.size == 2){
            return Math.min(cost1[0]?:0,cost1[1]?:0)
        }

        val cost = cost1.plus(0)
        val dp = arrayOfNulls<Int>(cost.size)

        dp[0] = cost[0]
        dp[1] = cost[1]

        for (i in 2 until  cost.size){

            dp[i] = Math.min(dp[i-1]?:0,dp[i-2]?:0) + cost[i]
        }

        return dp[cost.size -1]?:0

    }



    /**
     * 动态规划  精简版
     */
    fun minCostClimbingStairs1(cost: IntArray): Int {


        var previousCost = 0
        var currentCost = 0

        for (i in 0 until  cost.size){
            val temp = currentCost
            currentCost = Math.min(previousCost,currentCost) + cost[i]
            previousCost = temp
        }

        return Math.min(currentCost,previousCost)

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = MinCostClimbingStairs()

            val intArray = arrayListOf(10, 15,20) //最低花费是从cost[1]开始，然后走两步即可到阶梯顶，一共花费15。
//            val intArray = arrayListOf(1, 100, 1, 1, 1, 100, 1, 1, 100, 1) //最低花费是从cost[0]开始，然后走两步即可到阶梯顶，一共花费6。
            print("$intArray 最低花费 ${cs.minCostClimbingStairs1(intArray.toIntArray())}")

        }
    }

}