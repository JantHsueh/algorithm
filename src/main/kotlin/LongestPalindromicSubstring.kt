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

        var maxLen = 1
        var start = 0
        var len = 1
        for (i in 0 until s.length) {

            if (i == 0 || i == s.length - 1) continue
            var l = i - 1
            var r = i + 1

            //判断相邻字段是否是相同的，如果相同，就扩展进来
            while (l >= 0 && s[l] == s[i]) {
                l--
                len++
            }

            while (r <= s.length - 1 && s[r] == s[i]) {
                r++
                len++

            }

            //把以i 为中心，左右相同的字符全部，拓展进来后，再去判断 l，r 对应的字符是否相等
            while (l >= 0 && r <= s.length - 1 && s[l] == s[r]) {
                l--
                r++
                len += 2

            }

            if (len > maxLen) {
                maxLen = len
                start = l
            }
            len = 1
        }

        return s.substring(start + 1, start + maxLen + 1)
    }


    /**
     * 动态规划
     *
     * dp[l][r]  l 表示字符串的左边界，r 表示字符串的右边界
     * 状态：dp[l][r]  true 表示 s（l - r）是回文子串  false 表示s（l - r）不是回文子串
     * 转化：dp[l+1][r-1] = dp[l][r] && s[l+1] == s[r-1]  l -r 是 回文子串，那么s[l +1] == s[r-1] 那么回文子串的长度，可以左右拓展1，也就是s（l+1，r-1）
     *
     */
    fun longestPalindrome3(s: String): String {

        if (s.isEmpty() || s.length < 2) {
            return s
        }
        var maxLeft = 0
        var maxLength = 1
        //因为所有的字符本身就是回文子串，所以二位数组的两个索引值相同时，为true
        val dp = Array(s.length) {it-> BooleanArray(s.length) { it1 -> it == it1 } }

        for (r in 1 until s.length) {
            for (l in 0 until r) {

                //因为初始状态，dp的状态都是false ，所以需要考虑前一个dp是false的情况下，存在回文串的情况。
                // 例如：babd，中 r = 2，l=0，是一个回文串，这里看到dp[1][1] 已经在初始化时为true。
                //      cbbd，r =2 l =1，也是一个回文，相邻的两个字符相同，这种情况，通过r-l ==1 来进行附加判断
                if (s[l] == s[r] && (r -l == 1 || dp[l + 1][r - 1])) {
                    dp[l][r] = true

                    if (r - l +1 > maxLength) {

                        maxLeft = l
                        maxLength = r - l +1
                    }
                }
            }
        }
        //左闭右开，所以maxRight+1
        return s.substring(maxLeft, maxLeft + maxLength)

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
            println("$s  最长回文子串 ${cl.longestPalindrome3(s)}")

        }
    }
}