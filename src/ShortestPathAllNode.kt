import java.util.*
import kotlin.math.min

/**
 * create by xuexuan
 * time 2020/4/26 11:24
 *
 *
 * 847. 访问所有节点的最短路径
 *
 * 给出 graph 为有 N 个节点（编号为 0, 1, 2, ..., N-1）的无向连通图。 

 * graph.length = N，且只有节点 i 和 j 连通时，j != i 在列表 graph[i] 中恰好出现一次。

 * 返回能够访问所有节点的最短路径的长度。你可以在任一节点开始和停止，也可以多次重访节点，并且可以重用边。

 */

class ShortestPathAllNode {


    /**
     * 广度优先搜索 BFS
     *
     * 从每一个点出发，进行BFS搜索，直到所有的点都被遍历。
     *
     *
     * BFS 搜索需要使用队列，这里队列里保存 当前点和到达当前点所经过的点（因为到达当前点，可能是由于不同的起点，所以需要保存到达它的点路径）
     *
     * 时间复杂度：O(2^N * N)

    空间复杂度：O(2^N * N)
     *
     */
    fun shortestPathLength1(graph: Array<IntArray>): Int {

        val N = graph.size

        //dist、queue 中，一维值是路径经过的点(包含当前点，如果已经访问过就标1)，二维值是路径最前端的点的索引（值是在图中的点的索引）
        //例如：[3,1] 表示 3= 11 经过第0个点和第1个点，当前位置在1
        val queue = LinkedList<Pair<Int, Int>>()
        //使用dist 来保存经过不同点的组合的路径长度，和递归优化的原理一样
        val dist = Array(1 shl N) { IntArray(graph.size) { N * N } }

        for (i in 0 until N) {

            //每个点的起始点，和途径点是自己
            queue.offer(Pair(1 shl i, i))
            //自己到自己的距离是0
            dist[1 shl i][i] = 0
        }

        while (queue.isNotEmpty()) {

            val node = queue.pop()

            val d = dist[node.first][node.second]


            //遍历过所有的点，就是所有的位都是1，返回距离
            if (node.first == (1 shl N) - 1) return d

            for (next in graph[node.second]) {

                //把next点 加入到已经访问过的点中
                val path = node.first or (1 shl next)

                //d 经过node.first 上的点的路径的长度，
                //dist[path][next] 是在 node.first 的基础上，增加一个next点，如果此时的路径差值不是1，说明绕远了，对路径值进行更新
                //虽然起点不同，但是经过这样不断更新路径，如果路径相同，就使用最小路径，这样到达全部点的时候，路径是最小的
                if (d + 1 < dist[path][next]) {

                    dist[path][next] = d + 1

                    queue.offer(Pair(path, next))
                }
            }
        }

        return 0
    }


    /**
     * 动态规划
     *
     * 上面的解法，使用 广度优先搜索，来不断优化 dist ，保证到下一个结点，距离只加一。这样就能保证访问所有的点，距离是最短的
     *
     * 既然到下一个点的距离加1 是最优的，那么可以通过动态规划，来得到下一个点的距离，
     *
     *
     */
    fun shortestPathLength2(graph: Array<IntArray>): Int {

        val N = graph.size

        //dist、queue 中，一维值是路径经过的点(包含当前点，如果已经访问过就标1)，二维值是路径最前端的点的索引（值是在图中的点的索引）
        //例如：[3,1] 表示 3= 11 经过第0个点和第1个点，当前位置在1

        //使用dist 的状态值是，经过指定点的最短路径
        val dist = Array(1 shl N) { IntArray(graph.size) { N * N } }

        for (i in 0 until N) {
            //自己到自己的距离是0
            dist[1 shl i][i] = 0
        }


        for (path in 0 until (1 shl N)) {

            var repeat = true

            while (repeat) {
                repeat = false
                for (head in 0 until  N) {

                    //例如路径是path = 3 =  0011 但是head 是 3，路径没有访问到第三个点，这种应该过滤掉
                    if ((path and (1 shl head)) == 0){
                        continue
                    }
                    val d = dist[path][head]

                    //把next点 加入到已经访问过的点中
                    for (next in graph[head]) {

                        val path2 = path or (1 shl next)

                        if (d + 1 < dist[path2][next]) {
                            dist[path2][next] = d + 1

                            //路径B 更新了之前相同路径A的距离，很有可能，在前面基于路径A的到head的路径，计算是错误的
                            //所以需要重新计算一次
                            if (path == path2) repeat = true

                        }
                    }


                }
            }
        }

        var ans = N * N
        for (a in dist[(1 shl N) - 1]) {
            ans = min(a, ans)
        }

        return ans
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = ShortestPathAllNode()
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
            println("${Arrays.toString(array)} 最短路径 ${cs.shortestPathLength2(array)}")
            println("用时：${System.currentTimeMillis() - time}")

        }
    }

}