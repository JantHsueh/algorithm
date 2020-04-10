import kotlin.math.max
import kotlin.math.min

/**
 * create by xuexuan
 * time 2020/4/10 13:56
 *
 * 4. 寻找两个有序数组的中位数
 *
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
 * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 * 你可以假设 nums1 和 nums2 不会同时为空。
 */

class MedianOfTwoSortedArrays {


    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {

        //先确定下来，数组的大小
        val shortArray: IntArray
        val longArray: IntArray
        if (nums1.size < nums2.size) {
            shortArray = nums1
            longArray = nums2
        } else {
            shortArray = nums2
            longArray = nums1
        }


        // 取值范围是【0，size】，s指向0，选中全部，s指向size，全不选中
        var sMin = 0
        var sMax = shortArray.size


        val half = (shortArray.size + longArray.size) / 2

        while (sMin <= sMax) {
            //short数组中间的索引，（n - 1）/2 认为数组被划分为：【0，s-1】 【s，size-1】
            val s = (sMin + sMax) / 2

            //long 数组中的索引，认为数组被划分为：【0，l-1】 【l，size-1】
            val l = half - s

            when {
                //大数组的后半最小值，小于小数组前半最大值，s 的值需要前移
                s < sMax && longArray[l] < shortArray[s - 1] -> {
                    sMax = s - 1
                }

                //小数组的后半最小值，小于大数组前半最大值，s 的值需要后移
                sMin < s && shortArray[s] < longArray[l - 1] -> {
                    sMin = s + 1
                }

                else -> {
                    //大数组的【0，l-1】 和小数组的【0，s-1】 的最大值 小于 大数组的【l，size -1】 和小数组的【s，size -1】 的最小值
                    val leftMax = when {
                        s == 0 -> longArray[l - 1]
                        l == 0 -> shortArray[s - 1]
                        else -> max(longArray[l - 1], shortArray[s - 1])
                    }

                    if ((longArray.size + shortArray.size) / 2 == 1) {
                        //这个选左边的最大值，取决于half 的计算，两个数组长度之和 +1 后 除以2
                        return leftMax.toDouble()
                    }


                    val rightMin = when {

                        s == shortArray.size -> longArray[l]
                        l == longArray.size -> shortArray[s]
                        else -> min(shortArray[s], longArray[l])
                    }


                    return (leftMax + rightMin) / 2.0


                }
            }
        }
        return 0.0

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cl = MedianOfTwoSortedArrays()

            val list1 = arrayListOf(1, 3)
            val list2 = arrayListOf(2)
//            val list = arrayListOf(1, 2, 3, 4, 5, 6, 8, 9)
            println("$list1 and $list2  中位数 ${cl.findMedianSortedArrays(list1.toIntArray(), list2.toIntArray())}")

        }
    }
}