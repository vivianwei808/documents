## 连接池：别让连接池帮了倒忙
- 注意鉴别客户端SDK是否基于连接池：jedis、XXXPool（线程安全，可以并发获取链接和归还链接）、XXXClient（线程安全）、XXXConnection（非线程安全）
- 使用连接池务必确保复用：httpclient，别每次用就创建新的连接池
- 连接池的配置不是一成不变的：datasource 使用JMX Mbean观察连接池调优
- （补充）三种连接池如何设置两种『连接超时』：twotimeoutconfig