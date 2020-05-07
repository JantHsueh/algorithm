import java.util.*

/**
 * create by xuexuan
 * time 2020/4/23 9:35
 *
 * 字符串匹配
 *
 *
 */

class StringMatch {


    /**
     * 蛮力解法
     *
     * 算法复杂度 O(n * m)
     *
     * 返回值表示在source 的匹配的起始位置
     */
    fun match1(source: String, match: String): Int {

        for (sIndex in 0..source.length - match.length) {

            for (mIndex in 0 until match.length) {
                if (match[mIndex] == source[mIndex + sIndex]) {
                    if (mIndex == match.length - 1) {
                        println("匹配成功")
                        return sIndex - match.length
                    }
                } else {

                    break
                }
            }
        }
        return source.length
    }


    /**
     * KMP 算法
     *
     */
    fun match2(source: String, match: String): Int {

        var sIndex = 0
        var mIndex = 0

        val nextTable = buildNextTable2(match)
        while (sIndex < source.length) {

            while (mIndex < match.length) {

                if (mIndex < 0 || match[mIndex] == source[sIndex]) {

                    sIndex++
                    mIndex++
                    if (mIndex == match.length - 1) {
                        return sIndex - match.length
                    }
                } else {
                    mIndex = nextTable[mIndex] ?: 0
                }
            }
        }

        return 1
    }


    /**
     * 下面是我在了解了KMP 表原理后，使用蛮力方法实现的，
     *
     *  计算每一位的表值，以当前位后对齐，前缀最长的自匹配长度
     *
     *
     * 但是这三位大神提供了，更高级算法，见下面
     *
     */
    private fun buildNextTable1(match: String): Array<Int?> {

        val nextTable = arrayOfNulls<Int>(match.length)
        nextTable[0] = -1


        //遍历整个数组，含义是找到在x位置上不匹配，前缀有多少是与前缀的前缀 后对齐匹配的
        for (x in 1 until match.length) {

            var xValue = 0
            //下面的循环遍历[0，x]的前缀数据
            out@ for (y in 1 until x) {


                //下面的循环遍历[0，x]的前缀,也就是[0,z],因为是右对齐，找x的表值，所以z 的最大值是x - y
                // 也就是 偏移值y + 长度z = x
                for (z in 0..x - y) {

                    if (y + z >= x) {
                        break@out
                    }

                    if (match[z] == match[y + z]) {
                        xValue++
                    } else {
                        xValue = 0
                        break
                    }
                }
            }
            nextTable[x] = xValue
        }

        return nextTable
    }


    /**
     * 使用KMP 的算法
     *
     * 这个算法，没有吸取教训，只汲取了经验。
     *
     * 它会快速向右移动，但是如果遇到一个无法匹配的值，即使待匹配左边的值都是一样的。还是需要逐个去匹配，不断的失败
     *
     * 使用这个表的，算法的总体复杂度O(m+n)
     *
     * -1 0 0 1 2 3 1 0
     * m  a m a m m i a
     *
     */
    fun buildNextTable2(match: String): Array<Int?> {

        val nextTable = arrayOfNulls<Int>(match.length)
        //假想值，万能匹配
        nextTable[0] = -1

        //主串的当前位置，已经计算出表值的最后一位
        var i = 0
        //匹配串，不断移动它，来与主串进行匹配
        var j = -1
        while (i < match.length - 1) {

            if (j < 0 || match[i] == match[j]) {
                //j 小于0，说明遇到万能匹配
                //与[0,i] 匹配的最长前缀是[0,j]，
                //所以i +1 的表值是 [0,j]的长度
                nextTable[++i] = ++j
                // i ,j 都需要 + 1，来同时向右移动一位，来匹配下一位

            } else {
                //如果没有匹配，那么待匹配字符串的 当前匹配位置j 需要往左移动（也可以看做，j 不动，字符串往右移动）
                j = nextTable[j]!!
            }
        }

        return nextTable
    }


    /**
     * 吸取上面的教训
     * 如果遇到失败，并且待匹配左边的值都一样，则一次跳过多个
     * 使用这个表的，算法的总体复杂度O(m/n)
     * -1 0 -1 0 -1 3 1 0
     * m  a  m a  m m i a
     */
    fun buildNextTable3(match: String): Array<Int?> {

        val nextTable = arrayOfNulls<Int>(match.length)
        //假想值，万能匹配
        nextTable[0] = -1

        //主串的当前位置，已经计算出表值的最后一位
        var i = 0
        //匹配串，不断移动它，来与主串进行匹配
        var j = -1
        while (i < match.length - 1) {

            if (j < 0 || match[i] == match[j]) {

                j++
                i++
                nextTable[i] = if (match[i] != match[j]) {
                    j
                } else {
                    nextTable[j]
                }

            } else {

                j = nextTable[j]!!
            }
        }

        return nextTable
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val cl = StringMatch()
            val time = System.currentTimeMillis()
            //获得的输入行是字符串，需要显示转换
//    println("输入源字符串：")
    val sourceString = readLine()
//    val sourceString = "Current Toady I learn synchronized and sign var match"
//
//    var addition = StringBuilder()
//    for (i in 0..1000000) {
//        addition.append("Current Toady I learn synchronized and sign var ")
//    }
//
//    addition.append(sourceString)
//
//    println("输入待匹配字符串：")
//    val matchString = "Current Toady I learn synchronized and sign var match"
//
//
//
//    println("源字符串：${addition}  匹配字符串：${matchString} ")
//    println("${cl.match1(addition.toString() ?: "", matchString ?: "")}")
            //

            //  -1,0,0,1,2,3,1,0
            var nextString = "mamammia"
            println(Arrays.toString(cl.buildNextTable3(nextString)))


            println("用时：${System.currentTimeMillis() - time}")

//    Scanner 可以直接获取对应类型，无需显示转换
//    val read = Scanner(System.`in`)
//    println("输入年龄：")
//    var age1 = read.nextInt()


        }
    }

}

