/**
 * create by xuexuan
 * time 2020/4/8 18:22
 *
 * 1. 两数之和
 *
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *  你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素
 *
 */
class TwoSum {


    /**
     *
     * 如果使用蛮力算法，时间复杂度是O(n²)，第一次遍历，第二次查找
     *
     * 利用哈希表，使得查找所需的O(n) 降低到O(1)，所以时间复杂度是O(n)
     */
    fun twoSum(nums: IntArray, target: Int): IntArray {

        val map = mutableMapOf<Int, Int>()
        nums.mapIndexed { index, i -> map[i] = index }

        nums.forEachIndexed { index, i ->
            var t = target - i

            if (map.containsKey(t)) {

                var ti = map[t]

                if (ti == index) {
                    return@forEachIndexed
                }
                return intArrayOf(index, map[t] ?: 0)
            }

        }

        return arrayListOf<Int>().toIntArray()
    }



    /**
     *
     * 上面的时间复杂度具体时间O(2n)，下面使用一次遍历，时间复杂度是O(n)
     *
     */
    fun twoSum1(nums: IntArray, target: Int): IntArray {

        val map = mutableMapOf<Int, Int>()

        nums.forEachIndexed { index, i ->
            var t = target - i

            if (map.containsKey(t)) {

                var ti = map[t]

                if (ti == index) {
                    return@forEachIndexed
                }
                return intArrayOf(index, map[t] ?: 0)
            }

             map[i] = index

        }

        return arrayListOf<Int>().toIntArray()
    }



    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = TwoSum()
            //  nums = [2, 7, 11, 15], target = 9
            val target = 6
//            val list = arrayListOf(2, 7, 11, 15)
            val list = arrayListOf(3, 2, 4)

            var t = cs.twoSum1(list.toIntArray(), target)
            println("$list $target 两数之和 $t")

        }
    }
}