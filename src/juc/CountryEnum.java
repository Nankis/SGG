package juc;
import java.util.concurrent.CountDownLatch;
public enum CountryEnum {
    ONE(1, "齐"),
    TWO(2, "楚"),
    THREE(3, "燕"),
    FOUR(4, "赵"),
    FIVE(5, "魏"),
    SIX(6, "韩");

    private Integer retCode;
    private String retMessage;

    CountryEnum(Integer retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public Integer getRetCode() {
        return retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public static CountryEnum forEach_CountryEnum(int index) {
        CountryEnum[] myArray = CountryEnum.values();
        for (CountryEnum element : myArray) {
            if (index == element.getRetCode()) {
                return element;
            }
        }
        return null;
    }

    //测试
    public static void main(String[] args) throws InterruptedException {
        int count = 6;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 1; i <=6;i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t国,被灭");
                countDownLatch.countDown();
            },CountryEnum.forEach_CountryEnum(i).getRetMessage()).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t *********秦帝国,一统华夏!");

    }

}
