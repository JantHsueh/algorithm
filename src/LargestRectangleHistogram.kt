import java.util.*

/**
 * create by xuexuan
 * time 2020/3/31 11:28
 * 84、 柱状图中最大的矩形
 *
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。

 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 * LeetCode上这道题目和邓俊辉教授 所讲的，最大直方图是一样的。
 * 在一张边缘残缺的羊皮纸上，选出最大面积的矩形 的算法题目是本题的升级版
 *
 */

class LargestRectangleHistogram {


    /**
     * 单调栈的解法  时间复杂度O（n）  空间复杂度O（n）
     */
    fun largestRectangleArea(height: IntArray): Int {

        val hl = height.toMutableList()
        //增加头部哨兵，防止第一个元素被弹出，不便于计算
        hl.add(0, -1)
        //增加尾部哨兵，可以不用在结束循环判断后，再次判断栈中元素是否不为空。
        // 例如：[1,2,3,4,5],没有出栈，需要最后在判断一次，如果加入哨兵则不需要独立判断
        hl.add(hl.size, -1)
        val m: Stack<Int> = Stack() //min  栈中的数据是递增的,存的是数组的索引值
        m.push(0)
        var maxArea: Int = 0
        var k = 1

        //每个元素进出栈各一次，这个while循环最多2n次，时间复杂度是O（n）
        while (k < hl.size) {

            if (hl[m.peek()] != -1 && hl[m.peek()] > hl[k]) {
                val top = hl[m.pop()]
                maxArea = Math.max(maxArea, top * (k - m.peek() - 1))
            } else {
                m.push(k)
                k++
            }
        }

        return maxArea
    }


    /**
     * 线段树的解法
     */
    fun largestRectangleArea1(height: IntArray): Int {

        var maxArea: Int = 0

        return maxArea
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = LargestRectangleHistogram()


//            val intArray = arrayListOf(2, 1, 5, 6, 2, 3) //预期 10
//            val intArray = arrayListOf(1,1) //预期 2
//            val intArray = arrayListOf(5) //预期 5
//            val intArray = arrayListOf(0, 9) //预期 9
            val intArray = arrayListOf(9) //预期 9
//            val intArray = arrayListOf(2, 1, 2) //预期 3
            print("$intArray 柱状图中最大的矩形 ${cs.largestRectangleArea(intArray.toIntArray())}")

        }
    }
}