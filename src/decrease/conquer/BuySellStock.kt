package decrease.conquer

/**
 * create by xuexuan
 * time 2020/3/26 9:27
 *
 * leetcode 股票买卖系列
 *
 */

class BuySellStock {

    /**
     * 121 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 如果你最多只允许完成一笔交易（即买入和卖出一支股票一次），设计一个算法来计算你所能获取的最大利润。
     * 注意：你不能在买入股票前卖出股票。
     */
    fun maxProfit121(prices: IntArray): Int {

        if (prices.isEmpty()) return 0
        var min = prices[0]

        var currProfit = 0
        var maxProfit = 0
        for (index in 0 until prices.size) {

            if (prices[index] < min) {
                min = prices[index]
            }
            currProfit = prices[index] - min
            maxProfit = Math.max(currProfit, maxProfit)

        }
        return maxProfit
    }

    /**
     * 121  动态规划 解法
     * 可以通过简化，把数组去掉
     */
    fun maxProfit121_2(prices: IntArray): Int {

        if (prices.isEmpty()) return 0

        //二位数组，dp[d][s] 一维d 表示day，天数，二维s 表示state，是否持有股票
        //每一个值，表示最大收益状态。例如dp[1][0] 表示第一天手上没有股票的最大收益
        val dp = Array(prices.size) { IntArray(2) }


        dp[0][0] = 0
        dp[0][1] = -prices[0]

        for (index in 1 until prices.size) {

            dp[index][0] = Math.max(dp[index - 1][0], dp[index - 1][1] + prices[index])
            // 注意：因为题目限制只能交易一次，因此状态只可能从 1 到 0，不可能从 0 到 1
            dp[index][1] = Math.max(-prices[index], dp[index - 1][1])
        }

        //最后一天，手上没有股票的最大收益
        return dp[prices.size - 1][0]
    }


    /**
     * 121  动态规划 简化版 解法
     * 可以通过简化，把数组去掉
     */
    fun maxProfit121_3(prices: IntArray): Int {

        if (prices.isEmpty()) return 0

        //二位数组，dp[d][s] 一维d 表示day，天数，二维s 表示state，是否持有股票
        //每一个值，表示最大收益状态。例如dp[1][0] 表示第一天手上没有股票的最大收益

        var dp_d_0 = 0
        var dp_d_1 = -prices[0]

        for (index in 1 until prices.size) {

            dp_d_0 = Math.max(dp_d_0, dp_d_1 + prices[index])
            // 注意：因为题目限制只能交易一次，因此状态只可能从 1 到 0，不可能从 0 到 1
            dp_d_1 = Math.max(-prices[index], dp_d_1)
        }

        //最后一天，手上没有股票的最大收益
        return dp_d_0
    }


    /**
     * 122 第一种解法，算出每一段（连续上升）的max和min，相减，最后加起来。
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）
     */
    fun maxProfit122_1(prices: IntArray): Int {

        if (prices.isEmpty()) return 0
        var max = prices[0]
        var min = prices[0]

        var maxProfit = 0
        for (index in 0 until prices.size) {

            if (max < prices[index]) {
                max = prices[index]
            }

            if (index == prices.size - 1) {
                maxProfit += (max - min)
            } else if (prices[index + 1] < max) {
                maxProfit += (max - min)
                min = prices[index + 1]
                max = prices[index + 1]
            }
        }
        return maxProfit
    }

    /**
     * 122 第二种解法，把每一段，只要前者比后者高，就相减计算利润。官方的第三种解法
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）
     */
    fun maxProfit122_2(prices: IntArray): Int {

        if (prices.isEmpty()) return 0

        var maxProfit = 0
        for (index in 0 until prices.size - 1) {
            if (prices[index] < prices[index + 1]) {
                maxProfit += (prices[index + 1] - prices[index])
            }
        }
        return maxProfit
    }


