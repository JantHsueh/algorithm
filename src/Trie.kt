/**
 * create by xuexuan
 * time 2020/5/8 10:17
 *
 * 208. 实现 Trie (前缀树)
 *
 * https://leetcode-cn.com/problems/implement-trie-prefix-tree/
 * 实现一个 Trie (前缀树)，包含 insert, search, 和 startsWith 这三个操作。
 */
class Trie {
    /** Initialize your data structure here. */


    var mRoot = TrieNode()

    /** Inserts a word into the trie. */
    fun insert(word: String) {

        var node = mRoot

        for (c in word) {

            if (!node.containsKey(c)) {
                //如果存在，则往下继续查找
                node.insert(c, TrieNode())
            }
            node = node.get(c)
        }

        node.mEnd = true

    }

    /** Returns if the word is in the trie. */
    fun search(word: String): Boolean {

        var node = mRoot

        for (c in word) {

            if (node.containsKey(c)) {
                //如果存在，则往下继续查找
                node = node.get(c)
            } else {
                return false
            }
        }

        return node.mEnd
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    fun startsWith(prefix: String): Boolean {
        var node = mRoot

        for (c in prefix) {

            if (node.containsKey(c)) {
                //如果存在，则往下继续查找
                node = node.get(c)
            } else {
                return false
            }
        }

        return true
    }

}


fun main() {


    val t = Trie()


    println(t.insert("apple"))
    println(t.search("apple"))   // 返回 true
    println(t.search("app"))     // 返回 false
    println(t.startsWith("app")) // 返回 true
    println(t.insert("app"))
    println(t.search("app"))     // 返回 true


}

class TrieNode {


    //每个节点都可能储存26个字母中的任意一个
    private val R = 26
    private var mLink = arrayOfNulls<TrieNode>(R)


    //true 表示存在一个字符 从根节点到该节点
    var mEnd = false

    fun insert(c: Char, childNode: TrieNode) {


        mLink[c - 'a'] = childNode

    }


    fun containsKey(c: Char): Boolean {

        return mLink[c - 'a'] != null
    }


    fun get(c: Char): TrieNode {

        return mLink[c - 'a']!!
    }

    fun remove(c: Char) {
        mLink[c - 'a'] = null
    }


}