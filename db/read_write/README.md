# 数据库相关技术

目前针对互联网主流的数据库mysql进行讨论。

- 高可用集群：主从复制
- 读写分离
- 分库分表
- 索引原理

### 高可用集群：主从复制
mysql中的主从复制是根据binlog二进制文件实现的。具体如图：

![主从同步](http://www.vivianwei808.top/upload/2019/8/%E4%B8%BB%E4%BB%8E%E5%90%8C%E6%AD%A5-d5f3f56296d745dc9200eed427811c52.png)

主mysql会将DML语句写入到二进制日志文件（binlog）中，主mysql会单独开启一个logdump线程把binlog传输给从mysql，此连接为长连接。

从mysql会开启一个IO线程来获取主mysql的binlog，从节点的sql线程依赖于IO线程，它是执行IO线程传输过来的binlog二进制文件。

### 主从复制原理
   MySQL的主从复制是MySQL本身自带的一个功能，不需要额外的第三方软件就可以实现，其复制功能并不是copy文件来实现的，而是借助binlog日志文件里面的SQL命令实现的主从复制，可以理解为我再Master端执行了一条SQL命令，那么在Salve端同样会执行一遍，从而达到主从复制的效果。
 从库生成两个线程，一个I/O线程，一个SQL线程；
 i/o线程去请求主库 的binlog，并将得到的binlog日志写到relay log（中继日志） 文件中；
主库会生成一个 log dump 线程，用来给从库 i/o线程传binlog；
 SQL 线程，会读取relay log文件中的日志，并解析成具体操作，来实现主从的操作一致，而最终数据一致；

![1](https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566217459267&di=72905e020632d263f5aa6859df4bdcfa&imgtype=0&src=http%3A%2F%2Fimg2018.cnblogs.com%2Fblog%2F940734%2F201811%2F940734-20181129101514133-1274442433.png)

#### 主从配置
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

### 读写分离
读写分离基于mysql的主从配置，读从库，写主库。

#### 基于mycat配置读写分离

##### 什么是MyCat
  MyCAT是一款由阿里Cobar演变而来的用于支持数据库，读写分离、分表分库的分布式中间件。MyCAT支持Oracle、MSSQL、MYSQL、PG、DB2关系型数据库，同时也支持MongoDB等非关系型数据库。

 MyCAT原理MyCAT主要是通过对SQL的拦截，然后经过一定规则的分片解析、路由分析、读写分离分析、缓存分析等，然后将SQL发给后端真实的数据块，并将返回的结果做适当处理返回给客户端。

官方网站:http://www.mycat.io/

##### Linux环境安装MyCat实现读写分离
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

##### Linux环境安装MyCat实现读写分离
1、进入bin目录 
  启动MyCat ./mycat start 
  停止MyCat ./mycat stop
2、查看/usr/local/mycat/logs wrapper.log日志 如果是为successfully 则启动成功

关闭防火墙:systemctl stop firewalld.service
只可读的账号      user  user  端口号8066
可读可写的账号    root  123456  端口号8066

```mycat配置见项目main->resources里```


#### 基于springboot2.0配置实现读写分离  一主一从
```springboot配置以及项目见github->documents->db->mysql_read_write```
