.下载并解压es tar包即可。最好是5.6.9版本，因为 spring全家桶用的是这个版本的
.配置文件有  elasticsearch.yml 和 jvm.options,可以修改里边的配置
https://www.jianshu.com/p/e59a3cce5840
.新建用户和组，并为用户赋予解压elasticsearch后产生的文件夹权限
adduser 你的用户名
passwd  你的用户名 ，然后会让你输入密码
chown -R 你的用户名  文件夹名
.启动方式：进到bin下 ./elasticsearch -d
.验证安装结果
curl 'http://localhost:9200/?pretty'
