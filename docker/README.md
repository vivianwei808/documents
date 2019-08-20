
## docker简介
Linux Container容器是一种内核虚拟化技术，可以提供轻量级的虚拟化，以便隔离进程和资源。
Docker 是 PaaS 提供商 dotCloud 开源的一个基于 LXC 的高级容器引擎，源代码托管在 Github 上, 基于go语言并遵从Apache2.0协议开源。

很早之前如果要做容器隔离，就是用的lxc进行（linux内核就支持）。容器化技术有非常多的好处，而docker目前已经属于一家独大。
与传统虚拟化做对比，传统虚拟化：host os用hypervisor做虚拟化，分割出多个虚拟机，此时的应用是跑在虚拟机上的，同时虚拟机上是有各自的操作系统的，而docker可以直接在宿主系统上通过docker引擎直接让应用跑到容器中的，少了虚拟机的操作系统，同时节约了不少资源消耗。

![203fb80e7bec54e7719c18b0bb389b504fc26a2f](http://www.vivianwei808.top/upload/2019/8/203fb80e7bec54e7719c18b0bb389b504fc26a2f-5735b4cf2e884e8889ec2511f59766e9.jpg)

网络通讯示意图

docker默认通过docker0进行桥接通讯，而它其实也是通过修改iptables的nat的规则进行实现网络通讯。

![u=1783287420,2795886359&fm=26&gp=0](http://www.vivianwei808.top/upload/2019/8/u=1783287420,2795886359&fm=26&gp=0-a7ae877dd984487d853cedfec134f46c.jpg)


![Docker 容器化技术](http://www.vivianwei808.top/upload/2019/8/Docker%20%E5%AE%B9%E5%99%A8%E5%8C%96%E6%8A%80%E6%9C%AF-256b5525a3e7453a825277db1c58cb8a.png)

[思维导图附件](http://www.vivianwei808.top/upload/2019/8/Docker%20%E5%AE%B9%E5%99%A8%E5%8C%96%E6%8A%80%E6%9C%AF.mindnode-785d856acbc14a8ea2de5869b0c813b0.zip)