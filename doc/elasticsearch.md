------------------------------------
安装
------------------------------------
.下载并解压es tar包即可。最好是5.6.9版本，因为 spring全家桶用的是这个版本的
.配置文件有  elasticsearch.yml 和 jvm.options,可以修改里边的配置
elasticsearch.yml
cluster.name: my-application-1
bootstrap.memory_lock: true   ,关闭使用交换分区
network.host: 172.16.176.211  ,这个必须改，否则外部ip连不上

https://www.jianshu.com/p/e59a3cce5840
.新建用户和组，并为用户赋予解压elasticsearch后产生的文件夹权限
adduser elasticsearch
passwd  elasticsearch ，然后会让你输入密码
chown -R 你的用户名  文件夹名
.启动方式：进到bin下 ./elasticsearch -d
.验证安装结果
curl 'http://localhost:9200/?pretty'


错误集锦=====================================================
问题：锁定内存失败

解决方案：
切换到root用户，编辑limits.conf配置文件， 添加类似如下内容：
sudo vim /etc/security/limits.conf

添加如下内容:
* soft memlock unlimited
* hard memlock unlimited
备注：* 代表Linux所有用户名称

保存、退出、重新登录才可生效
临时取消限制
ulimit -l unlimited
--------------------------
问题：elasticsearch用户拥有的内存权限太小，至少需要262144；
解决：
切换到root用户
执行命令：
sysctl -w vm.max_map_count=262144
查看结果：
sysctl -a|grep vm.max_map_count
显示：
vm.max_map_count = 262144
上述方法修改之后，如果重启虚拟机将失效，所以：
解决办法：
在   /etc/sysctl.conf文件最后添加一行
vm.max_map_count=262144
即可永久修改
----------------------
springboot集成 es客户端
----------------------
elasticsearch原理及springbootdemo：https://blog.csdn.net/KingBoyWorld/article/details/78654820  https://github.com/KimZing/
.properties
spring.data.elasticsearch.cluster-name=my-application-1
spring.data.elasticsearch.cluster-nodes=172.16.176.211:9300
.pom.xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
        </dependency>
        <dependency>
            <groupId>org.elasticsearch.plugin</groupId> 
            <artifactId>transport-netty3-client</artifactId> 
            <version>5.6.11</version> 
        </dependency>
.写具体的代码
TestElasticsearchService.java








