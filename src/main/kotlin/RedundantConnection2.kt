import java.util.*

/**
 * create by xuexuan
 * time 2020/5/6 11:40
 *
 *
 * 685. 冗余连接 II
 *
 *
 * 在本问题中，有根树指满足以下条件的有向图。该树只有一个根节点，所有其他节点都是该根节点的后继。每一个节点只有一个父节点，除了根节点没有父节点。
 * 输入一个有向图，该图由一个有着N个节点 (节点值不重复1, 2, ..., N) 的树及一条附加的边构成。附加的边的两个顶点包含在1到N中间，这条附加的边不属于树中已存在的边。
 * 结果图是一个以边组成的二维数组。 每一个边 的元素是一对 [u, v]，用以表示有向图中连接顶点 u and v和顶点的边，其中父节点u是子节点v的一个父节点。
 * 返回一条能删除的边，使得剩下的图是有N个节点的有根树。若有多个答案，返回最后出现在给定二维数组的答案。
 *
 */


class RedundantConnection2 {

    /**
     *
     *
     *  并查集 的使用
     *
     *  并查集并查集是一种树型的数据结构，用于处理一些不交集（Disjoint Sets）的合并及查询问题。
     *
     *  两个关键操作：查找、合并
     *
     *  查找：不断查询点A的上级，最终返回最上级
     *
     *  合并：通过查询，如果点A 点B 的最上级不同。就可以选择合并，这样连通量就减一。
     *
     *  连通量指的是，这个所有点中，能够彼此互相连通的区域
     *
     *
     *
     *  n个点 n条边，有一条边是多出来的，分为三种情况：
     *  1、多余的边，指定顶点 （可以通过并查集，排查出多余的边）
     *  2、多余的边，指向点A（非顶点），点A为终点，也就是入度2 出度0 。 删除终点的任意一边即可，因为没有出度
     *  3、多余的边，指向点A（非顶点），点A为非终点，也就是入度2 出度1 。删除终点适当的一边即可，因为有出度，如果删除错误，
     *     会导致不连通，也就是连通量 增加了1
     *
     *  2、3 情况可以合并
     *
     */
    fun findRedundantDirectedConnection(edges: Array<IntArray>): IntArray {

        var ans = IntArray(2)

        //如果没有找到终点，那么多余的边就是指向顶点
        var endNode: Int? = null

        mConnectedCount = edges.size
        mNodes = IntArray(mConnectedCount + 1)

        for (i in 1..mConnectedCount) {

            mNodes[i] = i

        }

        //计算每个节点的入度，找到终点end
        val entryMap = hashMapOf<Int, Int>()
        for (e in edges) {

            entryMap[e[1]] = (entryMap[e[1]] ?: 0) + 1

            if (entryMap[e[1]] == 2) {
                endNode = e[1]
                break
            }
        }

        // 多余的边，指向的是非顶点。也就是2、3 情况
        if (endNode != null) {
            var firstEdge = IntArray(2)
            var secondEdge = IntArray(2)
            var isFirstEnd = true
            for (e in edges) {

                if (e[1] == endNode) {
                    //访问到末尾的节点
                    if (isFirstEnd) {

                        isFirstEnd = false
                        join(e[0], e[1])
                        firstEdge = e

                    } else {
                        secondEdge = e
                    }
                } else {
                    join(e[0], e[1])
                }
            }

            return if (mConnectedCount == 1) {
                //如果连通量是1，说明firstEdge可以接受的，secondEdge（靠后边） 将被抛弃
                secondEdge
            } else {
                firstEdge
            }

        } else {

            for (e in edges) {

                if (isConnect(e[0], e[1])) {
                    return e
                }
                join(e[0], e[1])
            }

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
        mConnectedCount--
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
    var mConnectedCount: Int = Int.MAX_VALUE

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = RedundantConnection2()
            val time = System.currentTimeMillis()

            val array = Array(4) { it ->
                when (it) {
                    0 -> intArrayOf(2, 1)
                    1 -> intArrayOf(3, 1)
                    2 -> intArrayOf(4, 2)
                    3 -> intArrayOf(1, 4)
//                    4 -> intArrayOf(2, 4)
                    else -> intArrayOf(1, 5)
                }
            }
            cs.mConnectedCount = array.size

            println("${Arrays.toString(array)} 最后一个冗余链接 ${Arrays.toString(cs.findRedundantDirectedConnection(array))}")
            println("用时：${System.currentTimeMillis() - time}")

        }
    }
}