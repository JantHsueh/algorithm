package link

import ListNode

/**
 * create by xuexuan
 * time 2025/4/22 14:54
 *  输入：单数项 是升序， 偶数项是降序
 *
 *  输出：整理成一个升序的链表
 */
class OrderLink {

    fun orderLink(head: ListNode?) {

        val oddLink: ListNode? = head
        var evenLink: ListNode? = head?.next


        splitLink(head, oddLink, evenLink)
        evenLink = reverseLink(evenLink)
        mergeLink(oddLink, evenLink)
    }

    fun splitLink(head: ListNode?, oddLink: ListNode?, evenLink: ListNode?) {

        var i = 1;
        var cur = head
        var next: ListNode?

        var oddLink = oddLink
        var evenLink = evenLink

        while (cur != null) {
            next = cur.next

            if (i % 2 == 0) {
                // 偶数项
                evenLink?.next = next?.next
                evenLink = next?.next

            } else {
                // 奇数项
                oddLink?.next = next?.next
                oddLink = next?.next
            }
            i++
            cur = next
        }
    }


    fun reverseLink(head: ListNode?): ListNode? {
        if (head == null) return null

        var cur = head
        var pre: ListNode? = null
        var next: ListNode?

        while (cur != null) {
            next = cur.next
            cur.next = pre
            pre = cur
            cur = next
        }

        return pre
    }


    fun mergeLink(a: ListNode?, b: ListNode?) {


        var processNode: ListNode?
        var immobileNode: ListNode?


        if ((a?.value ?: 0) <= (b?.value ?: 0)) {
            processNode = a
            immobileNode = b
        } else {
            processNode = b
            immobileNode = a
        }


        var preProcessNode: ListNode? = processNode

        while (true) {


            while (processNode != null && (processNode?.value ?: 0) <= (immobileNode?.value ?: 0)) {
                preProcessNode = processNode
                processNode = processNode?.next

            }

            preProcessNode?.next = immobileNode

            if (processNode == null) {
                break
            }
            val tempNode = processNode
            processNode = immobileNode
            immobileNode = tempNode

        }
    }
}


fun main() {


    val input = intArrayOf(1, 8, 3, 6, 5, 4, 7, 2)

    val headNode: ListNode? = ListNode()
    headNode?.value = input[0]
    var curNode = headNode

    for (i in 1..<input.size) {

        val nextNode = ListNode()
        nextNode.value = input[i]
        curNode?.next = nextNode
        curNode = nextNode
    }

    val orderLink = OrderLink()
    orderLink.orderLink(headNode)

    //打印 链表
    var cur = headNode
    while (cur != null) {
        print("${cur.value} -> ")
        cur = cur?.next
    }

}