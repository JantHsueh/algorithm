import sort.QuickSort
import java.util.*

/**
 * create by xuexuan
 * time 2020/4/10 10:58
 *
 * 16. 最接近的三数之和
 *
 * 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。返回这三个数的和。假定每组输入只存在唯一答案。
 */

class ThreeSumClosest {
    /**
     *时间复杂度：O(n²)
    空间复杂度：O(1)
     */
    fun threeSumClosest(nums: IntArray, target: Int): Int {


        var result :Int = 0


        var differ = Int.MAX_VALUE
        //对边界值，进行判断
        if (nums.size < 3) return result

        //对数组进行排序
        var numSort = QuickSort().quickSort(nums, 0, nums.size - 1)

        println("排序后的数组 ${Arrays.toString(numSort)}")
        //循环数组元素，作为候选元素，往右找其余两个元素，在内循环中使用双指针进行查找
        for (k in 0 until numSort.size) {

            //选取相同数字的第一个。例如：[1a,1b,2,3,4,],只判断 1a，1b会被过滤掉
            if (k > 0 && numSort[k] == numSort[k - 1]) continue

            //双指针进行查找剩余两个元素
            var l = k + 1
            var r = numSort.size - 1
            while (l < r) {

                var tempResult = numSort[k] + numSort[l] + numSort[r]
                println("临时结果  $k $l $r $tempResult")

                when {
                    tempResult == target -> {

                        //这里也需要过滤掉相同的l 和r
                        while (l < r && numSort[l] == numSort[l+1]){
                            l ++
                        }

                        while (l < r && numSort[r] == numSort[r-1]){
                            r --
                        }

                        l++
                        r--
                        return tempResult

                    }
                    //r 太大了
                    target < tempResult -> {
                        val abs = Math.abs(tempResult - target)
                        if (abs < differ) {
                            differ = abs
                            result = tempResult
                        }
                       r--
                    }
                    // l 小了，增大l，
                    tempResult < target -> {
                        val abs = Math.abs(tempResult - target)
                        if (abs < differ) {
                            differ = abs
                            result = tempResult
                        }
                         l++
                     }
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
            val ts = ThreeSumClosest()


//            给定数组 nums = [-1, 0, 1, 2, -1, -4]，
//
//            满足要求的三元组集合为：
//            [
//                [-1, 0, 1],
//                [-1, -1, 2]
//            ]

//            val list = arrayListOf(-1,2,1,-4)
//            val list = arrayListOf(1,1,1,1)
//            val list = arrayListOf(0,2,1,-3)
//            val list = arrayListOf(1,1,-1,-1,3)  //-1 期待-1
//            val list = arrayListOf(1, 2, 3, 4, 5, 6, 8, 9)
            val list = arrayListOf(4,0,5,-5,3,3,0,-4,-5) // -2 期待 -2
            println("$list  三数之和 ${ts.threeSumClosest(list.toIntArray(),-2)}")

        }
    }
}