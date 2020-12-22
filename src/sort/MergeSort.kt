package sort

import java.util.*

/**
 * create by xuexuan
 * time 2020/4/17 10:29
 * 归并排序
 */
class MergeSort {


    /**
     * 归并排序，典型的分治策略
     *
     * 分：使用递归，把待排序数组，分解为每段只有一个元素
     * 合：二路归并，保证每次合并后，都是有序的
     */
    fun mergeSort(array: IntArray, l: Int, r: Int){

        if (l == r) return
        val mid = (l + r) shr 1
        //分
        mergeSort(array, l, mid)
        mergeSort(array, mid + 1, r)

        //[l,mid] 的数组是有序的，[mid+1,r]的数组是有序的，使用二路归并，合并这两个有序的数组
        var leftArrayStart = l
        var rightArrayStart = mid + 1
        mTempList.clear()
        //二路归并
        while (leftArrayStart <= mid && rightArrayStart <= r) {

            if (array[leftArrayStart] < array[rightArrayStart]) {
                mTempList.add (array[leftArrayStart++])
            } else {
                mTempList.add (array[rightArrayStart++])
            }

        }

        //判断左边数组是否还有没有归并的数据

        while (leftArrayStart <= mid) {
            mTempList.add (array[leftArrayStart++])

        }
        //判断右边数组是否还有没有归并的数据
        while (rightArrayStart <= r) {
            mTempList.add ( array[rightArrayStart++])
        }

        //最后把这个list 重新写入到array 对应的位置上

        for (i in l..r) {

            array[i] = mTempList[i-l]
        }
    }

    var mTempList = mutableListOf<Int>()

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {


            val cs = MergeSort()

            val array = arrayListOf(1, 2, 3, 1, 5, 6, 7, 7).toIntArray()
            cs.mergeSort(array, 0, array.size - 1)
            println("${Arrays.toString(array)}  归并排序 ${Arrays.toString(array)}")

        }
    }
}