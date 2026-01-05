package link

import ListNode

/**
 * create by xuexuan
 * time 2025/4/22 10:27
 *
 *
 * 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
 *
 * k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 *
 * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 */


class ReverseKGroup {


    fun reverseKGroup(listHead: ListNode?, k: Int): ListNode? {

        var head = listHead
        var nextKHead = head
        // 转换后的上一个的tail
        var preTailReversed : ListNode? = null
        var headRevered = head
        var resultNode: ListNode? = null

        while (nextKHead != null) {
            nextKHead = moveTail(head, k)

//            headRevered = reverse(head, nextKHead,k)
            headRevered = reverse1(head, nextKHead)

            if (resultNode == null) {
                resultNode = headRevered
            }
            preTailReversed?.next = headRevered
            preTailReversed = head
            head = nextKHead
        }

        return resultNode

    }


    fun moveTail(head: ListNode?, k: Int): ListNode? {
        var tail = head
        for (i in 0 until k) {

            tail = tail?.next
        }
        return tail
    }


    fun reverse(head: ListNode?, tail: ListNode?, k: Int): ListNode? {
        var cur = head
        var k = k

        while (k >= 0 && cur != null) {
            cur = cur.next
            k--
        }

        if (k > 0) {
            return head
        }

         cur = head
        var pre = tail

        while (true) {
            val next = cur?.next

            if (cur == tail) {
                break
            }
            cur?.next = pre
            pre = cur
            cur = next
        }

        return pre

    }


    fun reverseKGroup1(listHead: ListNode?, k: Int): ListNode? {

        var segHead = listHead
        var preSegTail : ListNode? = null
        var resultNode: ListNode? = null

        while (segHead != null) {
            val segTail = splitList(segHead, k)

            if (segTail == null){
                preSegTail?.next = segHead
                break
            }


            val nextSegHead = segTail?.next
            reverse1(segHead, segTail)

            // 此时已经转换完成，segTail 是新的队头， segHead 是队尾
            if (resultNode == null){
                resultNode = segTail
            }

            preSegTail?.next = segTail
            preSegTail = segHead

            segHead = nextSegHead
        }

        return resultNode

    }

    fun splitList(head: ListNode?, k: Int): ListNode? {
        var tail = head
        for (i in 0 until k-1) {

            tail = tail?.next
        }
        return tail
    }

    fun reverse1(head: ListNode?, tail: ListNode?): ListNode?{

        var pre:ListNode? = null
        var cur = head
        var next = head?.next

        while (true){
            cur?.next = pre

            if (cur == tail){
               break
            }
            pre = cur
            cur = next
            next = cur?.next

        }
        return head

    }



}


fun main() {


    val input = intArrayOf(1, 2, 3, 4, 5,6,7,8,9,10)

    val headNode = ListNode()
    headNode.value = input[0]
    var curNode = headNode

    for (i in 1..<input.size) {

        val nextNode = ListNode()
        nextNode.value = input[i]
        curNode.next = nextNode
        curNode = nextNode
    }

    val reverseKGroup = ReverseKGroup()
    val node =  reverseKGroup.reverseKGroup1(headNode, 3)

    var currNode = node
    while (currNode != null) {
        println(currNode.value)
        currNode = currNode.next
    }


}
