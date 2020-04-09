package sort

import java.util.*

/**
 * create by xuexuan
 * time 2020/4/9 10:01
 *
 * 冒泡排序，算法复杂度是O(n²),贪心策略
 *
 * 遵循一个习惯，排序后的结果 左小右大,右边有序，左边无序，从右到左排序
 * 比较相邻的两个元素，如果左大右小，就交换。一次扫描后，最大的元素会拍到最右边
 * n次扫描后，就会完成排序
 */

class BubbleSort {


    fun bubbleSort(array: IntArray): IntArray {

        for (i in array.size - 1 downTo 0) {
            for (j in 0 until i) {
                if (array[j] > array[j + 1]) {
                    val t = array[j]
                    array[j] = array[j + 1]
                    array[j + 1] = t
                }
            }
        }

        return array
    }


    /**
     * 如果一次 j 循环，没有交换数据，说明已经剩下的都已经排序好了，立即结束
     */
    fun bubbleSort1(array: IntArray): IntArray {

        var switch = true
        for (i in array.size - 1 downTo 0) {
            if (!switch) break
            switch = false
            for (j in 0 until i) {
                if (array[j] > array[j + 1]) {
                    val t = array[j]
                    array[j] = array[j + 1]
                    array[j + 1] = t
                    switch = true
                }
            }
        }
        return array
    }


    /**
     *
     * 在i 循环中，如果右边的一段 已经是有序的，则直接跳过该段
     * 如果有100个数的数组，仅前面10个无序，后面90个都已排好序且都大于前面10个数字，那么在第一趟遍历后，最后发生交换的位置必定小于10，
     * 且这个位置之后的数据必定已经有序了，记录下这位置，第二次只要从数组头部遍历到这个位置就可以了。
     */
    fun bubbleSort2(array: IntArray): IntArray {

        var endIndex = array.size - 1
        var switch = true
        while (endIndex > 1) {
            if (!switch) break
            switch = false
            for (j in 0 until endIndex) {

                if (array[j] > array[j + 1]) {
                    val t = array[j]
                    array[j] = array[j + 1]
                    array[j + 1] = t
                    endIndex = j + 1
                    switch = true
                }
            }
        }
        return array
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = BubbleSort()

//            val list = arrayListOf(2, 7, 11, 15)
            val list = arrayListOf(1, 2, 3, 1, 5, 6, 7, 7)

            println("$list  冒泡排序 ${Arrays.toString(cs.bubbleSort2(list.toIntArray()))}")

        }
    }


}