package org.example.decrease.conquer

/**
 * create by xuexuan
 * time 2020/3/25 15:40
 *
 * 53. 最大子序和
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 *
 * 也就是邓俊辉 算法训练营，Greatest Slice
 */

class MaximumSubarray {


    /**
     * 动态规划  时间 O（n） 空间O（1）
     */
    fun maxSubArray(nums: IntArray): Int {



        var ans = nums[0]
        var currTotal = nums[0]
        for (index in 1 until nums.size) {

            currTotal = Math.max(currTotal, 0) + nums[index]
            ans = Math.max(ans,currTotal)
        }

        return ans

    }


    /**
     * 减而治之  时间 O（n） 空间O（1）
     */
    fun maxSubArray1(nums: IntArray): Int {

        var gs: Int = nums[0]

        var sum = 0
        for (index in nums.indices) {

            if ( sum < 0) {
                //更新
                sum = 0
            }
            sum += nums[index]

            if (sum > gs) {
                gs = sum
            }
        }

        return gs
    }


    /**
     * 分而治之  O（nlogn）
     */
    fun maxSubArray2(nums: IntArray): Int {

        return 0
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = MaximumSubarray()
            val intArray = arrayListOf(-2, 1, -3, 4, -1, 2, 1, -5, 4) //的和最大为 6。
//            val intArray = arrayListOf( 1) //的和最大为 1。
//            val intArray = arrayListOf(-2, -1, -3) //连续子数组 [4,-1,2,1] 的和最大为 -1。
            print("$intArray ,最大子序列和 ${cs.maxSubArray(intArray.toIntArray())}")
        }
    }
}