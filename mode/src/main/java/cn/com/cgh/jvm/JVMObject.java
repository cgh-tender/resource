package cn.com.cgh.jvm;

/**
 * -Xms30m -Xmx30m -XX:MaxMetaspaceSize=30m -XX:+UseConcMarkSweepGc +XX:-UseCompressedOops
 * sawindbg.dll
 * java -cp ./sa-jdi.jar sun.jvm.hotspot.HSDB
 */
public class JVMObject {
    public static void main(String[] args) throws InterruptedException {
        Teacher teacher = new Teacher();
        teacher.setA("qwer");
        teacher.setAge(35);
        teacher.setName("Mark");

//        for (int i = 0; i < 16; i++) {
//            System.gc();
//        }

        Teacher teacher1 = new Teacher();
        teacher1.setA("rewq");
        teacher1.setAge(18);
        teacher1.setName("King");
        Thread.sleep(Integer.MAX_VALUE);
    }
}
class Teacher{
    private String name;
    private int age;
    private String a;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }
}