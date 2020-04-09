package sort

import java.util.*

/**
 * create by xuexuan
 * time 2020/4/9 14:31
 *
 * 选择排序，减而治之
 * 还是以打扑克为例，从牌堆里取完所有的牌后，然后每次都在其中找出最大的放在手里（）
 * 不论给出的序列是什么样的，时间复杂度都是O(n²)
 */
class SelectSort {



    /**
     * 排序后，左小右大，右边有序，左边无序
     * 挑大的排序
     */
    fun selectSort(array: IntArray): IntArray {


        for (i in  0 until array.size) {

            //下面的循环是在有序序列中
            for (j in 0 until i) {

                if (array[j] > array[j+1]){
                    val t = array[j]
                    array[j] = array[j+1]
                    array[j+1] = t
                }
            }
        }
        return array
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = SelectSort()

            val list = arrayListOf(1, 2, 3, 1, 5, 6, 7, 7)
            println("$list  选择排序 ${Arrays.toString(cs.selectSort(list.toIntArray()))}")

        }
    }
}