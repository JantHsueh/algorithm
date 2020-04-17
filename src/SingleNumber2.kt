/**
 * create by xuexuan
 * time 2020/4/16 17:27
 *
 * 137. 只出现一次的数字 II
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现了三次。找出那个只出现了一次的元素。
 *
 *
 */

class SingleNumber2 {

    /**
     * 使用hashmap，来存储值
     */
    fun singleNumber(nums: IntArray): Int {

        val single: Int
        val map = hashMapOf<Int, Int>()
        nums.forEach {

            map[it] = map[it]?.plus(1)?:1

        }
        single = map.filter { it.value == 1 }.map { it.key }.firstOrNull()?:0
        return single
    }



    /**
     * 使用位运算
     *
     * 数字是由二进制组成的，所以一个出现三次的数字，例如：0000 1100 ，那么在第三四位上的1 ，必然也会至少出现三次
     *
     * 所以只要考虑，在某一位上，把1出现次数的次数对3取模的值，就是那个要找的数字二进制对应位上的值
     *
     *
     * 这里呢，使用与运算，取反运算，异或运算，来实现对3取模。可以减少运算的次数（也就是常系数）
     *
     *
     */
    fun singleNumber1(nums: IntArray): Int {

        //表示出现一次的数，once二进制每一位表示，所有这些数，对应二进制位 出现了一次
        var once = 0
        //表示出现两次的数，twice二进制每一位表示，所有这些数，对应二进制位 出现了两次
        var twice = 0
        nums.forEach {
            //可以理解为，对二进制的所有位同时进行运算
            //下面的分析，是指定某一位来分析，
            //如果没有出现两次，once == 1 或者 it == 1 （两者只能其中一个是1），once 对应二进制位置1
            //当前是两次(once 肯定是0)，it一个出现，once 对应二进制位置0 （对3取模）
            //所有只要twice对应二进制位 = 1，无论 是否出现，once都是0
            once = twice.inv() and (it xor once )

            //如果没有出现一次，it == 0 并且 twice == 1 ，twice 对应二进制位置1  （）
            //如果出现一次，it == 1 并且 twice == 0 ，twice 对应二进制位置1
            //twice = (once xor twice) and (it xor twice )
            //这里需要注意，下面使用的once，已经不是上个状态的，而是新状态的once，所以逻辑需要进行重写调整
            twice = once.inv() and (it xor twice )
        }
        return once
    }



    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cl = SingleNumber2()

            val intArray = arrayListOf(0,1,0,1,0,1,99)
            println("$intArray 只出现一次的数 ${cl.singleNumber(intArray.toIntArray())}")

        }
    }


}