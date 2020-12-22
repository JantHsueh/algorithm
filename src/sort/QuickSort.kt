package sort

import java.util.*

/**
 * create by xuexuan
 * time 2020/4/9 14:45
 *
 * 快速排序，分而治之
 * 把无序序列分为两堆，保证左边的序列，都比右边的序列小。一直这样细分下去，直到剩两个元素排序后，就完成了所有的排序
 * 最好情况，平均情况：T(n) = O(n) + 2T((n-1)/2) = O(nlogn)  解决这种关系式类型的标准数学归纳法技巧告诉我们 时间复杂度
 * 最坏情况：T(n) = T(n-1)+T(0) + O(n) = O(n²)
 *
 * 最好情况，最坏情况 取决于候选轴点
 */

class QuickSort {


    /**
     * 排序后，左小右大
     * 递归实现
     */
    fun quickSort(array: IntArray, left: Int, right: Int): IntArray {


        if (left < right) {

            val t = partition2(array, left, right)
            quickSort(array, left, t - 1)
            quickSort(array, t + 1, right)
        }

        return array
    }


    /**
     * 排序后，左小右大
     * 非递归 ，使用栈实现
     */
    fun quickSort1(array: IntArray, left: Int, right: Int): IntArray {

        val s = Stack<Int>()
        s.push(right)
        s.push(left)

        while (s.isNotEmpty()) {

            val l = s.pop()
            val r = s.pop()

            if (l < r) {
                val t = partition2(array, l, r)
                if (l < t) {
                    s.push(t - 1)
                    s.push(l)
                }

                if (t < r) {
                    s.push(r)
                    s.push(t + 1)
                }

            }
        }


        return array
    }


    /**
     *
     * 寻找轴点
     *
     * 方式一：
     * 左小右大，那么轴点：左边的元素都比轴点元素小，右边所有的元素都比轴点大
     * 左右两边不断增大，最后定出轴点的位置
     */
    fun partition(array: IntArray, left: Int, right: Int): Int {

        val pivot = array[left]
        var l = left
        var r = right

        while (l < r) {
            //找到从右往左，第一个小于轴点的数
            while (l < r && pivot <= array[r]) {
                r--
            }

            //小于轴点的值，放在左边
            if (l < r) {
                array[l] = array[r]
                l++
            }


            //找到从左往右，第一个大于等于轴点的数
            while (l < r && array[l] < pivot) {
                l++
            }
            //大于等于轴点的值，放在右边
            if (l < r) {
                array[r] = array[l]
                r--
            }
        }
        //上面每一次操作都判断，l < r,所以退出大循环，肯定是l =r，不可能出现l > r
        array[l] = pivot
        return l
    }


    /**
     *
     * 寻找轴点
     *
     * 方式二：序列分为三段
     * 左：小于轴点 中：大于等于轴点，右：待比较
     *
     */
    fun partition1(array: IntArray, left: Int, right: Int): Int {

        val pivot = array[left]
        //左段序列的最后索引
        var lo = left
        //中段序列的最后索引
        var hi = left + 1

        //这里的i，可以看做是中段序列的最后索引，因为i 前面的数据，就是小于轴点 和大于等于轴点的两端
        for (i in left + 1..right) {

            if (pivot <= array[i]) {
                //不小于轴点，放在hi
                hi = i

            } else {
                //小于轴点，放在lo

                val t = array[i]
                array[i] = array[++lo]
                array[lo] = t

                //因为hi段在lo段，后面，lo增加元素，hi要后移
                hi++
            }
        }

        //最后把轴点，和lo交换
        val t = array[lo]
        array[lo] = pivot
        array[left] = t
        return lo
    }


    /**
     *
     * 寻找轴点
     *
     * 方式二简化：序列分为三段
     * 左：小于轴点 中：大于等于轴点，右：待比较
     *
     */
    fun partition2(array: IntArray, left: Int, right: Int): Int {

        val pivot = array[left]
        //左段序列的最后索引
        var lo = left

        //中段序列的最后索引
        //这里的hi，可以看做是中段序列的最后索引，因为hi 前面的数据，就是小于轴点 和大于等于轴点的两端
        for (hi in left + 1..right) {

            if (array[hi] < pivot) {
                //小于轴点，放在lo
                val t = array[hi]
                array[hi] = array[++lo]
                array[lo] = t

                //因为hi段在lo段，后面，lo增加元素，hi要后移，在下一次循环，会自动++
            }
        }

        //最后把轴点，和lo交换
        val t = array[lo]
        array[lo] = pivot
        array[left] = t
        return lo
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = QuickSort()

            val list = arrayListOf(4, 2, 9, 1, 5, 6, 3, 7)
//            val list = arrayListOf(1, 2, 3, 4, 5, 6, 8, 9)
            println("$list  快速排序 ${Arrays.toString(cs.quickSort1(list.toIntArray(), 0, list.size - 1))}")

        }
    }
}