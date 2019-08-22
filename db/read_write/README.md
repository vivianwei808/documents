# 数据库相关技术

目前针对互联网主流的数据库mysql进行讨论。

- 高可用集群：主从复制
- 读写分离
- 分库分表
- 索引原理

## 高可用集群：主从复制
mysql中的主从复制是根据binlog二进制文件实现的。具体如图：

![主从同步](http://www.vivianwei808.top/upload/2019/8/%E4%B8%BB%E4%BB%8E%E5%90%8C%E6%AD%A5-d5f3f56296d745dc9200eed427811c52.png)

主mysql会将DML语句写入到二进制日志文件（binlog）中，主mysql会单独开启一个logdump线程把binlog传输给从mysql，此连接为长连接。

从mysql会开启一个IO线程来获取主mysql的binlog，从节点的sql线程依赖于IO线程，它是执行IO线程传输过来的binlog二进制文件。

## 主从复制原理
   MySQL的主从复制是MySQL本身自带的一个功能，不需要额外的第三方软件就可以实现，其复制功能并不是copy文件来实现的，而是借助binlog日志文件里面的SQL命令实现的主从复制，可以理解为我再Master端执行了一条SQL命令，那么在Salve端同样会执行一遍，从而达到主从复制的效果。
 从库生成两个线程，一个I/O线程，一个SQL线程；
 i/o线程去请求主库 的binlog，并将得到的binlog日志写到relay log（中继日志） 文件中；
主库会生成一个 log dump 线程，用来给从库 i/o线程传binlog；
 SQL 线程，会读取relay log文件中的日志，并解析成具体操作，来实现主从的操作一致，而最终数据一致；

![1](https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566217459267&di=72905e020632d263f5aa6859df4bdcfa&imgtype=0&src=http%3A%2F%2Fimg2018.cnblogs.com%2Fblog%2F940734%2F201811%2F940734-20181129101514133-1274442433.png)

### 主从配置
主mysql需要开启binlog日志，并设置服务id，从服务器除了需要开启binlog日志，并设置服务id外，主从服务id（service_id）不一致，还需要配置同步数据库。

主配置
> [mysqld]
log-bin=mysql-bin
server-id=1

从配置
> mysql> CHANGE MASTER TO
    ->     MASTER_HOST='master_host_name',
    ->     MASTER_USER='replication_user_name',
    ->     MASTER_PASSWORD='replication_password',
    ->     MASTER_LOG_FILE='recorded_log_file_name',
    ->     MASTER_LOG_POS=recorded_log_position;

## 读写分离
读写分离基于mysql的主从配置，读从库，写主库。

### 基于mycat配置读写分离

#### 什么是MyCat
  MyCAT是一款由阿里Cobar演变而来的用于支持数据库，读写分离、分表分库的分布式中间件。MyCAT支持Oracle、MSSQL、MYSQL、PG、DB2关系型数据库，同时也支持MongoDB等非关系型数据库。

 MyCAT原理MyCAT主要是通过对SQL的拦截，然后经过一定规则的分片解析、路由分析、读写分离分析、缓存分析等，然后将SQL发给后端真实的数据块，并将返回的结果做适当处理返回给客户端。

官方网站:http://www.mycat.io/

#### Linux环境安装MyCat实现读写分离
- 1、上传安装Mycat-server-1.6.5-release-20180122220033-linux.tar
- 2、解压安装包tar –zxvf  
- 3、配置schema.xml 和server.xml
- 4、客户端连接端口号: 8066


配置文件介绍:

|文件名|解释说明|
|-|-|
|server.xml|Mycat的配置文件，设置账号、参数等|
|schema.xml|Mycat对应的物理数据库和数据库表的配置|
|rule.xml|Mycat分片（分库分表）规则|

#### Linux环境安装MyCat实现读写分离
1、进入bin目录 
  启动MyCat ./mycat start 
  停止MyCat ./mycat stop
2、查看/usr/local/mycat/logs wrapper.log日志 如果是为successfully 则启动成功

关闭防火墙:systemctl stop firewalld.service
只可读的账号      user  user  端口号8066
可读可写的账号    root  123456  端口号8066

```mycat配置见项目main->resources里```


### 基于springboot2.0动态配置数据源实现读写分离 一主一从
```springboot配置以及项目见github->documents->db->mysql_read_write```

### 基于Sharding-Jdbc的读写分离

### Sharding-Jdbc介绍

Sharding-Jdbc在3.0后改名为Shardingsphere它由Sharding-JDBC、Sharding-Proxy和Sharding-Sidecar（计划中）这3款相互独立的产品组成。他们均提供标准化的数据分片、分布式事务和数据库治理功能，可适用于如Java同构、异构语言、容器、云原生等各种多样化的应用场景。

Sharding-Sphere定位为关系型数据库中间件，旨在充分合理地在分布式的场景下利用关系型数据库的计算和存储能力，而并非实现一个全新的关系型数据库。它通过关注不变，进而抓住事物本质。关系型数据库当今依然占有巨大市场，是各个公司核心业务的基石，未来也难于撼动，我们目前阶段更加关注在原有基础上的增量，而非颠覆。

应用场景:
数据库读写分离
数据库分表分库

相关资料:
Sharding-Jdbc官方网址: http://shardingsphere.io/index_zh.html
改名新闻: https://www.oschina.net/news/95889/sharding-jdbc-change-to-sphere


### Sharding-Jdbc与MyCat区别

MyCat是一个基于第三方应用中间件数据库代理框架，客户端所有的jdbc请求都必须要先交给MyCat，再有MyCat转发到具体的真实服务器中。

Sharding-Jdbc是一个Jar形式，在本地应用层重写Jdbc原生的方法，实现数据库分片形式。
MyCat属于服务器端数据库中间件，而Sharding-Jdbc是一个本地数据库中间件框架。

从设计理念上看确实有一定的相似性。主要流程都是SQL 解析 -> SQL 路由 -> SQL 改写 -> SQL 执行 -> 结果归并。但架构设计上是不同的。Mycat 是基于 Proxy，它复写了 MySQL 协议，将 Mycat Server 伪装成一个 MySQL 数据库，而 Sharding-JDBC 是基于 JDBC 的扩展，是以 jar 包的形式提供轻量级服务的。

这也就是微服务中的SpringCloud Ribbon与Nginx区别。


### Sharding-Jdbc实现读写分离

Sharding-Jdbc实现读写分离原理，非常容易。只需要在项目中集成主和从的数据源,Sharding-Jdbc自动根据DML和DQL语句类型连接主或者从数据源。

注意： Sharding-Jdbc只是实现连接主或者从数据源，不会实现主从复制功能，需要自己配置数据库自带主从复制方式。
查看MasterSlaveDataSource即可查看该类getDataSource方法获取当前数据源名称
DML：数据查询语言DQL基本结构是由SELECT子句，FROM子句，WHERE 子句组成的查询块： SELECT <字段名表> FROM <表或视图名> WHERE <查询条件>
DQL：数据操纵语言DML主要有三种形式： 1) 插入：INSERT 2) 更新：UPDATE 3) 删除：DELETE
