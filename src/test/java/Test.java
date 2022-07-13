public class Test {
    @org.junit.Test
    public void testReg() {
        String a = ".*[A-Z]+.*";
        String b = "*jl2i*Y95pk$W^!71E";
        System.out.println(b.matches(a));
    }
}
