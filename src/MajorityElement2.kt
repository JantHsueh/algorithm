/**
 * create by xuexuan
 * time 2020/4/8 10:34
 *
 *  229. 求众数 II
 *
 * 给定一个大小为 n 的数组，找出其中所有出现超过 ⌊ n/3 ⌋ 次的元素。
 * 说明: 要求算法的时间复杂度为 O(n)，空间复杂度为 O(1)。
 */

class MajorityElement2 {


    /**
     * 时间复杂度O(n)  空间复杂度O(n)
     */
    fun majorityElement(nums: IntArray): List<Int> {


        val times = nums.size / 3

        val map = mutableMapOf<Int, Int>()


        for (i in nums) {
            if (map.containsKey(i)) {
                map[i] = map[i]?.plus(1)!!
            } else {
                map[i] = 1
            }
        }


        val l = mutableListOf<Int>()
        for (m in map) {

            if (m.value > times) {
                l.add(m.key)
            }
        }

        return l
    }


    /**
     *
     * 摩尔投票法
     *
     * 本题目是，找出超过 ⌊ n/3 ⌋ 次的元素，也就是说最多只能有2个元素，如果3个元素，总数就超过n了
     *
     * 时间复杂度O(n)  空间复杂度O(1)
     */
    fun majorityElement2(nums: IntArray): List<Int> {


        var n1 = 0
        var n2 = 0
        var n1Count = 0
        var n2Count = 0
        for (i in nums) {

            if (n1 == i) {
                n1Count++
                continue
            }

            if (n2 == i ) {
                n2Count++
                continue
            }


            if (n1Count == 0){
                n1 = i
                n1Count ++
                continue
            }

            if (n2Count == 0){
                n2 = i
                n2Count ++
                continue
            }


            n2Count--
            n1Count--

        }


        n1Count = 0
        n2Count = 0

        for (i in nums) {

            if (i == n1)
                n1Count ++
            else
            if (i == n2)
                n2Count ++

        }

        val list = mutableListOf<Int>()
        if (n1Count > (nums.size/3)) {
            list.add(n1)
        }

        if (n2Count > (nums.size/3)) {
            list.add(n2)
        }
        return list
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = MajorityElement2()

//            val list = arrayListOf(3, 3, 1, 1, 1, 2, 2, 2)
//            val list = arrayListOf(3, 3, 2)
            val list = arrayListOf(0, 0, 0)


            println("$list 中超过1/3 的众数 ${cs.majorityElement2(list.toIntArray())}")

        }
    }
}