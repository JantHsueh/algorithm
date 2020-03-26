package decrease.conquer

/**
 * create by xuexuan
 * time 2020/3/25 14:18
 * 70 爬楼梯
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * 注意：给定 n 是一个正整数。
 */


class ClimbingStairs {



    /**
     * 动态规划 ，时间 O（n） 空间O（1）
     */
    fun climbStairs(n: Int): Int {

        var previous = 0
        var current = 1

        for (x in 1 .. n){
            val temp = previous + current
            previous = current
            current = temp
        }

        return current
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = ClimbingStairs()
            val num = 5
            print("$num 台阶 总共有 ${cs.climbStairs(num)}种方法")
        }
    }


}