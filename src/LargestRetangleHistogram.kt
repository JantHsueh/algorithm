import java.util.*
import kotlin.math.max

/**
 * create by xuexuan
 * time 2020/3/31 11:28
 *
 *
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。

 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 * LeetCode上这道题目和邓俊辉教授 所讲的，最大直方图是一样的。
 * 在一张边缘残缺的羊皮纸上，选出最大面积的矩形 的算法题目是本题的升级版
 *
 */

class LargestRetangleHistogram {




    /**
     * 单调栈的解法
     */
    fun largestRectangleArea(height: IntArray): Int {
        var m: Stack<Int> = Stack() //min  栈中的数据是递增的,存的是数组的索引值

        var maxArea: Int = 0
        var k = 0
//        m.push(0)

        while (k < height.size) {

            if (m.empty() || height[m.peek()] <= height[k]) {
                m.push(k)
                k++
            } else {
                while (height[m.peek()] > height[k]) {
                    maxArea = max(maxArea, height[k] * (k - m.pop()))
                }

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
            val cs = ContainerWithMostWater()


//            val intArray = arrayListOf(2,1,5,6,2,3) //预期 10
            val intArray = arrayListOf(1,1) //预期 2
            print("$intArray 最大盛水量 ${cs.maxArea(intArray.toIntArray())}")

        }
    }
}