import java.util.*

/**
 * create by xuexuan
 * time 2020/4/21 17:52
 *
 *
 * 邓俊辉算法训练营 象棋
 *
 *
 * 在一个给定的棋盘上，有的地方不可以放“车”，有的地方可以放，不妨就用0,1来表示
 * 在一个n×n的棋盘上你能放多少个“车”呢？
 *
 *
 * 输入
 * 第一行为一个正整数n。

 * 接下来n行，每行包含n个整数，若为0表示这个位置不能放“车”；若为1表示这个位置可以放“车”。


 * 输出
 * 输出一个整数，表示最多能放多少个“车”。

 * 样例1输入
 * 5
 * 1 0 0 0 0
 * 0 0 0 0 0
 * 0 0 0 1 0
 * 1 1 0 1 0
 * 0 0 0 1 0
 *
 *
 * 样例1输出
 * 3
 *
 * 样例1解释
 *
 * 我们这样放就只能放2个“车”：
 * 车 0 0 0 0
 * 0 0 0 0 0
 * 0 0 0 1 0
 * 1 0 0 车 0
 * 0 0 0 1 0
 *
 * 若我们这样放就能放下3个了：
 * 车 0 0 0 0
 * 0 0 0 0 0
 * 0 0 0 1 0
 * 1 车 0 1 0
 * 0 0 0 车 0
 *
 */

class ChessNCar {


    lateinit var node: Array<LinkedList<Int>?>

    val num = 5

    val matrix = arrayOf(
        intArrayOf(1, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 1, 0),
        intArrayOf(1, 1, 0, 1, 0),
        intArrayOf(0, 0, 0, 1, 0)
    )

    //被匹配的y组的点，这里可以看做是y点的组元素，是否被访问
    var visited = Array<Boolean>(num) { false }
    //索引是y值，值是x 值，表示y对应的匹配点 x
    var match = Array<Int>(num) { -1 }


    /**
     * 这个题目的解法，使用匈牙利算法，邻接表（也可以使用邻接矩阵）
     *
     * 使用广度优先搜索，不断寻找增广路，增加匹配边数
     *
     * 把棋盘中所有点的x,y 看成一个二步图的两组元素
     *
     */
    fun putCar(num: Int, checkerboard: Array<IntArray>): Int {


        var ans = 0
        //棋盘可以放棋子的位置数量的x点数字，也就是二步图的一组元素，数组的值是可与x 匹配的y 的值的链表
        node = arrayOfNulls(this.num)


        //生成邻接表
        checkerboard.forEachIndexed { index, ints ->
            val list = LinkedList<Int>()
            ints.forEachIndexed { index2, ints2 ->
                if (ints2 == 1) {
                    list.add(index2)
                }
            }

            node[index] = list

        }


        //dfs 寻找增广路径
        for (n in 0 until num) {
            if (dfs(n)) {
                ans++
            }
        }

        return ans
    }



    /**
     * 广度优先搜索，为 x 寻找一个匹配
     *
     * 寻找增广路，然后把增广路的奇数边 ，加入匹配，把增广路的偶数边，从匹配中删除。这样原匹配数量增加 1
     *
     * 下面这个算法，就是在做这样的事情
     *
     */
    private fun dfs(x: Int): Boolean {


        val yLinked = node[x]
        if (yLinked == null || yLinked.isEmpty()){
            return false
        }

        //找到与 x 对应的那个链表
        for (index in 0 until yLinked.size) {
            //遍历链表中的每一个y
            val y = yLinked[index]

            //判断这个y，在本次getAns 的寻找中是否已经被其他x 占用了
            if (!visited[y]) {
                visited[y] = true

                if (match[y] == -1 || dfs(match[y])) {
                    //如果y 没有匹配，就给y匹配一个
                    //如果y1已经匹配（因为同一个y1可能对应两个x1，x2），就去看看已经与y1 匹配的x1，他有没有对应其他的y2，如果有就把y1 与x2 匹配
                    //这样保证了，每一次可能匹配的都是被匹配到的
                    match[y] = x
                    return true
                }
            }
        }
        return false
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = ChessNCar()
            val time = System.currentTimeMillis()

            println("${cs.num} 个位置可以放 ，棋盘 ${cs.matrix} ，可以放 ${cs.putCar(cs.num, cs.matrix)}个车")
            println("用时：${System.currentTimeMillis() - time}")

        }
    }
}