# 本项目为学习项目

___

#### 源地址: [mall](https://github.com/macrozheng/mall)

#### 源文档地址: [mall学习教程目录](https://www.macrozheng.com/mall/catalog/mall_catalog.html#%E5%8F%8B%E6%83%85%E6%8F%90%E7%A4%BA)

___
> 2022-05-30
>
>> 1. 导入表结构
>> 2. 搭建程序骨架
>>
>>> SpringBoot为脚手架
>>>
>>> 整合Mybatis做为ORM映射
>>>
>>> 添加Mybatis-Mapper通用Mapper
>>>
>>> 添加Pagehelper分页工具
>>>
>>> 添加druid数据库连接池
>>>
>>> 添加Actuator监控服务状态
>>>
>>> 添加hutool-all工具类
>>
>> 3. 品牌信息进行编码实现
>>> 1. 分页获取品牌信息
>>> 2. 保存品牌信息
>>> 3. 依据主键编码更新品牌信息
>>> 4. 依据主键编码删除品牌信息
>>> 5. 依据主键编码获取指定品牌信息详情
>
> 2022-06-27
>
>> 1. 添加Springdoc作为在线文档
>> 2. 添加Knife4j框架增加API在线页面
>> 3. 会员登录注册编码实现
>>> 1. 依据电话号码生成验证码信息
>>> 2. 依据电话号码验证相应验证码
>> 4. 整合Redis作为缓存处理
>
> Redis整合笔记
>>
>> 1. 重写RedisTemplate模板
>> 2. 将template类型修改为<String, Object>键值对
>> 3. 设置Redis key(***hash***)序列化配置StringRedisSerializer
>> 4. 设置Redis value(***hash***)序列化配置Jackson2JsonRedisSerializer
>>> 1. 用ObjectMapper进行转义
>>> 2. 设置任何字段可见
>
> OpenApi3学习笔记
>> 1. @OpenAPIDefinition 类的文档说明
>>
>> @Info 详情信息
>>
>> @Contact 联系信息
>>
>> @License 许可证信息
>>
>> @Server 远程调用信息
>>
>> 2. @Tag 类、方法标签信息
>>
>> @Operation 方法描述信息
>>
>> @Parameter 方法、参数的定义信息
>>
>> @ApiResponse 方法声明的响应信息
>>
>> 3. @Content 定义参数、请求或响应的内容描述信息
>>
>> @Schema 定义参数、模型的概要信息
>
>2022-06-28
>
>> 1. 整合SpringSecurity权限认证和授权
>> 2. 添加后台用户管理编码实现
>>> 1. 依据用户名获取账号信息
>>> 2. 依据用户主键获取用户权限
>>> 3. 用户登录返回token
>>> 4. 用户信息注册
>
>   SpringSecurity整合笔记
>
>> 1. 实现获取登录用户信息接口
>> 2. 配置AuthenticationManager认证管理器
>> 3. 配置SecurityFilterChain过滤器(过滤器实现具体filter执行链过程)
>> 4. 配置BCryptPasswordEncoder加密方式
>> 5. 配置跨源访问(CORS)
>
>   JWT(JSON WEB TOKEN)学习笔记
>> JWT标准7个申明
>>>
>>> 1. setIssuer()                iss: 签发者
>>> 2. setSubject()               sub: 面向用户
>>> 3. setAudience()              aud: 接收者
>>> 4. setIssuedAt()              iat(issued at): 签发时间
>>> 5. setExpiration()            exp(expires): 过期时间
>>> 6. setNotBefore()             nbf(not before)：不能被接收处理时间，在此之前不能被接收处理
>>> 7. setId()                    jti：JWT ID为web token提供唯一标识
>>
>> 通过**signWith()** 设置签名方法
>
> 2022-06-29
>
>SpringSecurity学习笔记
>
>> SecurityFilterChain过滤器
>>
>> 1. 关闭Session会话,使用JWT进行安全交互
>> 2. 配置authorizeHttpRequests(http请求授权)制定放开URL路径
>> 3. 在用户密码认证过滤器之前,添加JWT过滤器(继承OncePerRequestFilter[每次请求时只执行一次])
>> 4. 添加exceptionHandling自定义异常处理
>>> 1. accessDeniedHandler(访问拒绝异常)
>>> 2. authenticationEntryPoint(身份验证失败异常)
>>> 
> 2022-07-14
> 
> 1. 搭建Docker - Elasticsearch(8.2.3)环境
> 2. 搭建DOcker - Kibana 环境
> 3. 学习Elasticsearch基础概念
> 4. 学习Elasticsearch基础API使用
> 5. 整合Elasticsearch 8.2.3 客户端(截至当前时间,Spring-Data整合的ES版本为7.17.3,无法使用)
>> 1. 利用Common-Pool对象连接池进行管理ES链接
>> 2. 添加ES索引、文档、查询工具类
> 
> 6. 实现商品搜索接口
>> 1. 数据库商品信息导入ES
>> 2. 从ES搜索商品信息
>> 
> 2022-07-20
>  
> 学习 Elasticsearch 不同版本API
> 1. 添加Elasticsearch 7.17.5 分支开发
> 2. 修改商品搜索实现类,采用 Spring Data Elasticsearch API 进行重写.
> 
> 2022-07-25
> 
> 1. 整合MongoDB文档数据存储
> 2. 整合RabbitMQ消息队列
> 3. 整合阿里云OSS文件上传
> 4. 整合jasypt进行配置文件的加密操作