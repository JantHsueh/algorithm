import java.util.*

/**
 * create by xuexuan
 * time 2020/5/8 14:38
 *
 * 212. 单词搜索 II
 *
 * 给定一个二维网格 board 和一个字典中的单词列表 words，找出所有同时在二维网格和字典中出现的单词。
 * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母在一个单词中不允许被重复使用。
 */


class WordSearch2 {


    val ans = mutableSetOf<String>()
    val matchWord = StringBuffer()


    /**
     * 使用trie 树，进行字符串匹配
     * 使用回溯算法，时间负责度
     */
    fun findWords(board: Array<CharArray>, words: Array<String>): List<String> {


        //构建trie 树
        val root = Trie()
        var node = root.mRoot
        //时间复杂度O(n)
        for (w in words) {
            root.insert(w)
        }

//        O(M(4⋅3^(L−1)))，其中MM 是二维网格中的单元格数，LL 是单词的最大长度。
//        假设单词的最大长度是 L，从一个单元格开始，最初我们最多可以探索 4 个方向。假设每个方向都是有效的（即最坏情况），
//        在接下来的探索中，我们最多有 3 个相邻的单元（不包括我们来的单元）要探索。因此，在回溯探索期间，我们最多遍历 4⋅3 ^(L−1) 个单元格。

        board.forEachIndexed { index, chars ->
            chars.forEachIndexed { index1, _ ->
                matchWord.delete(0, matchWord.length)
//                search(node, board, index, index1)
                search1(node, board, index, index1)
            }
        }


        return ans.toList()
    }


    fun search(node: TrieNode1?, board: Array<CharArray>, x: Int, y: Int) {


        if (x < 0 || y < 0 || x >= board.size || y >= board[0].size) {
            return
        }

        var n = node
        val c = board[x][y]
        //在一次回溯中，防止重复访问同一个元素
        board[x][y] = '#'
        if (n?.containsKey(c) == true) {

            matchWord.append(c)
            n = n.get(c)

            if (n?.mEnd == true) {
                ans.add(matchWord.toString())
//                matchWord.delete(0,matchWord.length)

                //这里不能返回，例如：dog，dogs，在index, index1 进入回溯， 如果只匹配到dog 就返回，会漏掉dogs
//                return true
            }

            search(n, board, x - 1, y)
            search(n, board, x + 1, y)
            search(n, board, x, y - 1)
            search(n, board, x, y + 1)

            //上面这样的回溯有问题，例如 [[a,b],[c,d]] 有单词ac  ab
            // 是这样回溯的从 0,0 -> 1,0(找到ac)  0,1
            // 1,0 -> 0,0(回溯过)   1,1 终止
            // 0,1( b 也有终止符，上一个单词ac + b = acb)


            //所以在某一个点回溯完成后，回溯到上一层，需要减去已经匹配到的最后一个字符
            matchWord.delete(matchWord.length - 1, matchWord.length)

        }

        board[x][y] = c

        return

    }


    fun search1(parent: TrieNode1?, board: Array<CharArray>, x: Int, y: Int) {


        if (x < 0 || y < 0 || x >= board.size || y >= board[0].size) {
            return
        }

        val c = board[x][y]
        var n:TrieNode1? = null
        //在一次回溯中，防止重复访问同一个元素
        board[x][y] = '#'
        if (parent?.containsKey(c) == true) {

            matchWord.append(c)

            n = parent?.get(c)

            if (n?.mEnd == true) {
                ans.add(matchWord.toString())
//                matchWord.delete(0,matchWord.length)

                //这里不能返回，例如：dog，dogs，在index, index1 进入回溯， 如果只匹配到dog 就返回，会漏掉dogs
//                return true
            }

            search1(n, board, x - 1, y)
            search1(n, board, x + 1, y)
            search1(n, board, x, y - 1)
            search1(n, board, x, y + 1)

            //上面这样的回溯有问题，例如 [[a,b],[c,d]] 有单词ac  ab
            // 是这样回溯的从 0,0 -> 1,0(找到ac)  0,1
            // 1,0 -> 0,0(回溯过)   1,1 终止
            // 0,1( b 也有终止符，上一个单词ac + b = acb)


            //所以在某一个点回溯完成后，回溯到上一层，需要减去已经匹配到的最后一个字符
            matchWord.delete(matchWord.length - 1, matchWord.length)

        }

        board[x][y] = c


        //剪枝，去掉trie的节点（因为节点肯定已经匹配了，并且不需要再次匹配），缩小trie 的规模，在存在一个单词，多种匹配的情况下，可以降低时间复杂度。
        //但是如果一个单词只有一个匹配，这样的剪枝并没有起到作用，反而会增加时间
        if (n != null && !n.hasChild()) {
            parent?.remove(c)
        }

        return

    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = WordSearch2()

//            val words = Array<String>(4){}
//            val words = arrayOf("oath", "pea", "eat", "rain")
//            val words = arrayOf("ab", "cb", "ad", "bd", "ac", "ca", "da", "bc", "db", "adcb", "dabc", "abb", "acb")
            val words = arrayOf("aaa")

            val board = Array(4) { it ->
                when (it) {
                    0 -> charArrayOf('a', 'a', 'a', 'a')
                    1 -> charArrayOf('a', 'a', 'a', 'a')
                    2 -> charArrayOf('a', 'a', 'a', 'a')
                    3 -> charArrayOf('a', 'a', 'a', 'a')
//                    2 -> charArrayOf('i', 'h', 'k', 'r')
//                    3 -> charArrayOf('i', 'f', 'l', 'v')
//                    4 -> arrayOf(2, 4)
                    else -> charArrayOf('w')
                }
            }
            print("${Arrays.toString(board)}  匹配的字符串 ${cs.findWords(board, words)}")

        }
    }

}