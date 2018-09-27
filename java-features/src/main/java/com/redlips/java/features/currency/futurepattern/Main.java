package com.redlips.java.features.currency.futurepattern;

/**
 * @author 花落孤忆
 * @create 2018-09-21 14:49
 * @description 系统启动，调用Client发出请求
 */
public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        // 这里会立即返回，因为得到的是FutureData，而不是RealData
        Data data = client.request("name");
        System.out.println("请求完毕" + data.getResult());


        try {
            // 此处用sleep代表其他业务的处理
            // 在处理这些业务的过程中，RealData被创建，从而充分利用了等待时间
            System.out.println("开始等待2S，这个过程处理其它业务");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 使用真实的数据
        System.out.println("真实数据是 = " + data.getResult());
    }
}
