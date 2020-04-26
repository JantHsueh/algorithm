import java.util.*

/**
 * create by xuexuan
 * time 2020/4/26 17:43
 *
 * 单起点，到达其他点的最短路径
 *
 */
class ShortestPathTwoNode {


    /**
     * 减而治之
     * Dijkstra 发明的算法
     * 这个算法不适合，这里，他是计算，单起点的最短路径
     */
    fun shortestPathLength1(graph: Array<IntArray>): Int {

        var ans = 0

        // 已经被访问的结点，
        //未被访问的结点，放在优先队列中


        return 0
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = ShortestPathTwoNode()
            val time = System.currentTimeMillis()

            val array = Array(4) { it ->
                when (it) {
                    0 -> intArrayOf(1, 2, 3)
                    1 -> intArrayOf(0)
                    2 -> intArrayOf(0)
                    3 -> intArrayOf(0)
                    else -> intArrayOf(0)
                }
            }
            println("${Arrays.toString(array)} 最短路径 ${cs.shortestPathLength1(array)}")
            println("用时：${System.currentTimeMillis() - time}")

        }
    }



}