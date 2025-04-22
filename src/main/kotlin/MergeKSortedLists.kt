import java.util.*

/**
 * create by xuexuan
 * time 2020/5/9 10:49
 *
 *
 */
class MergeKSortedLists {


    /**
     * 使用分而治之 ，两两合并，最终合并到一个 链表
     *
     *
     * 时间复杂度：
     *
     * 第一次合并 k/2 组链表，每组链表需要的时间复杂度 O(2n) = kn
     * 第二次合并 k/4 组链表，每组链表需要的时间复杂度 O(4n) = kn
     *
     * 两两合并，总共需要合并logk次
     * 总的时间复杂度O(kn * log k)
     *
     * 空间复杂度：应该是O(1)，只是在合并的时候，需要一个ListNode 对象，在函数结束的时候这个对象会被回收
     */
    fun mergeKLists(lists: Array<ListNode?>): ListNode? {

        return merge(lists, 0, lists.size - 1)
    }


    /**
     *
     */
    fun merge(lists: Array<ListNode?>, l: Int, r: Int): ListNode? {

        if (l == r) return lists[l]

        if (l > r) return null
        val mid = (l + r) shr 1

        return mergeTwoList(merge(lists, l, mid), merge(lists, mid + 1, r))

    }


    /**
     *
     */
    fun mergeTwoList(listA: ListNode?, listB: ListNode?): ListNode? {


        if (listA == null || listB == null) return listA ?: listB

        var list: ListNode? = null

        var currNodeA = listA
        var currNodeB = listB

        if ((currNodeA.value?:0) < (currNodeB.value?:0)) {

            list = ListNode(currNodeA.value)
            currNodeA = currNodeA.next
        } else {

            list = ListNode(currNodeB.value)
            currNodeB = currNodeB.next
        }
        var tail = list

        while (currNodeA != null && currNodeB != null) {

            if ((currNodeA.value?:0) < (currNodeB.value?:0)) {

                tail?.next = currNodeA
                currNodeA = currNodeA.next

            } else {
                tail?.next = currNodeB
                currNodeB = currNodeB.next

            }

            tail = tail?.next
        }

        tail?.next = currNodeA ?: currNodeB

        return list
    }


    /**
     *  参考二路归并的思路，因为有k 个链表，比较k 个链表的首元素，每次需要k 次 找出极值元素。总的时间复杂度是O(kn)
     *
     *  优化，k个元素保存在优先级队列，每次只需要log k 就能找出极值。总的时间复杂度是O(n logk)
     *
     *  空间复杂度 O(k),
     *
     */
    fun mergeKLists1(lists: Array<ListNode?>): ListNode? {

        val queue = PriorityQueue<ListNode?>() { o1, o2 -> (o1?.value?:0) - (o2?.value?:0) }
        var list:ListNode? = ListNode(0)
        var tail = list

        for (l in lists){
            queue.add(l)
        }

        while (queue.isNotEmpty()){

            val min = queue.poll()
            tail?.next = min
            val next = min?.next
            if (next!= null){
                queue.add(next)
            }
            tail = tail?.next
        }

        list = list?.next

        return list

    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = MergeKSortedLists()


            val linked1_null = ListNode()
            val linked1_1 = ListNode(1)
            val linked1_2 = ListNode(4)
            val linked1_3 = ListNode(5)
            linked1_1.next = linked1_2
            linked1_2.next = linked1_3

            val linked2_1 = ListNode(1)
            val linked2_2 = ListNode(3)
            val linked2_3 = ListNode(4)
            linked2_1.next = linked2_2
            linked2_2.next = linked2_3

            val linked3_1 = ListNode(2)
            val linked3_2 = ListNode(6)
            linked3_1.next = linked3_2

//            cs.mergeTwoList(linked1_1, linked3_1)
            val lists: Array<ListNode?> = Array(1) { it ->
                when (it) {
                    0 -> linked1_null
//                    1 -> linked2_1
//                    2 -> linked3_1
                    else -> ListNode(1)
                }
            }
            print("${Arrays.toString(lists)}  合并后 ${cs.mergeKLists1(lists)}")

        }
    }
}


/**
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 */

class ListNode(var value: Int? = null) {
    var next: ListNode? = null
}