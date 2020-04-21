/**
 * create by xuexuan
 * time 2020/4/20 17:08
 *
 * 这道是邓俊辉算法训练营的题目
 *
 * 在一条笔直的道路上（可以看做一个数轴），在不同的位置有
 *
 * 有一只奶牛在一条笔直的道路上（可以看做是一个数轴）。初始，它在道路上坐标为 K 的地方。
 * 这条道路上有 n 棵非常新鲜的青草（编号从 1 开始）。其中第 i 棵青草位于道路上坐标为 x[i] 的地方。奶牛每秒钟可以沿着道路的方向向前（坐标加）或向后（坐标减）移动一个坐标单位的距离。
 * 它只要移动到青草所在的地方，就可以一口吞掉青草，它的食速很快，吃草的时间可以不计。
 * 不过，青草太新鲜了，奶牛每移动一个单位，没有被吃掉的青草，都会损失一个口感。它要吃光所有的青草。
 * 请你帮它计算，该怎样来回跑动，才能在口感损失之和最小的情况下吃掉所有的青草。
 *
 */


class CowsEatGrass {

    /**
     *
     * 思路：
     * 刚开始我记录每一颗青草的口感，如果奶牛没有吃掉，就把这些青草的口感减一，在吃过一个草后，都有可能选择左边或右边的吃，因为每个青草都有两种选择，n棵青草就有2ⁿ，指数函数
     * 其实这里面是包含了大量的重复计算，比如：数轴 1,2,3 上各有一棵草，奶牛从1 的位置出发，向左 判断有没有青草，向右 到2 ，从2 位置出发， 向左 判断有没有青草，向右 到2
     * 在这个具体的例子中，向左的判断都是不必的。于是可以想到，一个区间，向左还是向右。而不是一个点向左向右
     *
     *
     * 动态规划解法
     *
     * 1、因为奶牛不能在路上闲逛，每一次移动都是冲着一个青草的位置移动
     * 2、奶牛不可能，跳过 某一颗青草，去吃后面的青草。所以奶牛吃掉的草，肯定是一个连续区间的，在这个区间内的草，都被吃掉了
     *
     * 3、没有被吃的草们新鲜度  = 没有被吃的草们 * 移动的距离
     *
     *
     * dp[l][r][ k]    l,r  表示这个区间的草地索引，具体区间的值是[x[ l],x[ r]] 吃掉青草的口感，k = 0表示吃这个区间的最左，0吃这个区间的最右
     * [a,b]区间的草被吃光了，下一刻吃这个区间左边还是右边的草，状态转换只有两种：
     *
     * 因为区间是连续的，所以吃 [l,r] 区间的左边草，只能从上一个连续区间 [l + 1,r] 移动过来，分为 [l + 1,r] 的最左边和最右边 来计算，去最小值。动态往下计算
     * dp[l][r][0] = min(dp[l+1][r][0] +  (n-i) * (x[l+1] - x[l]) ,  dp[l+1][r][1]+  (n-i) * (x[ r] - x[ l])  )
     * dp[l][r][1] = min(dp[l][r-1][1] +  (n-i) * (x[r-1] - x[r]) ,  dp[l][r-1][0]+  (n-i) * (x[ l] - x[ r])  )
     *
     *
     *
     */
    fun computeTaste(n: Int, k: Int, x: IntArray): Int {


        val dp = Array(n) { Array(n) { IntArray(2){Int.MAX_VALUE} } }

        x.sort()

        for (i in x.indices){
            //设置边界条件：只吃一棵草的情况下，
            val taste = Math.abs(x[i] - k) * n
            dp[i][i][0] = taste
            dp[i][i][1] = taste
        }
        //枚举区间，长度 1 .. n
        for (len in 1..n) {

            //从0 开始枚举，长度为len的所有区间
            for (l in 0 until n - len) {
                val r = l + len

                val remainder = n - (r - l)
                dp[l][r][0] = Math.min(dp[l + 1][r][0] + remainder * (x[l + 1] - x[l]), dp[l + 1][r][1] + remainder * (x[r] - x[l]))
                dp[l][r][1] = Math.min(dp[l][r - 1][1] + remainder * (x[r] - x[r-1]), dp[l][r - 1][0] + remainder * (x[r] - x[l]))
            }
        }

        return Math.min(dp[0][n-1][0],dp[0][n-1][1])
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = CowsEatGrass()

            //预期值是 29+1+3+11=44
            val n = 4
            val k = 10

            val list = arrayListOf(1, 9, 11, 19)
            println("$n 棵草 ，奶牛在 $k   流失的新鲜度总和  ${cs.computeTaste(n, k, list.toIntArray())}")

        }
    }
}