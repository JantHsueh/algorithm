/**
 * create by xuexuan
 * time 2020/4/11 16:16
 *
 * 5. 最长回文子串
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 */

class LongestPalindromicSubstring {


    /**
     * 暴力解法
     *
     * 从长度 0- n，遍历所有可能的字串，判断是否是回文
     */
    fun longestPalindrome1(s: String): String {

        if (s.isEmpty()) return ""

        var substring = s.substring(0, 1)
        for (i in 0 until s.length) {
            for (j in i + 1 until s.length) {

                var tempI = i
                var tempJ = j
                while (tempI < tempJ) {

                    if (s[tempI] == s[tempJ]) {
                        tempI++
                        tempJ--
                    } else {
                        break
                    }

                }

                if (tempJ <= tempI) {
                    val tempString = s.substring(i, j + 1)
                    if (tempString.length > substring.length) substring = tempString
                }
            }
        }

        return substring
    }


    /**
     * 中心拓展
     *
     * 以每一个字符为中心位置，向两边扩散，遇到不匹配的就
     *
     */
    fun longestPalindrome2(s: String): String {

        if (s.isEmpty()) return ""

        var substring = s.substring(0, 1)
        for (i in 0 until s.length) {

            if (i == 0 || i == s.length - 1) continue
            var l = i - 1
            var r = i + 1
            while (true) {
                if (l >= 0 && r <= s.length - 1) {

                    if (s[l] == s[r]) {
                        val t = s.substring(l, r + 1)
                        if (t.length > substring.length) substring = t
                        l--
                        r++
                    } else {
                        break
                    }

                } else {
                    break
                }

            }

        }

        return substring
    }


    /**
     * 动态规划
     */
    fun longestPalindrome3(s: String): String {


        return s

    }


    /**
     * 马拉车
     */
    fun longestPalindrome4(s: String): String {


        return s

    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cl = LongestPalindromicSubstring()

//            val s = "ababcd"
//            val s = "babad"
            val s = "cbbd"
            println("$s  最长回文子串 ${cl.longestPalindrome2(s)}")

        }
    }
}