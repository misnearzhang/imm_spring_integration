# imm_web_service

###目标

1. ***springMVC的使用***3/16/2017 4:19:58 PM 
2. ***mybatis的使用***
3. ***redis的使用***
4. ***RabbitMQ+spring集成使用***
4. ***maven的使用***
5. ***分布式session缓存的使用***

### 后期需求,分布式化服务 ###

------------星期四, 09. 二月 2017 09:24下午 -------------------------

###功能定义

1. ***用于[immcore](https://github.com/misnearzhang/imm_core_service)的支持方,提供文件上传,用户登录验证,immcore数据持久化***
2. ***用于后台用户管理***
3. ***提供webSocket支持***

###拓展学习
2. ***自定义中间件***

-----------2/10/2017 10:53:44 AM from windows 7---------------------



##目前(3/16/2017 4:20:08 PM  )

----------

>现在加入 rabbitMQ消息队列组件,接受来自core_imm发出的持久化请求

----------

>现在添加spring websocket 临时解决方案,使用rabbitMQ中间件处理websocket和tcp通讯问题

----------

