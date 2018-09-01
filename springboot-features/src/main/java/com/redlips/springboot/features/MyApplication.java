package com.redlips.springboot.features;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author 花落孤忆
 * @create 2018-08-31 9:55
 * @description
 */
@SpringBootApplication
public class MyApplication implements CommandLineRunner {
//    @Value("${local.server.port}")
    private String localServerPort;
    public static void main(String[] args) {
        /** 1.简单的开启方式 */
        SpringApplication app = new SpringApplication(MyApplication.class);
//        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

//        SpringApplication.run(MyApplication.class, args);
//        SpringApplication app = new SpringApplication(MyApplication.class);
//        app.setBannerMode(Banner.Mode.OFF);
//        app.run(args);

        /** 2.SpringApplicationBuilder可以创建一个ApplicationContext的层级关系（多个上下文的子父关系） */
        /** {@link SpringApplicationBuilder} */
//        new SpringApplicationBuilder().sources(MyApplication.class).run(args);
//        new SpringApplicationBuilder()
//                .sources(Parent.class)
//                .child(MyApplication.class)
//                .bannerMode(Banner.Mode.OFF)
//                .run(args);

        /** 添加ApplicationContext创建之前的监听器，或在META-INF目录中的spring.factories文件中通过键值指定 */
//        app.addListeners();

        /** Application事件的发送顺序 */
        /** 1.An ApplicationStartingEvent is sent at the start of a run, but before any processing except the registration of listeners and initializers. */
        /** 2.An ApplicationEnvironmentPreparedEvent is sent when the Environment to be used in the context is known, but before the context is created. */
        /** 3.An ApplicationPreparedEvent is sent just before the refresh is started, but after bean definitions have been loaded. */
        /** 4.An ApplicationReadyEvent is sent after the refresh and any related callbacks have been processed to indicate the application is ready to service requests. */
        /** 5.An ApplicationFailedEvent is sent if there is an exception on startup. */

        /** 设置Web环境，是否覆盖默认， 在JUnit环境中，经常会调用setWebEnvironment(false) */
//        app.setWebEnvironment();
        /** 该方法可以完全控制 ApplicationContext的类型 */
//        app.setApplicationContextClass();


    }

    /**
     * CommandLineRunner接口，提供了方法对应用程序参数的访问， 而ApplicationRunner使用上面讨论的ApplicationArguments接口
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("在ApplicationContext 完成之前created" + System.getProperties());
        System.getProperties();
    }
}
