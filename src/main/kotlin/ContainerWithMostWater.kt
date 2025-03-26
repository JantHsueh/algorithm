/**
 * create by xuexuan
 * time 2020/3/30 10:44
 *
 * 11. 盛最多水的容器
 * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * 说明：你不能倾斜容器，且 n 的值至少为 2。
 *
 */
class ContainerWithMostWater {


    /**
     *
     * 双指针
     *
     * 原理分析：两个指针在首尾两端，不断更新这两个指针中的最小者，虽然彼此距离，但是最低高度增加了，有可能面积会增大
     * 因为水面高度是由最低的决定，所以如果不更新最低者，彼此距离近了，只会是更小的面积
     *
     */
    fun maxArea(height: IntArray): Int {


        var maxArea: Int = 0

        var head = 0
        var tail = height.size - 1

        maxArea = Math.max(maxArea, (tail - head) * Math.min(height[head], height[tail]))

        for (k in 0 until height.size) {


            if (head < tail) {

                //更新首尾的较小者
                if (height[head] > height[tail]) {
                    //更新尾部
                    if (height[tail] < height[--tail]) {
                        maxArea = Math.max(maxArea, (tail - head) * Math.min(height[head], height[tail]))
                    }
                } else {
                    //更新首部

                    if (height[head] < height[++head]) {
                        maxArea = Math.max(maxArea, (tail - head) * Math.min(height[head], height[tail]))
                    }
                }
            }
        }

        return maxArea
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = ContainerWithMostWater()

            //预期值 49
//            val intArray = arrayListOf(1, 8, 6, 2, 5, 4, 8, 3, 7)
            //预期值 4
//            val intArray = arrayListOf(1, 2, 4, 3)
//            val intArray = arrayListOf(2,3,10,5,7,8,9) //预期 36
//            val intArray = arrayListOf(2,3,4,5,18,17,6) //预期 17
            val intArray = arrayListOf(1,1) //预期 1
            print("$intArray 最大盛水量 ${cs.maxArea(intArray.toIntArray())}")

        }
    }
}