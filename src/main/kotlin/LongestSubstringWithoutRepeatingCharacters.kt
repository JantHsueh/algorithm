package src.main.kotlin

import kotlin.math.max

/**
 * 给定一个字符串 s ，请你找出其中不含有重复字符的最长子串的长度。
 *
 * 示例 1:
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 *
 * 示例 2:
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 *
 * 示例 3:
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是子串的长度，"pwke" 是一个子序列，不是子串。
 */
class LongestSubstringWithoutRepeatingCharacters {
    fun lengthOfLongestSubstring(s: String): Int {
        // 哈希表记录字符上一次出现的位置
        val map = HashMap<Char, Int>()
        var maxLen = 0
        var left = 0

        for (right in s.indices) {
            val char = s[right]
            if (map.containsKey(char)) {
                // 如果字符已经在窗口中存在，更新左边界
                // 注意：left 只能向右移动，不能回退，所以取 max
                left = max(left, map[char]!! + 1)
            }
            // 更新字符的最新位置
            map[char] = right
            // 更新最大长度
            maxLen = max(maxLen, right - left + 1)
        }
        return maxLen
    }


    fun findSubString(s: String): MutableList<Char> {

        if (s.isEmpty()){
            return mutableListOf()
        }

        val list = mutableListOf<MutableList<Char>>()

        var map = mutableListOf<Char>()
        for (char in s) {
            if (!map.contains(char)) {
                map.add(char)
            } else {
                list.add(map)
                map = mutableListOf<Char>()
                map.add(char)
            }
        }
        var result = mutableListOf<Char>()
        var maxLength = 0;
        for (l in list) {

            if (l.size > maxLength) {
                maxLength = l.size
                result = l
            }


        }

        return result


    }

}

fun main() {
    val solution = LongestSubstringWithoutRepeatingCharacters()
    
    // 示例 1
    val s1 = "abcabcbb"
    println("输入: s = \"$s1\"")
    println("输出: ${solution.lengthOfLongestSubstring(s1)}") // 预期: 3
    
    // 示例 2
    val s2 = "bbbbb"
    println("输入: s = \"$s2\"")
    println("输出: ${solution.lengthOfLongestSubstring(s2)}") // 预期: 1
    
    // 示例 3
    val s3 = "pwwkew"
    println("输入: s = \"$s3\"")
    println("输出: ${solution.lengthOfLongestSubstring(s3)}") // 预期: 3
}