    /**
     * 122 第三种解法，动态规划
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）
     */
    fun maxProfit122_3(prices: IntArray): Int {

        if (prices.isEmpty()) return 0

        //二位数组，dp[d][s] 一维d 表示day，天数，二维s 表示state，是否持有股票
        //每一个值，表示最大收益状态。例如dp[1][0] 表示第一天手上没有股票的最大收益

        var dp_d_0 = 0
        var dp_d_1 = -prices[0]

        for (index in 1 until prices.size) {

            dp_d_0 = Math.max(dp_d_0, dp_d_1 + prices[index])
            // 注意：因为可以多次交易，所以这里，可以通过没有股票的状态0，转为持有股票的状态1
            dp_d_1 = Math.max(dp_d_0 - prices[index], dp_d_1)
        }

        //最后一天，手上没有股票的最大收益
        return dp_d_0
    }


    /**
     * 123  这种纯逻辑解法，目前还没在实现中
     * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
     * 注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     */
    fun maxProfit123(prices: IntArray): Int {

        if (prices.isEmpty()) return 0
        var max = prices[0]
        var min = prices[0]
        val arr = Array(3) { IntArray(2) }

        val first = 0
        val second = 0

        for (index in 0 until prices.size) {

            if (max < prices[index]) {
                max = prices[index]
            }

            if (index == prices.size - 1) {


            } else if (prices[index + 1] < max) {

                arr[2][0] = min
                arr[2][1] = max

//                if (arr[0].isNotEmpty() && arr[1].isNotEmpty()) {
//                    if (arr[0][1] < arr[1][1]) {
//                        //前两个可以合并
//                        arr[0][1] = arr[1][1]
//                        first = arr[0][1] - arr[0][0]
//
//                        arr[1][0] = arr[2][0]
//                        arr[1][1] = arr[2][1]
//                        second = arr[1][1] - arr[1][0]
//                    } else if (arr[1][1] < arr[2][1]) {
//                        //合并后两个
//                        arr[1][1] = arr[2][1]
//                        second = arr[1][1] - arr[1][0]
//
//                    } else {
//
//                        //记录前两大的利润，将
//
//                        //都不能合并
//
//                        if () {
//
//                        }
//                    }
//                }

                //在三段利润中，合并两个小的，得到利润最大的两个，如果下一次又出现了有

                min = prices[index + 1]
                max = prices[index + 1]
            }
        }
        return first + second
    }


    /**
     * 123  这种纯逻辑解法，目前还没在实现中
     * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
     * 注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     */
    fun maxProfit123_2(prices: IntArray): Int {

        if (prices.isEmpty()) return 0

        val maxK = 2

        val dp = Array(prices.size) { Array(2) { IntArray(2) } }
        //二位数组，dp[d][s] 一维d 表示day，天数，二维s 表示state，是否持有股票
        //每一个值，表示最大收益状态。例如dp[1][0] 表示第一天手上没有股票的最大收益

        dp[0][0][0] = 0
        dp[0][0][1] = 0
        dp[0][1][0] = 0
        dp[0][1][1] = -prices[0]


        for (d in 1 until prices.size) {

            for (k in 1 until maxK) {
                dp[d][k][0] = Math.max(dp[d-1][k][0], dp[d-1][k][1] + prices[d])
                // 注意：因为可以多次交易，所以这里，可以通过没有股票的状态0，转为持有股票的状态1
                dp[d][k][1] = Math.max(dp[d-1][k-1][0] - prices[d], dp[d-1][k][1])
            }
        }

        //最后一天，手上没有股票的最大收益
        return dp[prices.size-1][maxK-1][1]
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = BuySellStock()
            val intArray = arrayListOf(7, 1, 5, 3, 6, 4) //在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5
//            val intArray = arrayListOf(1, 2) //最大利润 = 1
            print("$intArray 最大利润 ${cs.maxProfit121_3(intArray.toIntArray())}")

            //[7,1,5,3,6,4] 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
            // 随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
//            val intArray1 = arrayListOf(7, 1, 5, 3, 6, 4)
//            val intArray1 = arrayListOf(1, 2, 3, 4, 5)
//            print("$intArray1 多次交易最大利润 ${cs.maxProfit122_2(intArray1.toIntArray())}")


//            val intArray2 = arrayListOf(3,3,5,0,0,3,1,4)
//            val intArray2 = arrayListOf(6,1,3,2,4,7)
//            val intArray2 = arrayListOf(1, 2, 4, 2, 5, 7, 2, 4, 9, 0)
//            print("$intArray2 两次交易最大利润 ${cs.maxProfit123(intArray2.toIntArray())}")

        }
    }
}