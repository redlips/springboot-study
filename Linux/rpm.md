# RPM(rpm软件包管理器)

Red-Hat Package Manager (RPM)是一个功能强大的命令行驱动的包管理系统，能够安装、卸载、验证、查询和更新计算机包软件。每个软件包由文件存档及有关包的信息（如：版本、描述等）组成。
<br>

RPM是免费软件，在GUN GPL(GNU通用公共许可证)下发布。
<br>

RPM是许多Linux发行版的核心组件，例如：Red Hat Enterprise Linux、CentOS等等。

## 功能
RPM Package Manager（RPM）是一个功能强大的包管理系统

- 从源代码构建计算机软件到易于分发的软件包
- 安装、更新、卸载打包的软件
- 查询有关已打包软件的详细信息，无论是否已安装
- 验证打包软件的完整性以及由此产生的软件安装

## 工作方式
rpm是以一种数据库记录的方式来将所需要的套件安装在Linux主机的一套管理程序。也就是说Linux系统中存在一个关于rpm的数据库，它记录了安装的包与包之间的依赖相关性。rpm包是预先在Linux主机上编译好并打包的文件，安装起来非常快捷。

## 链接
[RPM官网](http://rpm.org/)

[RPM百科](https://baike.baidu.com/item/RPM/3794648)

## 安装教程(Mysql5.7为例)
查看系统是否已经安装了MYSQL服务

    rpm -qa | grep mysql 或 yum list installed | grep mysql

    [root@localhost lib]# rpm -qa | grep mysql
    mysql-community-common-5.7.24-1.el7.x86_64
    mysql-community-client-5.7.24-1.el7.x86_64
    mysql57-community-release-el7-8.noarch
    mysql-community-libs-5.7.24-1.el7.x86_64
    mysql-community-server-5.7.24-1.el7.x86_64

如果已经安装，删除MYSQL服务及其依赖的包(其它组件可以依次删除)
    
    yum -y remove mysql-community-libs-5.7.24-1.el7.x86_64
    
    [root@localhost lib]# yum -y remove mysql-community-libs-5.7.24-1.el7.x86_64
    已加载插件：fastestmirror
    正在解决依赖关系
    --> 正在检查事务
    ---> 软件包 mysql-community-libs.x86_64.0.5.7.24-1.el7 将被 删除
    --> 正在处理依赖关系 mysql-community-libs(x86-64) >= 5.7.9，它被软件包 mysql-community-client-5.7.24-1.el7.x86_64 需要
    --> 正在检查事务
    ---> 软件包 mysql-community-client.x86_64.0.5.7.24-1.el7 将被 删除
    --> 正在处理依赖关系 mysql-community-client(x86-64) >= 5.7.9，它被软件包 mysql-community-server-5.7.24-1.el7.x86_64 需要
    --> 正在检查事务
    ---> 软件包 mysql-community-server.x86_64.0.5.7.24-1.el7 将被 删除
    --> 解决依赖关系完成
    
    依赖关系解决
    
    ================================================================================================================================================================================================================================================================================
     Package                                                                  架构                                                     版本                                                              源                                                                    大小
    ================================================================================================================================================================================================================================================================================
    正在删除:
     mysql-community-libs                                                     x86_64                                                   5.7.24-1.el7                                                      @mysql57-community                                                   9.5 M
    为依赖而移除:
     mysql-community-client                                                   x86_64                                                   5.7.24-1.el7                                                      @mysql57-community                                                   107 M
     mysql-community-server                                                   x86_64                                                   5.7.24-1.el7                                                      @mysql57-community                                                   744 M
    
    事务概要
    ================================================================================================================================================================================================================================================================================
    移除  1 软件包 (+2 依赖软件包)
    
    安装大小：861 M
    Downloading packages:
    Running transaction check
    Running transaction test
    Transaction test succeeded
    Running transaction
      正在删除    : mysql-community-server-5.7.24-1.el7.x86_64                                                                                                                                                                                                                  1/3 
      正在删除    : mysql-community-client-5.7.24-1.el7.x86_64                                                                                                                                                                                                                  2/3 
      正在删除    : mysql-community-libs-5.7.24-1.el7.x86_64                                                                                                                                                                                                                    3/3 
      验证中      : mysql-community-server-5.7.24-1.el7.x86_64                                                                                                                                                                                                                  1/3 
      验证中      : mysql-community-libs-5.7.24-1.el7.x86_64                                                                                                                                                                                                                    2/3 
      验证中      : mysql-community-client-5.7.24-1.el7.x86_64                                                                                                                                                                                                                  3/3 
    
    删除:
      mysql-community-libs.x86_64 0:5.7.24-1.el7                                                                                                                                                                                                                                    
    
    作为依赖被删除:
      mysql-community-client.x86_64 0:5.7.24-1.el7                                                                                           mysql-community-server.x86_64 0:5.7.24-1.el7                                                                                          
    
    完毕！

下载YUM源，5.7为例（可在[MYSQL-YUM源包](https://dev.mysql.com/downloads/repo/yum/)）中下载
    
    wget http://repo.mysql.com/mysql57-community-release-el7-8.noarch.rpm

安装 mysql57-community-release-el7-8.noarch.rpm
    
    rpm -ivh mysql57-community-release-el7-8.noarch.rpm

    [root@localhost local]# rpm -ivh mysql57-community-release-el7-8.noarch.rpm
    准备中...                          ################################# [100%]
    正在升级/安装...
       1:mysql57-community-release-el7-8  ################################# [100%]

安装后会得到两个包(目录如下)mysql-community.repo、mysql-community-source.repo
    
    /etc/yum.repos.d

安装MYSQL服务
    
    yum -y install mysql-server

安装完毕后，启动MYSQL服务，之后会在`/var/log/mysqld.log`文件中生成一个随机密码
    
    service mysqld start
    
取出随机密码，登录MYSQL服务端

    grep 'password' /var/log/mysqld.log
        
    [root@localhost log]# grep "password" /var/log/mysqld.log 
    2018-11-09T09:10:30.549492Z 1 [Note] A temporary password is generated for root@localhost: WHYld+ek9V2F
    
如果获取不到随机密码，可以尝试如下操作（systemctl是LINUX是服务和进程管理工具）
    
    rm -rf /var/lib/mysql
    
    重启：
    service mysqld restart 或者 systemctl restart mysqld
    
登录到 MySQL 服务端并更新用户 root 的密码，打印出 MySQL 的版本即表明已登录

    [root@localhost log]# mysql -u root -p
    Enter password: 
    Welcome to the MySQL monitor.  Commands end with ; or \g.
    Your MySQL connection id is 2
    Server version: 5.7.24
    
    Copyright (c) 2000, 2018, Oracle and/or its affiliates. All rights reserved.
    
    Oracle is a registered trademark of Oracle Corporation and/or its
    affiliates. Other names may be trademarks of their respective
    owners.
    
    Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

不修改密码，会报一下错误
    
    mysql> use mysql
    ERROR 1820 (HY000): You must reset your password using ALTER USER statement before executing this statement.

MYSQL5.7以上的策略造成的一下错误
    
    mysql> ALTER USER USER() IDENTIFIED BY '12345678';
    ERROR 1819 (HY000): Your password does not satisfy the current policy requirements
    
修改MYSQL密码安全策略级别，这样，判断密码的标准就基于密码的长度了
    
    mysql> set global validate_password_policy=0;
    Query OK, 0 rows affected (0.00 sec)

此时再进行修改密码
    
    mysql> ALTER USER USER() IDENTIFIED BY '12345678';
    Query OK, 0 rows affected (0.00 sec)
    
    flush privileges;

可以查看关于密码的策略设置
    
    mysql> SHOW VARIABLES LIKE 'validate_password%';
    +--------------------------------------+--------+
    | Variable_name                        | Value  |
    +--------------------------------------+--------+
    | validate_password_check_user_name    | OFF    |
    | validate_password_dictionary_file    |        |
    | validate_password_length             | 8      |
    | validate_password_mixed_case_count   | 1      |
    | validate_password_number_count       | 1      |
    | validate_password_policy             | MEDIUM |
    | validate_password_special_char_count | 1      |
    +--------------------------------------+--------+
    7 rows in set (0.01 sec)

修改root权限 创建远程账号连接（第一个root表示用户名，%表示所有的电脑都可以连接，也可以设置某个ip地址运行连接，123345678表示密码）

    GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '12345678' WITH GRANT OPTION;
    flush privileges; 命令立即生效

通过外部工具(Navicat等)连接MYSQL，可能此时还无法连接，需要设置防火墙

* 查看防火墙状态(CentOS7默认的防火墙不是iptables，而是firewalld)
```
service firewalld status

[root@localhost sysconfig]# service firewalld status
Redirecting to /bin/systemctl status firewalld.service
● firewalld.service - firewalld - dynamic firewall daemon
   Loaded: loaded (/usr/lib/systemd/system/firewalld.service; enabled; vendor preset: enabled)
   Active: active (running) since 一 2018-11-12 16:52:00 CST; 51s ago
     Docs: man:firewalld(1)
 Main PID: 10545 (firewalld)
   CGroup: /system.slice/firewalld.service
           └─10545 /usr/bin/python -Es /usr/sbin/firewalld --nofork --nopid

11月 12 16:52:00 localhost.localdomain systemd[1]: Starting firewalld - dynamic firewall daemon...
11月 12 16:52:00 localhost.localdomain systemd[1]: Started firewalld - dynamic firewall daemon.
```

* 停止firewalld服务(即时生效，重启后失效)
```
systemctl stop firewalld | service firewalld stop
```

* 关闭防火墙，停止并禁用开机启动(永久关闭)，此时外部工具可连接MYSQL
```
systemctl disable firewalld
```

更多防火墙设置可查看链接

[Centos防火墙设置与端口开放的方法](https://blog.csdn.net/u013262689/article/details/80308870)

[Centos7 开放防火墙端口命令](https://www.cnblogs.com/happyflyingpig/archive/2017/11/08/7795495.html)