import sort.QuickSort

/**
 * create by xuexuan
 * time 2020/4/10 10:06
 *
 * 15. 三数之和
 *
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 */

class ThreeSum {


    /**
     * 1、数组排序
     * 2、使用双指针进行排查
     *时间复杂度：O(n²)
     *空间复杂度：O(1)
     */
    fun threeSum(nums: IntArray): List<List<Int>> {


        var result = mutableListOf<List<Int>>()

        //对边界值，进行判断
        if (nums.size < 3) return result

        //对数组进行排序
        var numSort = QuickSort().quickSort(nums, 0, nums.size - 1)

        //循环数组元素，作为候选元素，往右找其余两个元素，在内循环中使用双指针进行查找
        for (k in 0 until numSort.size) {

            //因为已经是排序好的，从小到大，i 右边的元素都是大于0 的
            if (numSort[k] > 0) break

            //选取相同数字的第一个。例如：[1a,1b,2,3,4,],只判断 1a，1b会被过滤掉
            if (k>0 && numSort[k] == numSort[k - 1]) continue


            //双指针进行查找剩余两个元素
            var l = k + 1
            var r = numSort.size - 1
            while (l < r) {

                var tempResult = numSort[k] + numSort[l] + numSort[r]
                when {
                    tempResult == 0 -> {
                        result.add(listOf(numSort[k], numSort[l], numSort[r]))

                        //这里也需要过滤掉相同的l 和r
                        while (l < r && numSort[l] == numSort[l+1]){
                            l ++
                        }

                        while (l < r && numSort[r] == numSort[r-1]){
                            r --
                        }

                        l++
                        r--

                    }
                    //r 太大了
                    0 < tempResult -> r--
                    // l 小了，增大l，
                    tempResult < 0 -> l++
                }
            }
        }

        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val ts = ThreeSum()


//            给定数组 nums = [-1, 0, 1, 2, -1, -4]，
//
//            满足要求的三元组集合为：
//            [
//                [-1, 0, 1],
//                [-1, -1, 2]
//            ]


            val list = arrayListOf(-1, 0, 1, 2, -1, -4)
//            val list = arrayListOf(1, 2, 3, 4, 5, 6, 8, 9)
            println("$list  三数之和 ${ts.threeSum(list.toIntArray())}")

        }
    }
}