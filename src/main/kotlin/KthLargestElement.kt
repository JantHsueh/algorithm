import java.util.*

/**
 * create by xuexuan
 * time 2020/4/1 10:32
 *
 * 215. 数组中的第K个最大元素
 * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 */
class KthLargestElement {


    /**
     * 减而知之 时间复杂度 O(nlogn) 空间复杂度O(n)
     * 查找第K个最大元素
     *
     * 这个方法，随机选取一个数字，把数组分成两堆，
     */
    fun findKthLargest1(nums: IntArray, k: Int): Int {


        val numList = nums.map { it }.toMutableList()
        val smallList = mutableListOf<Int>()
        val bigList = mutableListOf<Int>()

        var random: Int = nums[0]
        //记录大于随机数的个数
        var bigNum = 0
        var found = false
        while (!found) {

            smallList.clear()
            bigList.clear()
            for (index in 0 until numList.size) {

                if (numList[index] < random) {
                    //严格小于随机数的
                    smallList.add(numList[index])
                } else {
                    bigList.add(numList[index])
                }
            }


            when {
                //找到第K大的元素
                bigList.size == k -bigNum  -> found = true

                //随机数，是最小的，直接减去，不影响最终结果
                bigList.size == numList.size ->{
                    numList.clear()
                    numList.addAll(bigList)
                    numList.removeAt(0)
                    random = numList[0]

                }

                //bigList的元素个数大于等于 k - bigNum ，说明随机数取得小了，第k大的元素在bigMap
                bigList.size > k - bigNum ->  {
                    numList.clear()
                    numList.addAll(bigList)
                    random = numList[0]
                }


                //bigList的元素个数小于k，说明随机数大了，第k大的元素在smallArray
                bigList.size < k - bigNum ->{
                    //只有这种情况，才需要增加bigNum
                    bigNum += bigList.size
                    numList.clear()
                    numList.addAll(smallList)
                    random = numList[0]
                }

            }
        }
        return random
    }




    /**
     * 优先队列 时间复杂度 O(nlogn) 空间复杂度O(n)
     * 查找第K个最大元素
     *
     * 使用java 的优先队列，内部通过最小堆来实现，每次取出的元素都是最小的
     */
    fun findKthLargest2(nums: IntArray, k: Int): Int {


        val queue = PriorityQueue<Int>(Comparator<Int> { o1, o2 -> o1 - o2})
        for (i in nums){
            queue.add(i)
            if (queue.size > k){
                queue.poll()
            }
        }

        return queue.poll()
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = KthLargestElement()

//            val intArray = arrayListOf(6,2,1,5,3,4) // 2 预期 5
//            val intArray = arrayListOf(3,2,3,1,2,4,5,5,6) //4 预期 4
//            val intArray = arrayListOf(2,1) //1 预期 2
            val intArray = arrayListOf(5,2,4,1,3,6,0) //4 预期 3
            print("$intArray 第k大元素是 ${cs.findKthLargest2(intArray.toIntArray(), 4)}")

        }
    }

}