# RabbitMQ 安装和部署

## RabbitMQ介绍

- MQ全称为Message Queue, 消息队列（MQ）是一种应用程序对应用程序的通信方法。应用程序通过读写出入队列的消息（针对应用程序的数据）来通信，而无需专用连接来链接它们。
    * RabbitMQ是一个在AMQP基础上完成的，可复用的企业消息系统。他遵循Mozilla Public License开源协议。
- RabbitMQ WIKI：<https://zh.wikipedia.org/zh/RabbitMQ>
- RabbitMQ 百科：<https://baike.baidu.com/item/rabbitmq>
- RabbitMQ 官网：<http://www.rabbitmq.com/>
- RabbitMQ 官方下载：<http://www.rabbitmq.com/download.html>
- RabbitMQ 官方安装(基于Linux)：<http://www.rabbitmq.com/install-rpm.html>
- RabbitMQ 文档：
    * <http://www.rabbitmq.com/getstarted.html>
    * <http://www.rabbitmq.com/documentation.html>
    * 简书<https://www.jianshu.com/p/79ca08116d57>
    * 知乎<https://zhuanlan.zhihu.com/p/25069044>
    
## Docker 安装 RabbitMQ

- 官方镜像：<https://hub.docker.com/_/rabbitmq/>
- 官方镜像说明(Dockerfile)：<https://docs.docker.com/samples/library/rabbitmq/>
- 执行命令：
        
        docker run -d --name rabbitmq -p 5671:5671 -p 5672:5672 -p 4369:4369 -p 25672:25672 -p 15671:15671 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:3.7-rc-management
 
- 参数说明：
    * run：创建并运行一个容器
    * -d：容器以后台模式运行
    * --name：命名一个容器
    * -p：对外开发的端口
    * rabbitmq:3.7-rc-management：只有带 management 后缀的才有 web 端管理入口
    * 15672：表示 RabbitMQ 控制台端口号，可以在浏览器中通过控制台来执行 RabbitMQ 的相关操作。容器启动成功后，可以在浏览器输入地址：<http://ip:15672/> 访问控制台
    * 5672: 表示 RabbitMQ 所监听的 TCP 端口号，应用程序可通过该端口与 RabbitMQ 建立 TCP 连接，完成后续的异步消息通信
    * RABBITMQ_DEFAULT_USER：用于设置登陆控制台的用户名，这里我设置 admin
    * RABBITMQ_DEFAULT_PASS：用于设置登陆控制台的密码，这里我设置 admin