import java.util.*
import kotlin.math.min

/**
 * create by xuexuan
 * time 2020/5/7 10:45
 *
 * 最小生成树 最小伸展树
 *
 * 1098 : 最小生成树二·Kruscal算法
 * https://hihocoder.com/problemset/problem/1098?sid=1575835
 *
 */

class MinimumSpanningTree {


    /**
     * 贪心策略
     *
     * Prim 算法，选一点A，采纳到达点A的最小的边，继续采纳到达这两个点 的最小的边，这样不断扩大树的规模，直到所有的点被加入
     */
    fun prim(edgeNum: Int, nodeNum: Int, x: IntArray, y: IntArray, w: IntArray): Int {


        var ans = 0

        //使用邻接矩阵
        val nodeMatrix = Array(nodeNum + 1) { it -> IntArray(nodeNum + 1) { it1 -> if (it == it1) 0 else Int.MAX_VALUE } }

        for (i in x.indices) {
           //如果是无向图，需要这样设置临街矩阵，保障x-y  y-x 都是最小边
            nodeMatrix[x[i]][y[i]] = min(w[i], nodeMatrix[x[i]][y[i]])
            nodeMatrix[y[i]][x[i]] = min(w[i], nodeMatrix[y[i]][x[i]])

        }

        //已经访问过的点,0表示未访问 1 表示访问
        val visitedNode = IntArray(nodeNum + 1) { 0 }

        //未访问的点A  到已经被访问过的所有点的（图）最短距离
        val distanceNotVisitedToVisited = IntArray(nodeNum + 1) { i -> if (i == 1) 0 else Int.MAX_VALUE }


        //每次循环添加一个点，所以需要n次循环
        for (i in 1..nodeNum) {

            var min = Int.MAX_VALUE
            var candidateNode: Int? = null


            //在所有图外点里面选择一个距离图片距离最短的点
            for (j in 1..nodeNum) {
                if (visitedNode[j] == 0 && distanceNotVisitedToVisited[j] < min) {
                    min = distanceNotVisitedToVisited[j]
                    candidateNode = j
                }
            }

            //把这个点加入到图中
            if (candidateNode != null) {

                visitedNode[candidateNode] = 1

                ans += distanceNotVisitedToVisited[candidateNode]
                //更新图外点 ，到图的距离
                for (z in 1..nodeNum) {

                    if (visitedNode[z] == 0 && nodeMatrix[candidateNode][z] < distanceNotVisitedToVisited[z]) {
                        distanceNotVisitedToVisited[z] = nodeMatrix[candidateNode][z]
                    }
                }
            }
        }

        return ans
    }


    /**
     * 贪心策略
     *
     * Kruskal 算法，把所有的边进行排序，从小到大，依次选取所有的边，逐步扩大树的规模
     * （如果边没有重复）在第三条边开始，计算边所在的顶点是否在已得到的树中，如果在则忽略该边，如果没有则加入该边，扩大树的规模
     *
     * x 到 y 的距离是w
     *
     * 使用并查集，来判断是否连通
     *
     * 时间复杂度 O(n + n + n log* n )
     */
    fun kruskal(edgeNum: Int, nodeNum: Int, x: IntArray, y: IntArray, weight: IntArray): Int {


        var ans = 0
        //把x y w 使用优先级队列整理，
        val heap = PriorityQueue<Pair<Int, Pair<Int, Int>>>() { o1, o2 -> o1.first - o2.first }

        mNodes = IntArray(nodeNum)

        for (i in 0 until mNodes.size) {
            mNodes[i] = i
        }


        for (i in 0 until weight.size) {
            heap.add(Pair(weight[i], Pair(x[i], y[i])))
        }


        while (!heap.isEmpty()) {

            val w = heap.poll()
            val edge = w.second

            if (isConnect(edge.first, edge.second)) {
                //如果这两个点是连通的，说明已经有其他更小的边连接了他们
                continue
            }

            join(edge.first, edge.second)
            ans += w.first
        }

        return ans
    }


    /**
     * 查找指定点，顶级的点
     */
    fun find(n: Int): Int {

        var node: Int = n

        while (node != mNodes[node]) {
            //压缩路径,因为最后判断两个节点是否连通，看是否有相同的顶级节点来看，所以可以把孙子节点，提升为孩子节点
            //使用路径优化，是个多叉树，可以把查找的时间复杂度是O(log* n)，接近于O(1)
            mNodes[node] = mNodes[mNodes[node]]
            node = mNodes[node]
        }
        return node
    }

    /**
     *  连接两点
     */
    fun join(a: Int, b: Int) {

        val topA = find(a)
        val topB = find(b)

        //这两个点是连通的
        if (topA == topB) return

        //如果不连通，则随机把一个顶级节点，放在另一个点击节点下面
        mNodes[topA] = topB

        return
    }


    /**
     * 判断两点是否连通
     */
    fun isConnect(a: Int, b: Int): Boolean {

        val topA = find(a)
        val topB = find(b)
        return topA == topB

    }


    var mNodes: IntArray = intArrayOf()


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = MinimumSpanningTree()
            val time = System.currentTimeMillis()


            val s = Scanner(System.`in`)
            val nodeNum = s.nextInt()
            val edgeNum = s.nextInt()

            val x = IntArray(edgeNum)
            val y = IntArray(edgeNum)
            val w = IntArray(edgeNum)


            for (i in 0 until edgeNum) {
                x[i] = s.nextInt()
                y[i] = s.nextInt()
                w[i] = s.nextInt()
            }


            println("${Arrays.toString(x)} 最小生成树 ${cs.prim(edgeNum, nodeNum, x, y, w)}")
            println("用时：${System.currentTimeMillis() - time}")

        }
    }


}