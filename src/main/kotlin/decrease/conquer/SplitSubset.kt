package org.example.decrease.conquer

/**
 * create by xuexuan
 * time 2020/12/26 8:54 下午
 */
class SplitSubset {


    /**
     * 416. 分割等和子集
     * 给定一个只包含正整数的非空数组。是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
     */
    fun canPartition(nums: IntArray): Boolean {

        //子集长度小于2，不能分割
        if (nums.size < 2) return false

        //数组和为 奇数，不能成功分割
        if (nums.sum() % 2 != 0) {
            return false
        }

        val target = nums.sum() / 2

        //如果最大的数，大于和的一半，那也不能成功分割
        if (nums.max() ?: 0 == target) {
            return true
        } else if (nums.max() ?: 0 > target) {
            return false

        }

        //状态 i 表示数组索引[0,i]中选任意个数字的和 等于 j（目标和）
        val dp = Array(nums.size) {i-> Array(target+1) { j -> j == 0 } }

        dp[0][nums[0]] = true


        for (i in 1 until nums.size ) {

            val num = nums[i]
            for (j in 1 .. target) {

                if ( num <= j) {
                    // 目标和 大于当前的数值，可以考虑加入或者拒绝

                    //dp[i-1][j] 表示拒绝当前数字num，那么看[0,i-1]选任意个数字的和 是否等于 j
                    //dp[i-1][j-num] 表示加入当前数字num，那么看[0,i-1]选任意个数字的和 是否等于 j-num
                    //因为选择和不选择 都是可以的，所以这样这两个有一个为true，结果就是true
                    dp[i][j] = dp[i - 1][j] or dp[i - 1][j - num]
                } else {
                    // 目标和 小于当前的数值，那就不能加入，加入就超过了j
                    dp[i][j] = dp[i - 1][j]
                }
            }
        }
        return dp[nums.size - 1][target]
    }


}


fun main() {
    val cs = SplitSubset()
//    val intArray = arrayListOf(2, 2, 3, 5) //false
    val intArray = arrayListOf(3,3,3,4,5) //true

    print("$intArray ,分割等和子集 ${cs.canPartition(intArray.toIntArray())}")

}