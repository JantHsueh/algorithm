import kotlin.math.min

/**
 * create by xuexuan
 * time 2020/4/2 15:42
 *
 * 887. 鸡蛋掉落
 * 你将获得 K 个鸡蛋，并可以使用一栋从 1 到 N  共有 N 层楼的建筑。
 * 每个蛋的功能都是一样的，如果一个蛋碎了，你就不能再把它掉下去。
 * 你知道存在楼层 F ，满足 0 <= F <= N 任何从高于 F 的楼层落下的鸡蛋都会碎，从 F 楼层或比它低的楼层落下的鸡蛋都不会破。
 * 每次移动，你可以取一个鸡蛋（如果你有完整的鸡蛋）并把它从任一楼层 X 扔下（满足 1 <= X <= N）。
 * 你的目标是确切地知道 F 的值是多少。
 * 无论 F 的初始值如何，你确定 F 的值的最小移动次数是多少？
 *
 * 这里的最小移动次数，意思就是最坏情况的最少次数，
 *
 * 如何理解最坏情况的最少次数？
 * 状态转换中，同级状态的最大值，能考虑到的最坏情况，在状态转移的时候，取这些同级最坏情况的最小值
 */
class SuperEggDrop {

    /**
     * 动态规划解法一：线性扫描版
     *
     *
     * 状态：K 个鸡蛋，第N层楼  dp[K][N] 表示K个鸡蛋，N层楼，最小尝试次数
     *
     * 转移：dp[K][N] = dp[K][N - i] + dp[K -1][i-1] + 1
     *
     *
     * 这个方法，是从根据网上的原理，但是我认为这个解法更像是递归
     */
    fun superEggDrop1_1(K: Int, N: Int): Int {


        val map = mutableMapOf<Map<Int, Int>, Int>()
        fun dp(K: Int, N: Int): Int {

            println("--开始计算 $K 个鸡蛋 $N 层楼 ")
            if (K == 1) {
                println("完成 $K 个鸡蛋 $N 层楼 需要的次数 $N")

                return N
            }
            if (N == 0) {
                println("完成 $K 个鸡蛋 $N 层楼 需要的次数 0")

                return 0
            }

            val mapKey = mutableMapOf<Int, Int>()
            mapKey[K] = N

            if (mapKey in map) {
                println("已经 $K 个鸡蛋 $N 层楼 ${map[mapKey]} ")

                return map[mapKey] ?: 0
            }

            var res = Int.MAX_VALUE
            for (i in 1..N) {
                //因为不知道鸡蛋在第几层楼会碎，所以就拿着K个鸡蛋，在这N层楼每一次都计算一下，所有的最坏情况取最小值，就是最坏情况的最少次数
                res = Math.min(Math.max(dp(K, N - i), dp(K - 1, i - 1)) + 1, res)
//                println("      在循环中$K 个鸡蛋 第$i 层楼 需要的次数 $res")

            }
            println("完成 $K 个鸡蛋 $N 层楼 需要的次数 $res")
            println()

            val mapKey1 = mutableMapOf<Int, Int>()
            mapKey1[K] = N

            map[mapKey1] = res
            return res
        }
        return dp(K, N)

    }


    /**
     * 动态规划解法一：二分查找版
     *
     *
     * 状态：K 个鸡蛋，N层楼  dp[K][N] 表示K个鸡蛋，N层楼，最小尝试次数
     *
     * 转移：dp[K][N] = dp[K][N-i] + dp[K -1][i-1] + 1
     *
     */
    fun superEggDrop1_2(K: Int, N: Int): Int {


        val map = mutableMapOf<Map<Int, Int>, Int>()
        fun dp(K: Int, N: Int): Int {

            println("--开始计算 $K 个鸡蛋 $N 层楼 ")
            if (K == 1) {
                println("完成 $K 个鸡蛋 $N 层楼 需要的次数 $N")

                return N
            }
            if (N == 0) {
                println("完成 $K 个鸡蛋 $N 层楼 需要的次数 0")

                return 0
            }

            val mapKey = mutableMapOf<Int, Int>()
            mapKey[K] = N

            if (mapKey in map) {
                println("已经 $K 个鸡蛋 $N 层楼 ${map[mapKey]} ")

                return map[mapKey] ?: 0
            }

            var res = Int.MAX_VALUE

            var lo = 1
            var hi = N

            //使用二分法进行查找，因为dp(K, n) 在n上是一个单调函数 ，所以dp(K - 1, mid) 单调增   dp(K - 1, N-mid)单调减
            // 交点处，就是他们相等的地方，再i继续增加，各自
            while (lo <= hi) {

                val mid = (lo + hi) / 2

                //递增函数
                val broken = dp(K - 1, mid - 1)
                //递减函数
                val notBroken = dp(K, N - mid)

                if (broken > notBroken) {
                    //前半段的数据大于后半段
                    res = min(res, broken + 1)
                    hi = mid - 1
                } else {
                    //前半段的数据不大于后半段
                    res = min(res, notBroken + 1)
                    lo = mid + 1
                }
            }

            println("完成 $K 个鸡蛋 $N 层楼 需要的次数 $res")
            println()

            val mapKey1 = mutableMapOf<Int, Int>()
            mapKey1[K] = N

            map[mapKey1] = res
            return res
        }
        return dp(K, N)
    }


    /**
     * 动态规划解法二：
     *
     *  K 个鸡蛋，N层楼，最坏情况最少需要几次找出临界值
     *  等同于 K 个鸡蛋，M次，最坏 情况找出最高楼层N
     *
     *
     * 状态：K 个鸡蛋，尝试次数M ，dp[K][ M] 表示K个鸡蛋，尝试M次，可以确定的最高楼层
     *
     * 转移：dp[K][ M] = dp[K -1][M-1] + dp[K][M-1] + 1
     *
     *
     * 在x层楼扔鸡蛋，如果鸡蛋碎了，那么0到x 的最大值（最高楼层）dp[K-1][M-1]
     * 在x层楼扔鸡蛋，如果鸡蛋没碎，那么x 以上的层数，最高楼层dp[K][M-1]
     *
     *
     */
    fun superEggDrop2(k: Int, n: Int): Int {

        println(" $k 个鸡蛋 $n 层楼 ")

        if (k == 1) return n
        if (n == 0) return 0

        //m 最多不会超过n层楼，线性扫描
        val dp = Array(k +1) { IntArray(n +1) }
        //尝试的次数
        var m = 0
        dp[0][0] = 0
        dp[1][0] = 0

        while (dp[k][m] < n) {
            m++
            for (i in 1..k) {
                dp[i][m] = dp[i - 1][m - 1] + dp[i][m-1] + 1
                println("dp[$i][$m] = ${dp[i][m]}")
            }
        }
        return m
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = SuperEggDrop()
            // 期望次数 是 3
            val k = 2
            val n = 6

//            val k = 100
//            val n = 8191
            // 期望次数 是 4
//            val k = 3
//            val n = 14


            println("$k $n 最坏情况最少次数 ${cs.superEggDrop1_1(k, n)}")

        }
    }
}