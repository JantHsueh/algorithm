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

        var pre: ListNode? = null
        var cur = head
        var next: ListNode? = cur?.next

        while (cur != null) {
            cur.next = pre
            pre = cur
            cur = next
            next = cur?.next
        }

        return pre
    }


    fun mergeLink1(a: ListNode?, b: ListNode?): ListNode? {

        if (a == null) {
            return b
        }

        if (b == null) {
            return a
        }

        var head: ListNode? = null
        var tail: ListNode? = null
        var small = a
        var big = b


        if ((a?.value ?: 0) < (b?.value ?: 0)) {
            small = a
            big = b
        } else {
            small = b
            big = a
        }
        head = small
        var preSmall = small
        while (small != null && big != null) {

            while (small != null && (small?.value ?: 0) < (big?.value ?: 0)) {
                preSmall = small
                small = small?.next
            }

            preSmall?.next = big


            val temp = big
            big = small
            small = temp
        }

//        if (big != null){
//            tail?.next = aTemp
//        }


        return head
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


    val input = intArrayOf(1, 5, 4, 7, 8, 9, 11, 2)

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