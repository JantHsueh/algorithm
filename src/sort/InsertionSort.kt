package sort

import java.util.*

/**
 * create by xuexuan
 * time 2020/4/9 11:36
 *
 * 插入排序，贪心策略
 * 类似于打扑克的时候，手中的牌是有序的，在牌堆里里抓一张牌，按照大小放在手中的牌中
 * 这是一个输入敏感的算法，最好O(1)，最坏O(n²)
 */
class InsertionSort {

    /**
     * 排序后，左小右大，右边有序，左边无序
     * 挑大的排序
     */
    fun insertionSort(array: IntArray): IntArray {


        for (i in array.size - 2 downTo 0) {

            val t = array[i]
            //下面的循环是在有序序列中
            var slotIndex = i
            for (j in i+1 until array.size) {

                if (array[j] < t) {
                    //往左移动一位
                    array[j-1] = array[j]
                    slotIndex = j
                }else if (j == i+1){
                    //有序序列的最左边，就是最小值，t 比它还小，不需要继续往后查找了
                    break
                }
            }
            array[slotIndex] = t
        }

        return array

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = InsertionSort()

//            val list = arrayListOf(2, 7, 11, 15)
            val list = arrayListOf(1, 2, 3, 1, 5, 6, 7, 7)

            println("$list  插入排序 ${Arrays.toString(cs.insertionSort(list.toIntArray()))}")

        }
    }

}