/**
 * create by xuexuan
 * time 2020/5/9 16:36
 *
 *  使用哈希表 + 双向链表
 *
 *  哈希表和链表的节点，所以有前向和后向
 *
 */
class DLinkNode(
    val key: Int? = null,
    var value: Int? = null
) {


    var prev: DLinkNode? = null
    var next: DLinkNode? = null

}

/**
 * 双向链表 需要固定两个节点，首位，这样可以O(1)的时间，插入 到头部 ，删除尾部
 */
class LRUCache(var mCapacity: Int) {


    var mHead = DLinkNode()
    var mTail = DLinkNode()

    var mMap = hashMapOf<Int, DLinkNode>()


    init {
        mHead.next = mTail
        mTail.prev = mHead
    }

    /**
     * 插入一个节点，因为是双向链表，所以需要修改4条指向
     */
    fun addNode(dLinkNode: DLinkNode) {


        dLinkNode.next = mHead.next
        dLinkNode.prev = mHead

        mHead.next?.prev = dLinkNode
        mHead.next = dLinkNode

        mMap[dLinkNode.key!!] = dLinkNode

    }


    /**
     * 删除一个节点，只需要改变被删除节点前后的两个节点的指向
     */
    fun removeNode(dLinkNode: DLinkNode) {

        dLinkNode.prev?.next = dLinkNode.next
        dLinkNode.next?.prev = dLinkNode.prev
        mMap.remove(dLinkNode.key)
    }


    fun removeTail() {

        val node = mTail.prev!!
        if (node != mHead) {
            removeNode(node)
        }

    }


    fun moveToHead(dLinkNode: DLinkNode) {

        removeNode(dLinkNode)
        addNode(dLinkNode)

    }


    fun put(key: Int, value: Int) {


        if (mMap.containsKey(key)) {

            val node = mMap[key]!!
            node.value = value
            //访问过得元素，就将其移动到头部
            moveToHead(node)

        } else {

            val node = DLinkNode(key, value)
            mMap[key] = node

            addNode(node)

            if (mMap.size > mCapacity) {
                removeTail()
            }
        }
    }

    fun get(key: Int): Int? {

        if (mMap.containsKey(key)) {
            val node = mMap[key]!!
            //访问过得元素，就将其移动到头部
            moveToHead(node)
            return node.value
        }

        return -1


    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val cache = LRUCache(2)

            println(cache.put(1, 1))
            println(cache.put(2, 2));
            println(cache.get(1));       // 返回  1
            println(cache.put(3, 3));    // 该操作会使得密钥 2 作废
            println(cache.get(2));       // 返回 -1 (未找到)
            println(cache.put(4, 4));    // 该操作会使得密钥 1 作废
            println(cache.get(1));       // 返回 -1 (未找到)
            println(cache.get(3));       // 返回  3
            println(cache.get(4));       // 返回  4

        }
    }
}


class LRUCache1 : LinkedHashMap<Int, Int> {

    var mCapacity: Int = 0

    constructor(capacity: Int) : super(capacity, 0.75F, true) {
        mCapacity = capacity
    }


    override fun get(key: Int): Int {
        return super.getOrDefault(key, -1)
    }

    override fun put(key: Int, value: Int): Int? {
        return super.put(key, value)
    }

    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<Int, Int>?): Boolean {
        return size > mCapacity

    }

}