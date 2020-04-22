/**
 * create by xuexuan
 * time 2020/4/22 11:43
 *
 * 51. N皇后
 *
 * n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 *
 * 给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。
 * 每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 * 输入: 4
 * 输出: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
 * 解释: 4 皇后问题存在两个不同的解法。
 *
 */
class NQueens {


    var n: Int = 0
    //主对角线上是否有皇后，（主对角线上某线上的所有点的 x -y 是相同的）
    lateinit var mMainDiagonal: IntArray

    //次对角线上是否有皇后（主对角线上某线上的所有点的 x  + y 是相同的）
    lateinit var mSecondDiagonal: IntArray


    //某一列上是否有皇后
    lateinit var mCol: IntArray

    //皇后的位置，因为每一行都会有皇后，所以这里的索引值是row，数组值是column
    lateinit var mQueue: IntArray

    //所有不同的解法
    var ans = mutableListOf<List<String>>()

    /**
     * 使用回溯法
     *
     */
    fun solveNQueens(n: Int): List<List<String>> {

        mMainDiagonal = IntArray(2 * n - 1) { 0 }

        mSecondDiagonal = IntArray(2 * n - 1) { 0 }

        mCol = IntArray(n) { 0 }

        mQueue = IntArray(n) { 0 }

        this.n = n

        backtrack(0)
        return ans
    }

    /**
     * 在每一行放置一个皇后，并在行，列，主对角线，次对角线 进行标记
     *
     * 在下一行，可以放置的地方，放一个皇后
     */
    private fun backtrack(row: Int) {


        //在递归中，不断进行row +1
        if (row >= n) return

        //在row 行上，的所有列尝试放置皇后

        for (column in 0 until n) {

            //判断是否可以在 row 行 column列上放置皇后
            if (isNotUnderAttack(row, column)) {

                //放置皇后
                addQ(row, column)

                //如果是最后一行，这里不能return，因为需要回溯到第一行，选择另一列来 放置皇后
                if (row == n - 1) addSolution()

                //在下一行row +1 上放置皇后，一旦没有地方可以放置，backtrack 返回void，就执行下面的删除。
                //如果一直递归下去，都可以放置，就会在上面返回一个结果
                backtrack(row + 1)
                //删除这个位置的皇后
                removeQ(row, column)
            }
        }
    }

    /**
     * 结果中增加一种解法
     */
    private fun addSolution() {

        var solution = mutableListOf<String>()
        mQueue.forEachIndexed { indexR, indexC ->

            val rowString = StringBuilder()
            for (i in 0 until n) {
                if (i == indexC) {
                    rowString.append("Q")
                } else {

                    rowString.append(".")
                }
            }

            solution.add(rowString.toString())
        }
        ans.add(solution)
    }


    /**
     * 在第row 行 column 列上 放置一个皇后
     */
    private fun addQ(row: Int, column: Int) {
        mQueue[row] = column
        mCol[column] = 1
        mMainDiagonal[row - column + n - 1] = 1
        mSecondDiagonal[row + column] = 1

    }

    /**
     * 删除这个位置的皇后
     *
     */
    private fun removeQ(row: Int, column: Int) {

        mQueue[row] = 0
        mCol[column] = 0
        mMainDiagonal[row - column + n - 1] = 0
        mSecondDiagonal[row + column] = 0
    }

    /**
     * 是否不在攻击范围
     */
    private fun isNotUnderAttack(row: Int, column: Int): Boolean {

        //主对角线 的索引，为了避免出现负数，所以结果再加n
        return (mCol[column] + mMainDiagonal[row - column + n - 1] + mSecondDiagonal[row + column]) == 0
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = NQueens()
            val time = System.currentTimeMillis()

            println("${2} 阶棋盘，n皇后的解法 ${cs.solveNQueens(2)}")
            println("用时：${System.currentTimeMillis() - time}")

        }
    }


}