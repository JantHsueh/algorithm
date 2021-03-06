import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * create by xuexuan
 * time 2020/4/26 17:43
 *
 *
 * 743. 网络延迟时间
 *
 * 有 N 个网络节点，标记为 1 到 N。
 * 给定一个列表 times，表示信号经过有向边的传递时间。 times[i] = (u, v, w)，其中 u 是源节点，v 是目标节点， w 是一个信号从源节点传递到目标节点的时间。
 * 现在，我们从某个节点 K 发出一个信号。需要多久才能使所有节点都收到信号？如果不能使所有节点收到信号，返回 -1。
 *
 */
class ShortestPathTwoNode {


    /**
     * 减而治之
     * Dijkstra 发明的算法
     *
     * 单起点，到达其他点的最短路径, 待访问节点使用优先队列 进行保存
     *
     * 针对本题，就是目标起点到最后一个点的距离
     */
    fun networkDelayTime1(times: Array<IntArray>, k: Int, n: Int): Int {

        val graph = hashMapOf<Int, MutableList<IntArray>>()
        // 转换图的存储结构，map key存储节点，value 里面存储list<IntArray> 存储可到达的点及距离
        // 类似于邻接表
        times.forEach {

            if (!graph.containsKey(it[0])) {
                graph[it[0]] = mutableListOf()
            }
            graph[it[0]]?.add(intArrayOf(it[1], it[2]))
        }

        // 已经被访问的结点，存的是一个数据对，第一个值是节点索引，第二个值是该节点到起始点的距离
        val visitedMap = hashMapOf<Int, Int>()

        //待访问的结点，距离起点的，放在优先队列中
        val candidateQueue = PriorityQueue<Pair<Int, Int>> { o1, o2 -> o1.second - o2.second }


        candidateQueue.add(Pair(k, 0))

        while (candidateQueue.isNotEmpty()) {

            //选出候选节点中，距离起始点最近的点，进行访问
            val tmpNode = candidateQueue.poll()

            if (visitedMap.containsKey(tmpNode.first)) {
                continue
            }
            visitedMap[tmpNode.first] = tmpNode.second
            val criticalNodeList = graph[tmpNode.first]


            //新访问节点，可以访问到候选节点，更新候选节点
            if (criticalNodeList != null) {
                for (c in criticalNodeList) {

                    // 候选节点 c没有被访问 并且
                    // 当前访问节点（tmpNode） 到起始点的距离 + 候选节点（c）到当前访问节点的距离 < 候选节点到起始点的距离（candidateQueue），逻辑上是需要这样的判断，
                    // 但是为了简化在队列中查找指定候选点，就直接再次加入一个节点到优先级队列中，因为是优先级队列，所以数据大（到起始点的距离）的数据加载下面
                    // 相同的点A，到起始点的距离 A1 < A2 ,等待poll A2的时候，发现a点已经访问过了，于是continue 跳过
                    //
                    if (!visitedMap.containsKey(c[0])) {
                        candidateQueue.add(Pair(c[0], tmpNode.second + c[1]))
                    }

                }
            }
        }

        if (visitedMap.size != n) return -1
        var ans = 0

        visitedMap.map { ans = max(it.value, ans) }

        return ans
    }


    /**
     * 减而治之
     * Dijkstra 发明的算法 第二种实现
     *
     * 保存每个节点到起点的距离，来优化queue里保存的数据
     *
     * 单起点，到达其他点的最短路径, 待访问节点使用优先队列 进行保存
     *
     */
    fun networkDelayTime2(times: Array<IntArray>, k: Int, n: Int): Int {

        val graph = hashMapOf<Int, MutableList<IntArray>>()
        // 转换图的存储结构，map key存储节点，value 里面存储list<IntArray> 存储可到达的点及距离
        times.forEach {

            if (!graph.containsKey(it[0])) {
                graph[it[0]] = mutableListOf()
            }
            graph[it[0]]?.add(intArrayOf(it[1], it[2]))
        }

        // 已经被访问的结点，存的是一个数据对，第一个值是节点索引，第二个值是该节点到起始点的距离
        val visitedMap = hashMapOf<Int, Int>()

        //待访问的结点，距离起点的，放在优先队列中
        val candidateQueue = PriorityQueue<Pair<Int, Int>> { o1, o2 -> o1.second - o2.second }

        val distance = Array(n + 1) { Int.MAX_VALUE }

        distance[k] = 0
        candidateQueue.add(Pair(k, 0))


        while (candidateQueue.isNotEmpty()) {

            //选出候选节点中，距离起始点最近的点，进行访问
            val tmpNode = candidateQueue.poll()

            if (visitedMap.containsKey(tmpNode.first)) {
                continue
            }
            visitedMap[tmpNode.first] = tmpNode.second
            val criticalNodeList = graph[tmpNode.first]


            //增加更新候选节点，通过新节点，能访问到的点，都需要验证
            if (criticalNodeList != null) {
                for (c in criticalNodeList) {

                    // 候选节点 c没有被访问 并且 当前访问节点（tmpNode） 到起始点的距离 + 候选节点（c）到当前访问节点的距离 < 候选节点到起始点的距离（candidateQueue）
                    // 这里引入distance 来方便查找速度
                    if (!visitedMap.containsKey(c[0]) && tmpNode.second + c[1] < distance[c[0]]) {
                        distance[c[0]] = tmpNode.second + c[1]
                        candidateQueue.add(Pair(c[0], tmpNode.second + c[1]))
                    }

                }
            }
        }

        if (visitedMap.size != n) return -1
        var ans = 0

        visitedMap.map { ans = max(it.value, ans) }

        return ans
    }


    /**
     * Floyd 佛洛依德 算法
     *
     *
     * 以一点为中心，不断去优化能经过它的两点的距离，
     *
     */
    fun networkDelayTime3(times: Array<IntArray>, k: Int, n: Int): Int {

        val graph = Array(n + 1) { IntArray(n + 1) { -1 } }
        //使用邻接矩阵 来存储图
        times.forEach {
            graph[it[0]][it[1]] = it[2]
        }
        //点到达自身的距离是0
        for (i in 1..n) {
            graph[i][i] = 0
        }

        //中间转折点
        //这里遍历的顺序，先中间点，然后遍历起点终点，这样不会遗漏，保证所有能经过该点的路径值都是最优的
        //这样把所有的点都作为中间点遍历完后，整个图就是最优的
        for (z in 1..n) {
            //起点i
            for (i in 1..n) {
                //终点j
                for (j in 1..n) {
                    //进行松弛
                    if (graph[i][z] != -1 && graph[z][j] != -1) {

                        if (graph[i][j] != -1) {
                            graph[i][j] = min(graph[i][j], graph[i][z] + graph[z][j])
                        } else {
                            graph[i][j] = graph[i][z] + graph[z][j]
                        }
                    }

                }
            }
        }

        var ans = -1
        for (i in 1..n) {
            if (graph[k][i] == -1) {
                return -1
            }

            ans = max(ans, graph[k][i])
        }


        return ans
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = ShortestPathTwoNode()
            val time = System.currentTimeMillis()

            val array = Array(3) { it ->
                when (it) {
                    0 -> intArrayOf(2, 1, 1)
                    1 -> intArrayOf(2, 3, 1)
                    2 -> intArrayOf(3, 4, 1)
                    else -> intArrayOf(0)
                }
            }

            val n = 4
            val k = 2
            println("${Arrays.toString(array)} 网络延迟时间 ${cs.networkDelayTime3(array, k, n)}")
            println("用时：${System.currentTimeMillis() - time}")

        }
    }


}