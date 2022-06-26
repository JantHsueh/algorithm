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
            print("prices = ${prices[index]}     ")
            print("dp[$index][0] = ${dp[index][0]}        ")
            println("dp[$index][1] = ${dp[index][1]}")
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

    fun maxProfit123_3(prices: IntArray): Int {
        println("原始的数组：${prices}")

        if (prices.isEmpty()) return 0
        var max = prices[0]
        var min = prices[0]
        var profit = 0
        //防止 整体是下降趋势，只有在低点，涨了，才进行该区段的记录
        var ups = false
        var resultPrices: ArrayList<Int> = arrayListOf()
        //需要保存的prices的某一项到resultPrices 的位置，
        resultPrices.add(prices[0])
        var resultIndex = 0
        for (index in 1 until prices.size) {

            //价格出现拐点，就记录
            if (prices[index] <= max) {

                if (ups) {
                    resultIndex++
                    //记录上一个高点，如果第0位是高点，就不做记录，
                    resultPrices.add(resultIndex, prices[index - 1])

                    //最后一个出现拐点，就不用加入了，因为在上一点卖出更划算
                    if (index == prices.size - 1) {
                        break
                    }

                    resultIndex++
                    //记录拐点后的低点
                    resultPrices.add(resultIndex, prices[index])
                    ups = false
                } else {
                    //连续下降的话，就不断更新
                    resultPrices.set(resultIndex, prices[index])

                }
            } else if (max < prices[index]) {
                ups = true

                //最后一个点后，不在出现拐点
                if (index == prices.size - 1) {
                    resultPrices.add(prices[index])
                }
            }
            //重置最高点
            max = prices[index]
        }
        println("简化后的数组：${resultPrices}")


        //长度2 或 4
        var maxProfit = 0
        var secondMaxProfit = 0
        var finallyPrices = maxProfit123_31(resultPrices)
        if (finallyPrices.size > 1) {
            //计算出 利润最大的前两个
            for (index in 1 until finallyPrices.size step 2) {
                val currentProfit = finallyPrices[index] - finallyPrices[index - 1]
                println("index = ${index}   profit  ${currentProfit} ")

                //这里的 = ，是为了，让maxProfit 到secondMaxProfit，currentProfit到让maxProfit
                if (currentProfit >= maxProfit) {
                    secondMaxProfit = maxProfit
                    maxProfit = currentProfit
                    println("max index = ${index} ")
                } else if (currentProfit > secondMaxProfit) {
                    secondMaxProfit = currentProfit
                    println("second max index = ${index} ")

                }
            }
        }

        return maxProfit + secondMaxProfit

    }

    /**
     * 整理过的数组，格式[买A，卖B，买C，卖D，.., 买E，卖F]，连续上升的段被合并，也就是这里的 卖B>买C
     * 在这里检查是否需要合并其中的相邻的段
     *
     * A->B C->D 可以合并的场景：  (B-A)+(D-C)-(D-A)< 数组中最小的段, 合并后，利润是有损失的，损失的利润小于这些数组中买卖最小的段，就合理
     *
     * 该算法无法实现，本质问题没有解决，对未知的数据的影响，无法充分考虑
     *
     * 18, 9957, 1, 9996, 2, 9013, 559, 9766,  看上去可以合并 18, 9957, 1, 9996, 559, 9766,
     *
     * 2，9013被合并后，会出现把最优解 被忽略掉，原因是，2 是一个很小的买入，如果后续有卖价高于9997（1，9996中的9996+1），那么2.9013 应该买入，在9997的那时卖出
     * 但是仅仅在 1, 9996, 2, 9013, 559, 9766，中来判断是否合并，是不合理的
     *
     */
    fun maxProfit123_31(prices: ArrayList<Int>): ArrayList<Int> {


        //如果数组的长度小于4，那就直接返回两段的利润，因为买卖两次
        if (prices.size <= 4) {
            return prices
        }
        var firstProfit = Int.MIN_VALUE
        var secondProfit = Int.MIN_VALUE
        var minProfit = Int.MIN_VALUE
        var waitToRemovePricesIndex: ArrayList<Int> = arrayListOf()

        //计算相连的买卖 利润 倒数第3个
        for (index in 1 until prices.size step 2) {

            var currentProfit = prices[index] - prices[index - 1]
            if (currentProfit >= firstProfit) {
                minProfit = secondProfit
                secondProfit = firstProfit
                firstProfit = currentProfit
            } else if (currentProfit >= secondProfit) {
                minProfit = secondProfit
                secondProfit = currentProfit
            } else if (currentProfit > minProfit) {
                minProfit = currentProfit

            }
        }
        println("最三小利润：${minProfit}")

        var isMerge = false
        for (index in 1 until prices.size step 2) {

            var one = 0
            var two = 0
            val three = prices[index - 1]
            val four = prices[index]
            // 每次拿出三段来比较（1-2，3-4，5-6），
            // 第一段和第二段合并的利润损失 firstMergeProfitLoss，因为第二段的买价会低于第一段的卖价，所以如果合并，中间的利润就吃不到了
            //需要根据，哪段的利润损失更小，来判断加入第二段是加入第一段，还是第三段
            var firstMergeProfitLoss = Int.MAX_VALUE
            var secondMergeProfitLoss = Int.MAX_VALUE

            if (index > 2) {
                one = prices[index - 3]
                two = prices[index - 2]
                //第二段的买价会低于第一段的卖价，计算利润损失
                if (one <= three && two <= four) {
                    firstMergeProfitLoss = ((two - one) + (four - three) - (four - one))
                }
                //如果相邻的段，前者高低值，包含后者的高低值，就把后者段给去掉。去掉后，后面有更合适的合并情况，就会出现，
                //但是如果去掉也是有问题的，详见该函数注释
                if (one < three && two > four && (four - three < minProfit)) {
                    waitToRemovePricesIndex.add(index)
                    waitToRemovePricesIndex.add(index - 1)
                    isMerge = true
                    break
                }
            }


            var five = 0
            var six = 0
            if (index + 2 < prices.size) {
                five = prices[index + 1]
                six = prices[index + 2]
                if (four <= six && three <= five) {
                    secondMergeProfitLoss = ((four - three) + (six - five) - (six - three))
                }

                if (four > six && three < five && (six - five < minProfit)) {
                    waitToRemovePricesIndex.add(index + 1)
                    waitToRemovePricesIndex.add(index + 2)
                    isMerge = true
                    break
                }
                if (six > four && five < three && (four - three < minProfit)) {
                    waitToRemovePricesIndex.add(index)
                    waitToRemovePricesIndex.add(index - 1)
                    isMerge = true
                    break
                }
            }

            //判断是合并到前面，还是后面.1-2，3-4 ，5-6 有两种合并结果，1-4，5-6 或 1-2，3-6


            println("index = ${index} ${one},${two}, ${three},${four}, ${five},${six} firstMergeProfitLoss ：${firstMergeProfitLoss}  ${secondMergeProfitLoss}")


            if (secondMergeProfitLoss != Int.MIN_VALUE && firstMergeProfitLoss != Int.MIN_VALUE) {

                if (firstMergeProfitLoss <= secondMergeProfitLoss && secondMergeProfitLoss != Int.MIN_VALUE) {
                    // 这里的 = ，为了尽可能合并，因为1-2，3-4 合并为 1-4，可能就可以和5-6 合并了
                    if (two <= four && one <= three && firstMergeProfitLoss <= minProfit) {
                        waitToRemovePricesIndex.add(index - 2)
                        waitToRemovePricesIndex.add(index - 1)
                        isMerge = true
                        break
                    }

                } else {
                    if (four <= six && three <= five && secondMergeProfitLoss <= minProfit) {
                        waitToRemovePricesIndex.add(index)
                        waitToRemovePricesIndex.add(index + 1)
                        isMerge = true
                        break
                    }

                }
            }
        }

        if (!isMerge) {
//            var temp = maxProfit123_2(prices.toIntArray())
//            print(" 两次交易最大利润 ${temp}")
            return prices
        }

        for (i in waitToRemovePricesIndex) {

            prices.set(i, -1)
        }

        prices.removeAll(arrayListOf(-1))
        println("合并后的数组：${prices}")
        var temp = maxProfit123_2(prices.toIntArray())
        print(" 两次交易最大利润 ${temp}")

        return maxProfit123_31(prices)
    }


    /**
     * 123  动态规划 三维状态实现
     * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
     * 注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     */
    fun maxProfit123_2(prices: IntArray): Int {

        if (prices.isEmpty()) return 0

        val maxK = 2

        //三位数组，dp[d][s] 一维d 表示day，二位k 表示累计买入次数，三维s 表示state，是否持有股票
        //每一个值，表示最大收益状态。例如dp[1][1][0] 表示第二天，累计买入一次，手上没有股票的最大收益
        //最多允许交易两次，也就有三种状态，1、没有交易 2、 交易一次 3、 交易两次
        val dp = Array(prices.size) { Array(maxK + 1) { IntArray(2) } }

        dp[0][0][0] = 0

        //第1天，没有买卖，手上有股票。这是不可能发生,
        // 但是注意 这里第三位是1，表示当前有股票，也就是说在运算过程中，可能要卖出股票，增加股价价，所以设置最小值，加20个亿都是负数
        dp[0][0][1] = Int.MIN_VALUE
        //第1天，状态买入一次，手上没有有股票。这是不可能发生,
        // 但是注意 这里第三位是0，当前没有股票，也就是说在运算过程中，可能要买入股票，减去估价，所以不能设置最小值，减1后会变成最大数
        dp[0][1][0] = 0
        //第1天，状态买入一次，手上有股票，收益是股票价格的负数
        dp[0][1][1] = -prices[0]
        dp[0][2][1] = Int.MIN_VALUE
        dp[0][2][0] = 0


        for (d in 1 until prices.size) {

            print("d = $d     ")
            print("prices = ${prices[d]}     ")

            // k = 0 表示没有买入，没有买入，也就不能卖出，如果全部天数都没有买卖，那最大收益是0，所以不进行考虑
            for (k in 1..maxK) {

                dp[d][k][0] = Math.max(dp[d - 1][k][0], dp[d - 1][k][1] + prices[d])
                // 注意：因为可以多次交易，所以这里，可以通过没有股票的状态0，转为持有股票的状态1
                dp[d][k][1] = Math.max(dp[d - 1][k - 1][0] - prices[d], dp[d - 1][k][1])
                print("k = $k  ")

                print("dp[$d][$k][0] = ${dp[d][k][0]}     ")
                print("dp[$d][$k][1] = ${dp[d][k][1]}         ")

            }
            println()
        }

        //最后一天，手上没有股票的最大收益
        return Math.max(dp[prices.size - 1][maxK][0], dp[prices.size - 1][maxK - 1][0])
    }


    /**
     * 188. 买卖股票的最佳时机 IV
     * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
     * 注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     */
    fun maxProfit188(k: Int, prices: IntArray): Int {

        if (prices.isEmpty() || k == 0) return 0

        //一次买卖，至少需要两天，所以如果交易次数大于天数的一般，可退化为无限交易次数
        if (k > prices.size / 2)
            return maxProfit122_3(prices)


        //三位数组，dp[d][s] 一维d 表示day，二位k 表示累计买入次数，三维s 表示state，是否持有股票
        //每一个值，表示最大收益状态。例如dp[1][1][0] 表示第二天，累计买入一次，手上没有股票的最大收益
        //最多允许交易两次，也就有三种状态，1、没有交易 2、 交易一次 3、 交易两次
        val dp = Array(prices.size) { Array(k + 1) { IntArray(2) } }

        dp[0][0][0] = 0

        //第1天，没有买卖，手上有股票。这是不可能发生,
        // 但是注意 这里第三位是1，表示当前有股票，也就是说在运算过程中，可能要卖出股票，增加股价价，所以设置最小值，加20个亿都是负数
        dp[0][0][1] = Int.MIN_VALUE
        //第1天，状态买入一次，手上没有有股票。这是不可能发生,
        // 但是注意 这里第三位是0，当前没有股票，也就是说在运算过程中，可能要买入股票，减去估价，所以不能设置最小值，减1后会变成最大数
        dp[0][1][0] = 0
        //第1天，状态买入一次，手上有股票，收益是股票价格的负数
        dp[0][1][1] = -prices[0]

        for (index in 2..k) {

            dp[0][index][1] = Int.MIN_VALUE
            dp[0][index][0] = 0
        }


        for (d in 1 until prices.size) {

            print("d = $d     ")
            print("prices = ${prices[d]}     ")

            // k = 0 表示没有买入，没有买入，也就不能卖出，如果全部天数都没有买卖，那最大收益是0，所以不进行考虑
            for (k in 1..k) {

                dp[d][k][0] = Math.max(dp[d - 1][k][0], dp[d - 1][k][1] + prices[d])
                // 注意：因为可以多次交易，所以这里，可以通过没有股票的状态0，转为持有股票的状态1
                dp[d][k][1] = Math.max(dp[d - 1][k - 1][0] - prices[d], dp[d - 1][k][1])
                print("k = $k  ")

                print("dp[$d][$k][0] = ${dp[d][k][0]}     ")
                print("dp[$d][$k][1] = ${dp[d][k][1]}         ")

            }
            println()
        }

        //最后一天，手上没有股票的最大收益
        return Math.max(dp[prices.size - 1][k][0], dp[prices.size - 1][k - 1][0])
    }


    /**
     * 309. 最佳买卖股票时机含冷冻期
     * 给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。​
     * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
     * 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
     */
    fun maxProfit309(prices: IntArray): Int {

        if (prices.isEmpty()) return 0

        //二位数组，dp[d][s] 一维d 表示day，天数，二维s 表示state，是否持有股票
        //每一个值，表示最大收益状态。例如dp[1][0] 表示第一天手上没有股票的最大收益

        var dp_d_0 = 0
        var dp_d_1 = -prices[0]
        var dp_pre_0 = 0

        for (index in 1 until prices.size) {
            val temp = dp_d_0
            dp_d_0 = Math.max(dp_d_0, dp_d_1 + prices[index])
            // 注意：因为可以多次交易，所以这里，可以通过没有股票的状态0，转为持有股票的状态1
            dp_d_1 = Math.max(dp_pre_0 - prices[index], dp_d_1)
            dp_pre_0 = temp
        }

        //最后一天，手上没有股票的最大收益
        return dp_d_0
    }


    /**
     * 714. 买卖股票的最佳时机含手续费
     * 给定一个整数数组 prices，其中第 i 个元素代表了第 i 天的股票价格 ；非负整数 fee 代表了交易股票的手续费用。
     * 你可以无限次地完成交易，但是你每次交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
     * 返回获得利润的最大值。
     */
    fun maxProfit714(prices: IntArray, fee: Int): Int {

        if (prices.isEmpty()) return 0

        //二位数组，dp[d][s] 一维d 表示day，天数，二维s 表示state，是否持有股票
        //每一个值，表示最大收益状态。例如dp[1][0] 表示第一天手上没有股票的最大收益

        var dp_d_0 = 0
        var dp_d_1 = -prices[0] - fee

        for (index in 1 until prices.size) {
            dp_d_0 = Math.max(dp_d_0, dp_d_1 + prices[index])
            // 注意：因为可以多次交易，所以这里，可以通过没有股票的状态0，转为持有股票的状态1
            dp_d_1 = Math.max(dp_d_0 - prices[index] - fee, dp_d_1)
        }

        //最后一天，手上没有股票的最大收益
        return dp_d_0
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = BuySellStock()
//            val intArray = arrayListOf(7, 1, 5, 3, 6, 4) //在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5
//            val intArray = arrayListOf(1, 2) //最大利润 = 1
//            print("$intArray 最大利润 ${cs.maxProfit121_2(intArray.toIntArray())}")

            //[7,1,5,3,6,4] 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
            // 随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
//            val intArray1 = arrayListOf(7, 1, 5, 3, 6, 4)
//            val intArray1 = arrayListOf(1, 2, 3, 4, 5)
//            print("$intArray1 多次交易最大利润 ${cs.maxProfit122_2(intArray1.toIntArray())}")


//            val intArray2 = arrayListOf(3,3,5,0,0,3,1,4)
//            val intArray2 = arrayListOf(6,1,3,2,4,7)
//            在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。
//           随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。
//            val intArray2 = arrayListOf(3, 3, 5, 0, 0, 3, 1, 4)
//            val intArray2 = arrayListOf(1,2,3,4,5)
//            val intArray2 = arrayListOf(5, 4, 3, 2, 1, 0)
//            val intArray2 = arrayListOf( 1, 4,2)
            val intArray3 = arrayListOf(1, 2, 4, 2, 5, 7, 2, 4, 9, 0)
            val intArray2 = arrayListOf(
                397,
                6621,
                4997,
                7506,
                8918,
                1662,
                9187,
                3278,
                3890,
                514,
                18,
                9305,
                93,
                5508,
                3031,
                2692,
                6019,
                1134,
                1691,
                4949,
                5071,
                799,
                8953,
                7882,
                4273,
                302,
                6753,
                4657,
                8368,
                3942,
                1982,
                5117,
                563,
                3332,
                2623,
                9482,
                4994,
                8163,
                9112,
                5236,
                5029,
                5483,
                4542,
                1474,
                991,
                3925,
                4166,
                3362,
                5059,
                5857,
                4663,
                6482,
                3008,
                3616,
                4365,
                3634,
                270,
                1118,
                8291,
                4990,
                1413,
                273,
                107,
                1976,
                9957,
                9083,
                7810,
                4952,
                7246,
                3275,
                6540,
                2275,
                8758,
                7434,
                3750,
                6101,
                1359,
                4268,
                5815,
                2771,
                126,
                478,
                9253,
                9486,
                446,
                3618,
                3120,
                7068,
                1089,
                1411,
                2058,
                2502,
                8037,
                2165,
                830,
                7994,
                1248,
                4993,
                9298,
                4846,
                8268,
                2191,
                3474,
                3378,
                9625,
                7224,
                9479,
                985,
                1492,
                1646,
                3756,
                7970,
                8476,
                3009,
                7457,
                8922,
                2980,
                577,
                2342,
                4069,
                8341,
                4400,
                2923,
                2730,
                2917,
                105,
                724,
                518,
                5098,
                6375,
                5364,
                3366,
                8566,
                8838,
                3096,
                8191,
                2414,
                2575,
                5528,
                259,
                573,
                5636,
                4581,
                9049,
                4998,
                2038,
                4323,
                7978,
                8968,
                6665,
                8399,
                7309,
                7417,
                1322,
                6391,
                335,
                1427,
                7115,
                853,
                2878,
                9842,
                2569,
                2596,
                4760,
                7760,
                5693,
                9304,
                6526,
                8268,
                4832,
                6785,
                5194,
                6821,
                1367,
                4243,
                1819,
                9757,
                4919,
                6149,
                8725,
                7936,
                4548,
                2386,
                5354,
                2222,
                8777,
                2041,
                1,
                2245,
                9246,
                2879,
                8439,
                1815,
                5476,
                3200,
                5927,
                7521,
                2504,
                2454,
                5789,
                3688,
                9239,
                7335,
                6861,
                6958,
                7931,
                8680,
                3068,
                2850,
                1181,
                1793,
                7138,
                2081,
                532,
                2492,
                4303,
                5661,
                885,
                657,
                4258,
                131,
                9888,
                9050,
                1947,
                1716,
                2250,
                4226,
                9237,
                1106,
                6680,
                1379,
                1146,
                2272,
                8714,
                8008,
                9230,
                6645,
                3040,
                2298,
                5847,
                4222,
                444,
                2986,
                2655,
                7328,
                1830,
                6959,
                9341,
                2716,
                3968,
                9952,
                2847,
                3856,
                9002,
                1146,
                5573,
                1252,
                5373,
                1162,
                8710,
                2053,
                2541,
                9856,
                677,
                1256,
                4216,
                9908,
                4253,
                3609,
                8558,
                6453,
                4183,
                5354,
                9439,
                6838,
                2682,
                7621,
                149,
                8376,
                337,
                4117,
                8328,
                9537,
                4326,
                7330,
                683,
                9899,
                4934,
                2408,
                7413,
                9996,
                814,
                9955,
                9852,
                1491,
                7563,
                421,
                7751,
                1816,
                4030,
                2662,
                8269,
                8213,
                8016,
                4060,
                5051,
                7051,
                1682,
                5201,
                5427,
                8371,
                5670,
                3755,
                7908,
                9996,
                7437,
                4944,
                9895,
                2371,
                7352,
                3661,
                2367,
                4518,
                3616,
                8571,
                6010,
                1179,
                5344,
                113,
                9347,
                9374,
                2775,
                3969,
                3939,
                792,
                4381,
                8991,
                7843,
                2415,
                544,
                3270,
                787,
                6214,
                3377,
                8695,
                6211,
                814,
                9991,
                2458,
                9537,
                7344,
                6119,
                1904,
                8214,
                6087,
                6827,
                4224,
                7266,
                2172,
                690,
                2966,
                7898,
                3465,
                3287,
                1838,
                609,
                7668,
                829,
                8452,
                84,
                7725,
                8074,
                871,
                3939,
                7803,
                5918,
                6502,
                4969,
                5910,
                5313,
                4506,
                9606,
                1432,
                2762,
                7820,
                3872,
                9590,
                8397,
                1138,
                8114,
                9087,
                456,
                6012,
                8904,
                3743,
                7850,
                9514,
                7764,
                5031,
                4318,
                7848,
                9108,
                8745,
                5071,
                9400,
                2900,
                7341,
                5902,
                7870,
                3251,
                7567,
                2376,
                9209,
                9000,
                1491,
                7030,
                2872,
                7433,
                1779,
                362,
                5547,
                7218,
                7171,
                7911,
                2474,
                914,
                2114,
                8340,
                8678,
                3497,
                2659,
                2878,
                2606,
                7756,
                7949,
                2006,
                656,
                5291,
                4260,
                8526,
                4894,
                1828,
                7255,
                456,
                7180,
                8746,
                3838,
                6404,
                6179,
                5617,
                3118,
                8078,
                9187,
                289,
                5989,
                1661,
                1204,
                8103,
                2,
                6234,
                7953,
                9013,
                5465,
                559,
                6769,
                9766,
                2565,
                7425,
                1409,
                3177,
                2304,
                6304,
                5005,
                9559,
                6760,
                2185,
                4657,
                598,
                8589,
                836,
                2567,
                1708,
                5266,
                1754,
                8349,
                1255,
                9767,
                5905,
                5711,
                9769,
                8492,
                3664,
                5134,
                3957,
                575,
                1903,
                3723,
                3140,
                5681,
                5133,
                6317,
                4337,
                7789,
                7675,
                3896,
                4549,
                6212,
                8553,
                1499,
                1154,
                5741,
                418,
                9214,
                1007,
                2172,
                7563,
                8614,
                8291,
                3469,
                677,
                4413,
                1961,
                4341,
                9547,
                5918,
                4916,
                7803,
                9641,
                4408,
                3484,
                1126,
                7078,
                7821,
                8915,
                1105,
                8069,
                9816,
                7317,
                2974,
                1315,
                8471,
                8715,
                1733,
                7685,
                6074,
                257,
                5249,
                4688,
                8549,
                5070,
                5366,
                2962,
                7031,
                6059,
                8861,
                9301,
                7328,
                6664,
                5294,
                8088,
                6500,
                6421,
                1518,
                4321,
                5336,
                2623,
                8742,
                1505,
                9941,
                1716,
                2820,
                4764,
                6783,
                906,
                2450,
                2857,
                7515,
                4051,
                7546,
                2416,
                9121,
                9264,
                1730,
                6152,
                1675,
                592,
                1805,
                9003,
                7256,
                7099,
                3444,
                3757,
                9872,
                4962,
                4430,
                1561,
                7586,
                3173,
                3066,
                3879,
                1241,
                2238,
                8643,
                8025,
                3144,
                7445,
                882,
                7012,
                1496,
                4780,
                9428,
                617,
                396,
                1159,
                3121,
                2072,
                1751,
                4926,
                7427,
                5359,
                8378,
                871,
                5468,
                8250,
                5834,
                9899,
                9811,
                9772,
                9424,
                2877,
                3651,
                7017,
                5116,
                8646,
                5042,
                4612,
                6092,
                2277,
                1624,
                7588,
                3409,
                1053,
                8206,
                3806,
                8564,
                7679,
                2230,
                6667,
                8958,
                6009,
                2026,
                7336,
                6881,
                3847,
                5586,
                9067,
                98,
                1750,
                8839,
                9522,
                4627,
                8842,
                2891,
                6095,
                7488,
                7934,
                708,
                3580,
                6563,
                8684,
                7521,
                9972,
                6089,
                2079,
                130,
                4653,
                9758,
                2360,
                1320,
                8716,
                8370,
                9699,
                6052,
                1603,
                3546,
                7991,
                670,
                3644,
                6093,
                9509,
                9518,
                7072,
                4703,
                2409,
                3168,
                2191,
                6695,
                228,
                2124,
                3258,
                5264,
                9645,
                9583,
                1354,
                1724,
                9713,
                2359,
                1482,
                8426,
                3680,
                6551,
                3148,
                9731,
                8955,
                4751,
                9629,
                6946,
                5421,
                9625,
                9391,
                1282,
                5495,
                6464,
                5985,
                4256,
                5984,
                4528,
                952,
                6212,
                6652,
                562,
                1476,
                6297,
                145,
                9182,
                8021,
                6211,
                1542,
                5856,
                4637,
                1574,
                2407,
                7785,
                1305,
                1362,
                2536,
                934,
                4661,
                4309,
                559,
                4052,
                1943,
                2406,
                516,
                4280,
                6662,
                2852,
                8808,
                7614,
                9064,
                1813,
                4529,
                6893,
                8110,
                4674,
                2427,
                2484,
                7237,
                3969,
                8340,
                1874,
                5543,
                7099,
                6011,
                3200,
                8461,
                8547,
                486,
                9474,
                9208,
                7397,
                9879,
                7503,
                9803,
                6747,
                1783,
                6466,
                9600,
                6944,
                432,
                8664,
                8757,
                4961,
                1909,
                6867,
                5988,
                4337,
                5703,
                3225,
                4658,
                4043,
                1452,
                6554,
                1142,
                7463,
                9754,
                5956,
                2363,
                241,
                1782,
                7923,
                7638,
                1661,
                5427,
                3794,
                8409,
                7210,
                260,
                8009,
                4154,
                692,
                3025,
                9263,
                2006,
                4935,
                2483,
                7994,
                5624,
                8186,
                7571,
                282,
                8582,
                9023,
                6836,
                6076,
                6487,
                6591,
                2032,
                8850,
                3184,
                3815,
                3125,
                7174,
                5476,
                8552,
                968,
                3885,
                2115,
                7580,
                8246,
                2621,
                4625,
                1272,
                1885,
                6631,
                6207,
                4368,
                4625,
                8183,
                2554,
                8548,
                8465,
                1136,
                7572,
                1654,
                7213,
                411,
                4597,
                5597,
                5613,
                7781,
                5764,
                8738,
                1307,
                7593,
                7291,
                8628,
                7830,
                9406,
                6208,
                6077,
                2027,
                833,
                7349,
                3912,
                7464,
                9908,
                4632,
                8441,
                8091,
                7187,
                6990,
                2908,
                4675,
                914,
                4562,
                8240,
                1325,
                9159,
                190,
                6938,
                3292,
                5954,
                2028,
                4600,
                9899,
                9319,
                3228,
                7730,
                5077,
                9436,
                159,
                7105,
                6622,
                7508,
                7369,
                4086,
                3768,
                2002,
                8880,
                8211,
                5541,
                2222,
                1119,
                216,
                3136,
                5682,
                4809,
                813,
                1193,
                4999,
                4103,
                4486,
                7305,
                6131,
                9086,
                7205,
                5451,
                2314,
                1287,
                528,
                8102,
                1446,
                3985,
                4724,
                5306,
                1355,
                5163,
                9074,
                9709,
                4043,
                7285,
                5250,
                2617,
                4756,
                1818,
                2105,
                6790,
                6627,
                2918,
                7984,
                7978,
                7021,
                2470,
                1636,
                3152,
                7908,
                8841,
                4955,
                222,
                6480,
                5484,
                4676,
                7926,
                5821,
                9401,
                3232,
                7176,
                916,
                8658,
                3237,
                1311,
                5943,
                8487,
                3928,
                7051,
                306,
                6033,
                3842,
                3285,
                8951,
                1826,
                7616,
                2324,
                648,
                9252,
                5476,
                8556,
                4445,
                6784
            )
//            val intArray2 = arrayListOf(1, 1, 2, 2, 1, 1, 3, 3, 2, 4, 6, 3, 10, 5, 3)
//            val intArray2 = arrayListOf(5, 2, 3, 2, 6, 6, 2, 9, 1, 0, 7, 4, 5, 0)
//            val intArray2 = arrayListOf(8,3,6,2,8,8,8,4,2,0,7,2,9,4,9)
//            val intArray2 = arrayListOf(6, 5, 4, 8, 6, 8, 7, 8, 9, 4, 5)
//            print("$intArray2 两次交易最大利润 ${cs.maxProfit123_2(intArray2.toIntArray())}")
            print("$intArray2 两次交易最大利润 ${cs.maxProfit123_3(intArray2.toIntArray())}")

//            val intArray4 = arrayListOf(1, 2, 3, 0, 2)
//            print("$intArray4 卖出后，隔一天才能买入 ${cs.maxProfit309(intArray4.toIntArray())}")


//            val intArray5 = arrayListOf(1, 3, 2, 8, 4, 9)
//            print("$intArray5 添加手续费后的最大收益 ${cs.maxProfit714(intArray5.toIntArray(), 2)}")

        }
    }
}