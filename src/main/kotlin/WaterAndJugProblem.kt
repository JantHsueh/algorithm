import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * create by xuexuan
 * time 2020/4/21 9:55
 *
 * 365. 水壶问题
 *
 *
 * 有两个容量分别为 x升 和 y升 的水壶以及无限多的水。请判断能否通过使用这两个水壶，从而可以得到恰好 z升 的水？
 * 如果可以，最后请用以上水壶中的一或两个来盛放取得的 z升 水。
 * 你允许：
 * 装满任意一个水壶
 * 清空任意一个水壶
 *从一个水壶向另外一个水壶倒水，直到装满或者倒空
 *
 *
 */


class WaterAndJugProblem {


    /**
     *
     * 题目中的三种操作，其实对应六种操作：
     * 1、倒空x ，y不变
     * 2、倒空y ，x不变
     * 3、装满x ，y不变
     * 4、装满y ，x不变
     * 5、x 的水倒入y
     * 6、y 的水倒入x
     *
     * 枚举所有的状态，每种状态都可以通过上述操作进行转换，就形成一张状态图，使用广度优先搜索 BFS 或者深度优先搜索 DFS，看是否能够达到目标状态。
     *
     * 拓展：x升 和 y升 的水壶，至少n次操作，让水最近就d升，求n   使用BFS 这里的n就是拓展的次数
     *
     *
     * 为简单起见，以 x = 1, y = 3, z = 2 为例，来看下如何构思：
     * 当 x = 1, y = 3 时，一共有八种状态
     * (0,0), (1,0), (0,3), (0,1), (1,1), (0,2), (1,2), (1,3)
     */
    fun canMeasureWater1(x: Int, y: Int, z: Int): Boolean {


        if (z < 0 || z > x + y) {
            return false
        }


        if (z == x + y) {
            return true
        }

        val q = LinkedList<State>()

        var visited = hashSetOf<State>()

        var s0 = State(0, 0)
        q.add(s0)
        visited.add(s0)
        while (!q.isEmpty()) {
            val s = q.poll()
            val currX = s.x
            val currY = s.y

            if (currX == z || currY == z || currX + currY == z) {
                return true
            }


            val stateList = arrayListOf<State>()
            //1、倒空x ，y不变
            if (currX > 0) {
                stateList.add(State(0, currY))
            }

            //2、倒空y ，x不变
            if (currY > 0) {
                stateList.add(State(currX, 0))
            }
            //3、装满x ，y不变，不满时，才需要装满
            //第一次提交，这里判断为 currY == 0，这会导致状态过多，超时，下同
            if (currX < x) {
                stateList.add(State(x, currY))
            }
            //4、装满y ，x不变  不满时，才需要装满
            if (currY < y) {
                stateList.add(State(currX, y))
            }
            //5、x 的水倒入y
            if (currX > 0 && currY < y) {
                stateList.add(State(max(currX + currY - y, 0), min(y, currX + currY)))
            }
            //6、y 的水倒入x
            if (currY > 0 && currX < x) {
                stateList.add(State(min(x, currX + currY), max(currY + currX - x, 0)))

            }

            for (temp in stateList) {
                if (!visited.contains(temp)) {
                    visited.add(temp)
                    q.add(temp)
                }
            }
        }
        return false
    }


    data class State(var x: Int, var y: Int) {


        override fun equals(other: Any?): Boolean {
//
//            if (this === other) {
//                return true
//            }
            val other1 = other as State
            return x == other1.x && y == other1.y
        }

        override fun hashCode(): Int {
            return Objects.hash(x, y)
        }

    }


    /**
     *
     * 下面这个方法是错误的，但是它是我思考到求公约数的一个中间思路
     *
     * 用数学法，把小数看做被除数，大数看做除数，把小杯倒入大杯中，如此反复倒空大杯的水，倒满小杯的水，就是求余操作，小杯剩下水的可以看做是余数，。
     *
     * 数学法也很容易想到，就是两个杯子容量差是关键信息，不断使用这个容量差，来组合不同的容量。
     *
     *  那为什么要大杯看做除数，因为余数<除数，除数大一点，余数的范围也大一点，这样所求值在大小杯直间，也可以正确求解
     *
     * 例如：
     *
     *  x = 3, y = 5, z = 4
     *
     *  3 % 5 = 3
     *  6 % 5 = 1
     *  9 % 5 = 4
     *  12 % 5 = 2
     *  15 % 5 = 0
     *
     * 结合小杯的容量，可以得出5,6,7,8
     *
     *  如果小杯是除数，只能如下，丢失很多信息：
     *  8 % 3 = 2
     *  10 % 3 = 1
     *  15 % 3 = 0
     *
     *
     */
    fun canMeasureWater2(x1: Int, y1: Int, z: Int): Boolean {

        var x = x1
        var y = y1

        if (x == 0) {
            return z == y
        }

        if (y == 0) {
            return z == x
        }

        if (z < 0 || z > x + y) {
            return false
        }

        if (z == x + y) {
            return true
        }

        if (x > y) {
            x = x xor y
            x = x xor y
            y = x xor y
        }

        //至此x < y 成立
        return if (y % x == 0) {
            z % x == 0
        } else {
            true
        }
        //此代码是错误的
    }


    /**
     *
     * 其实最大公约数 = 1 是保证了，被除数a 在计算过程中，余数范围是否在[1,a-1]
     *
     * 上面的想法错误在，如果两个数的最大公约数是a (a ≠ 1)，那么余数范围是在(a,2a ... n-1)
     *
     * 如果z 不能整除a，那就无法得到z升水
     *
     */
    fun canMeasureWater3(x1: Int, y1: Int, z: Int): Boolean {

        if (z < 0 || z > x1 + y1) {
            return false
        }

        if (z == x1 + y1) {
            return true
        }

        var x = x1
        var y = y1
        if (x > y) {
            x = x xor y
            x = x xor y
            y = x xor y
        }

        if (x == 0) {
            return z == y
        }

        while (y % x != 0) {
            var t: Int = y % x
            y = x
            x = t
        }

        return z % x == 0

    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cs = WaterAndJugProblem()
            val time = System.currentTimeMillis()
            val x = 6
            val y = 9
            val z = 1
            println("$x 升瓶子  $y 升瓶子  需要 $z 升水  水壶问题 ${cs.canMeasureWater3(x, y, z)}")
            println("用时：${System.currentTimeMillis() - time}")

        }
    }
}