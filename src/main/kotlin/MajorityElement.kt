/**
 * create by xuexuan
 * time 2020/4/17 9:52
 *
 * 169. 多数元素
 *
 * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 *
 */
class MajorityElement {


    /**
     * 减而治之
     * 找次数大于 ⌊ n/2 ⌋ 的元素，使用减而治之，候选元素A, 遇到A 加一，不是A 减一，如果A 数量为0，替换候选元素
     */
    fun majorityElement(nums: IntArray): Int {


        if (nums.isEmpty()) return 0

        var candidate = nums[0]
        //候选元素的数量
        var num = 0

        for (i in nums){

            if (num == 0) candidate = i

            if (candidate == i){
                num ++
            }else{
                num --
            }

        }


        return candidate

    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = MajorityElement()

            val list = arrayListOf(2,2,1,1,1,2,2)
            println("$list 中超过1/2 的众数 ${cs.majorityElement(list.toIntArray())}")

        }
    }
}