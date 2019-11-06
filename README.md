# springboot-es
springboot 整合 elasticsearch

##### 一、windowns安装elasticsearch
```
# 下载安装包
https://www.elastic.co/cn/products/elasticsearch

# 解压运行
 1.解压
 2.进入elasticsearch/bin  双击elasticsearch.bat运行
 
# 端口
 1.9300:浏览器、postman访问的端口
 2.9200：java程序访问的端口  
```



##### 二、安装Elasticsearch-head插件
```
# 什么是head
   Elasticsearch-head是一款专门针对于Elasticsearch的客户端工具
   
# 下载地址
   https://github.com/mobz/elasticsearch-head
   
# 安装步骤
   1.https://nodejs.org/en/download/  下载相应系统的msi，双击安装
   2.安装完成用cmd进入安装目录执行 node -v可查看版本号
   3.执行 npm install -g grunt-cli 安装grunt ，安装完成后执行grunt -version查看是否安装成功，会显示安装的版本号
   4.进入Elasticsearch安装目录下的config目录，修改elasticsearch.yml文件.在文件的末尾加入以下代码
   
         http.cors.enabled: true 
         http.cors.allow-origin: "*"
         node.master: true
         node.data: true
         
   5.去掉network.host: 192.168.0.1的注释并改为network.host: 0.0.0.0，去掉cluster.name；node.name；http.port的注释
   6.双击elasticsearch.bat重启Elasticsearch
   7.在https://github.com/mobz/elasticsearch-head中下载head插件，选择下载zip
   8.解压到指定文件夹下
   9.elasticsearch-head-master\Gruntfile.js 在对应的位置加上hostname:’*’
   10.打开cmd命令行窗口，elasticsearch-head-master 下执行npm install 安装
   11.完成后执行grunt server 或者npm run start 运行head插件，如果运行不成功建议重新安装grunt
   12.成功访问 http://localhost:9100
            
```
