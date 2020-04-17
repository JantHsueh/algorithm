/**
 * create by xuexuan
 * time 2020/4/16 14:53
 *
 *
 * 136. 只出现一次的数字
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 */

class SingleNumber {


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
     * XOR  实现
     *
     * 使用异或操作，可以实现两值交换  a b
     *
     *  a = a xor b
     *  b = a xor b
     *  a = a xor b
     *
     *  因为异或操作，可以认为是保留了原数的信息，所以与某数异或两次
     *
     *  a = a xor 0
     *  0 = a xor a
     *
     *  xor 满足交换律 结合律 ，因为每个数字，对应的位的0,1 个数是固定的，所以最终结果是一样的
     *  a xor b xor a =
     *
     *
     */
    fun singleNumber1(nums: IntArray): Int {

        var single: Int = 0
        nums.forEach {
            single = single xor it

        }
        return single
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cl = SingleNumber()

            val intArray = arrayListOf(4,1,2,1,2)
            println("$intArray 只出现一次的数 ${cl.singleNumber1(intArray.toIntArray())}")

        }
    }


}