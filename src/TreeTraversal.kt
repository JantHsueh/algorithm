import java.util.*

/**
 * create by xuexuan
 * time 2020/12/24 4:49 下午
 */
class TreeTraversal {


    /**
     * 根 左 右
     * 递归 先序遍历
     */
    fun preorderTraversal(node: TreeNode?): List<Int> {
        var mResultList: MutableList<Int> = mutableListOf()

        recursionPreorderTraversal(node, mResultList)
        return mResultList

    }


    fun recursionPreorderTraversal(node: TreeNode?, resultList: MutableList<Int>) {

        if (node == null) {
            return
        }

        resultList.add(node?.value!!)
        recursionPreorderTraversal(node?.left, resultList)
        recursionPreorderTraversal(node?.right, resultList)
    }


    /**
     * 这个迭代遍历和LeetCode上的迭代遍历，思想不一样，
     * 该算法的思想是，先遍历根节点，遍历左子树，遍历右子树
     * 把一个节点的左右孩子，放入栈中，在访问其中一个节点的时候，进行左顾右盼，看是否有节点可以介入栈中，如此反复循环，直到栈为空
     * 先序遍历
     *
     *
     */
    fun preorderTraversal1(root: TreeNode?): List<Int> {


        var mResultList: MutableList<Int> = mutableListOf()

        var mStack: Stack<TreeNode> = Stack()

        if (root != null) {
            mStack.add(root)
        }
        mStack.add(root)

        while (mStack.isNotEmpty()) {

            val node = mStack.pop()

            mResultList.add(node.value)
            if (node.right != null) mStack.add(node.right)
            if (node.left != null) mStack.add(node.left)
        }

        return mResultList
    }

    /**
     *  先序遍历
     */
    fun preorderTraversal2(root: TreeNode?): List<Int> {


        var mResultList: MutableList<Int> = mutableListOf()

        var mStack: Stack<TreeNode> = Stack()

        if (root != null) {
            mStack.add(root)
        }
        mStack.add(root)

        while (mStack.isNotEmpty()) {

            val node = mStack.pop()

            mResultList.add(node.value)
            if (node.right != null) mStack.add(node.right)
            if (node.left != null) mStack.add(node.left)
        }

        return mResultList
    }


    /**
     * 递归 中序遍历
     *
     * 左孩子 根节点 右孩子
     */

    fun inorderTraversal(node: TreeNode?): List<Int> {
        var mResultList: MutableList<Int> = mutableListOf()

        recursionMiddleorderTraversal(node, mResultList)
        return mResultList
    }

    fun recursionMiddleorderTraversal(node: TreeNode?, resultList: MutableList<Int>) {

        if (node == null) {
            return
        }
        recursionMiddleorderTraversal(node?.left, resultList)
        resultList.add(node?.value!!)
        recursionMiddleorderTraversal(node?.right, resultList)
    }


    /**
     * 中序遍历
     */
    fun inorderTraversal2(root: TreeNode?): List<Int> {


        var mResultList: MutableList<Int> = mutableListOf()

        var mStack: Stack<TreeNode> = Stack()


        var node: TreeNode? = root
        while (mStack.isNotEmpty() || node != null) {


            //循环获取节点的左孩子，直到没有左孩子
            while (node != null) {
                mStack.add(node)
                node = node.left
            }
            //弹出后，先访问该节点，再访问它的右节点
            node = mStack.pop()

            mResultList.add(node.value)

            //以该节点的右孩子为基准，再次获取它的所有左孩子
            //如果是叶节点，那么node.right 为null，下一次循环会直接pop该叶节点
            //pop后，会遍历上面循环中，加入的那些左孩子的上一个节点A，也就是相当于访问了a的左子树，访问了a，接下来访问a的右子树
            node = node.right

        }

        return mResultList


    }


    /**
     * 递归 后续遍历
     *
     * 左子树 右子树 根节点
     */

    fun postorderTraversal(node: TreeNode?): List<Int> {
        var mResultList: MutableList<Int> = mutableListOf()

        recursionPostOrderTraversal(node, mResultList)
        return mResultList

    }


    fun recursionPostOrderTraversal(node: TreeNode?, resultList: MutableList<Int>) {

        if (node == null) {
            return
        }

        recursionPostOrderTraversal(node?.left, resultList)
        recursionPostOrderTraversal(node?.right, resultList)
        resultList.add(node?.value!!)
    }


    /**
     * 后续遍历
     * 该算法的思想是，按照左右根 的顺序 把节点 加入栈中，
     * 在弹出的时候，去判断该节点的直接节点是否添加到栈中。如果已经加入了，那么后序访问的时候就会先访问（按照后序，因为在添加的时候 考虑的是后序）。
     * 如果没有添加，那就把节点添加进去，开启下一次循环。也就是会访问该节点的左右节点，符合后序
     *
     */
    fun postorderTraversal1(root: TreeNode?): List<Int> {

        var mResultList: MutableList<Int> = mutableListOf()
        var mStack: Stack<TreeNode> = Stack()
        var node: TreeNode? = root
        mStack.add(node)

        while (mStack.isNotEmpty()) {

            //弹出栈顶，也就是局部的左孩子.首次为根节点
            node = mStack.pop()

            if (node?.visitOffspring == false) {
                mStack.add(node)
                if (node?.right != null) mStack.add(node?.right)
                if (node?.left != null) mStack.add(node?.left)

                node?.visitOffspring = true

                continue
            } else {

                //直接后代都访问过了，就访问它自己，也就是左 右 根的根节点
                mResultList.add(node.value)
                println(node.value)
            }

        }

        return mResultList
    }


