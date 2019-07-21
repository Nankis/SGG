package jvm;

public class HelloGC {
    public static void main(String[] args) {
        long totalMemory =Runtime.getRuntime().totalMemory();//返园 Java 虚我机中的内存总量。
        long maxMemory =Runtime.getRuntime().maxMemory();//返回 Java 虚拟机就图使用的最大内存量。
        System.out.println("TOTAL_MEMORY(-Xms)="+totalMemory+"(字节)");
        System.out.println("MAX_MEMORY(-Xmx)="+maxMemory+"(字节)"+(maxMemory/(double)1024/1024)+"MB");
    }
}
