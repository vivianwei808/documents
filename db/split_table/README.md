# 分库分表
分库分表的概念以及水平拆分、垂直拆分的概念就不累赘了。


## 使用MyCat实现水平分片策略
 MyCat支持10种分片策略
- 1、
- 2、分片枚举
- 3、范围约定å
- 4、日期指定
- 5、固定分片hash算法
- 6、通配取模
- 7、ASCII码求模通配
- 8、编程指定
- 9、字符串拆分hash解析
详细:http://www.mycat.io/document/mycat-definitive-guide.pdf


### 分片枚举
分片枚举这种规则适用于特定的场景，比如有些业务需要按照省份或区县来做保存，而全国的省份区县固定的，这类业务使用这一规则。
根据地区进行分库 湖北数据库、江苏数据库 山东数据库,配置如下

1.案例步骤：
创建数据库userdb_1、userdb_2、userdb_3 
2.修改partition-hash-int.txt 规则
wuhan=0
shanghai=1
suzhou=2


### 求模算法

1、根据id进行十进制求摸运算，运算结果为分区索引  
注意：数据库节点分片数量无法更改。  和ES集群非常相似的。

Mycat中的路由结果是通过分片字段和分片方法来确定的,如果查询条件有id字段的情况还好，查询将会落到某个具体的分片。

如果查询没有分片的字段，会向所有的db都会查询一遍，让后封装结果级给客户端。修改/usr/local/mycat/conf/log4j2.xml日志级别为debug可查看到发起的sql。

## Sharding-Jdbc分表分库
### LogicTable
数据分片的逻辑表，对于水平拆分的数据库(表)，同一类表的总称。
订单信息表拆分为2张表,分别是t_order_0、t_order_1，他们的逻辑表名为t_order。

### ActualTable
在分片的数据库中真实存在的物理表。即上个示例中的t_order_0、t_order_1。
### DataNode
数据分片的最小单元。由数据源名称和数据表组成，例：test_msg0.t_order_0。配置时默认各个分片数据库的表结构均相同，直接配置逻辑表和真实表对应关系即可。
### ShardingColumn
分片字段。用于将数据库(表)水平拆分的关键字段。SQL中如果无分片字段，将执行全路由，性能较差。Sharding-JDBC支持多分片字段。
### ShardingAlgorithm
分片算法。Sharding-JDBC通过分片算法将数据分片，支持通过等号、BETWEEN和IN分片。分片算法目前需要业务方开发者自行实现，可实现的灵活度非常高。未来Sharding-JDBC也将会实现常用分片算法，如range，hash和tag等。


### SpringBoot整合Sharding-Jdbc分为两种方式
一种为原生配置方式，自己需要实现接口。见sharding-Jdbc1
1.分库算法类需要实现SingleKeyDatabaseShardingAlgorithm<T>接口
2.分表算法类需要实现SingleKeyTableShardingAlgorithm<T>接口
第二种通过配置文件形式配置。见sharding-Jdbc2
案例比如：t_order 拆分程t_order_0 t_order _1

### Sharding-Jdbc日志分析与原理图
Sharding-JDBC中的路由结果是通过分片字段和分片方法来确定的,如果查询条件中有 id 字段的情况还好，查询将会落到某个具体的分片
如果查询没有分片的字段，会向所有的db或者是表都会查询一遍，让后封装结果级给客户端。
Sharding-Jdbc和MyCat查询原理大致相同


![图片 1](http://www.vivianwei808.top/upload/2019/8/%E5%9B%BE%E7%89%87%201-a1e4fabcbf4d49868c16d0f1a0afb894.png)


