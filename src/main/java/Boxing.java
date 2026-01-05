/**
 * create by xuexuan
 * time 2025/3/28 15:17
 */
class Boxing {
    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;

        Long g = 3L;
        System.out.println(g.equals(a + b));
        Integer c = 3;

        Integer d = 3;
        Integer e = 321;
        Integer f = 321;


        System.out.println(c == d);
        System.out.println(c.equals(d));

        System.out.println(e == f);
        System.out.println(e.equals(f));

        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));
        System.out.println(g == (a + b));
        System.out.println(g.equals(a + b));

        Integer a1 = 3;
        long b1 = 3;

        System.out.println(a1 == b1);


        String s1 = "Hello";
        String s2 = "Hello";
        String s3 = "Hel" + "lo";
        String s4 = "Hel" + new String("lo");
        String s5 = new String("Hello");
        String s7 = "H";
        String s8 = "ello";
        String s9 = s7 + s8;

        System.out.println(s1 == s2);  // true
        System.out.println(s1 == s3);  // true
        System.out.println(s1 == s4);  // false
        System.out.println(s1 == s9);  // false
    }




}