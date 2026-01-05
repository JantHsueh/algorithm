/**
 * create by xuexuan
 * time 2025/3/28 15:13
 */
fun main() {
    val a = 1
    val b = 2
    val c = 3

    val d = 3
    val e = 321
    val f = 321
    val g = 3L

    println(c == d)
    println(c.equals(d))

    println(e == f)
    println(e.equals(f))

    println(c == (a + b))
    println(c .equals(a + b))
    println(g.toInt() == (a + b))
    println(g .equals(a + b))

    val a1 = 3
    val b1: Long = 3

    println(a1.toLong() == b1)
}