    /**
     * 后续遍历
     * 下面的代码待完善
     *
     */
    fun postorderTraversal2(root: TreeNode?): List<Int> {

        var mResultList: MutableList<Int> = mutableListOf()
        var mStack: Stack<TreeNode> = Stack()
        var node: TreeNode? = root
        var prePartentNode: TreeNode? = null
        mStack.add(node)

        while (mStack.isNotEmpty()) {

            //弹出栈顶 .首次为根节点
            node = mStack.pop()

            if ((node?.right != null || node?.left != null) && node != prePartentNode) {
                prePartentNode = node
                mStack.add(node)
                if (node?.right != null) mStack.add(node?.right)
                if (node?.left != null) mStack.add(node?.left)


                continue
            } else {
                //两种情况可以访问当前节点 1、如果左右孩子都是空的 2、当前节点的左右子树都已经访问过了

                if (node == prePartentNode){
                    prePartentNode = node
                }
                mResultList.add(node.value)
                prePartentNode = node
                println(node.value)
            }

        }

        return mResultList
    }



    /**
     * 层次遍历
     * 102. 二叉树的层序遍历
     */
    fun levelOrder(root: TreeNode?): List<List<Int>> {


        var mResultList: MutableList<List<Int>> = mutableListOf()

        var mQueue: Queue<TreeNode> = LinkedList()

        if (root != null) {
            mQueue.offer(root)
        }

        while (mQueue.isNotEmpty()) {

            val levelList = mutableListOf<Int>()

            for (i in 0 until mQueue.size) {

                var node = mQueue.poll()
                levelList.add(node.value)
                if (node.left != null) mQueue.offer(node.left)
                if (node.right != null) mQueue.offer(node.right)
            }

            mResultList.add(levelList)
        }

        return mResultList
    }


    /**
     * 103. 二叉树的锯齿形层序遍历
     */
    fun zigzagLevelOrder(root: TreeNode?): List<List<Int>> {


        var mResultList: MutableList<List<Int>> = mutableListOf()

        var mQueue: Queue<TreeNode> = LinkedList()

        if (root != null) {
            mQueue.offer(root)
        }

        var mLeftToRight = true

        while (mQueue.isNotEmpty()) {

            val levelList = mutableListOf<Int>()
            for (i in 0 until mQueue.size) {

                var node = mQueue.poll()

                if (mLeftToRight) {
                    levelList.add(node.value)
                } else {
                    levelList.add(0, node.value)
                }

                if (node.left != null) mQueue.offer(node.left)
                if (node.right != null) mQueue.offer(node.right)
            }

            mResultList.add(levelList)
            mLeftToRight = !mLeftToRight


        }

        return mResultList
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {


//            var mRoot = TreeNode(0)
            var mNode1 = TreeNode(1)

            var mNode2 = TreeNode(2)
            var mNode3 = TreeNode(3)

            var mNode4 = TreeNode(4)
            var mNode5 = TreeNode(5)
            var mNode6 = TreeNode(6)
            var mNode7 = TreeNode(7)

            var mNode8 = TreeNode(8)
            var mNode9 = TreeNode(9)

            mNode1.left = mNode2
            mNode1.right = mNode3

            mNode2.left = mNode4
            mNode2.right = mNode5

            mNode3.left = mNode6
            mNode3.right = mNode7

            mNode4.left = mNode8
            mNode4.right = mNode9


            val traversal = TreeTraversal()


//            println(" 先序遍历 ${traversal.preorderTraversal(mNode1).toString()}")
//            println(" 先序遍历 ${traversal.preorderTraversal1(mNode1)}")
//
//            println(" 中序遍历 ${traversal.inorderTraversal(mNode1)}")
//            println(" 中序遍历 ${traversal.inorderTraversal2(mNode1)}")
//
//            println(" 后序遍历 ${traversal.postorderTraversal(mNode1)}") //[8, 9, 4, 5, 2, 6, 7, 3, 1]
//            println(" 后序遍历 ${traversal.postorderTraversal1(mNode1)}")
            println(" 后序遍历 ${traversal.postorderTraversal2(mNode1)}")
//            println(" 层次遍历 ${traversal.levelOrder(mNode1)}")
//            println(" 锯齿遍历 ${traversal.zigzagLevelOrder(mNode1)}")

        }
    }
}


class TreeNode(var value: Int) {


    var left: TreeNode? = null
    var right: TreeNode? = null

    //为了后续遍历的非递归 访问 添加的字段，true 表示它的直接后代都已经访问过了
    var visitOffspring: Boolean = false

}