# 本项目为学习项目

___

#### 源地址: [mall](https://github.com/macrozheng/mall)

#### 源文档地址: [mall学习教程目录](https://www.macrozheng.com/mall/catalog/mall_catalog.html#%E5%8F%8B%E6%83%85%E6%8F%90%E7%A4%BA)

___
>2022-05-30
>
>1.导入表结构
>
>2.搭建程序骨架
>
>> SpringBoot为脚手架
>>
>> 整合Mybatis做为ORM映射
>>
>> 添加Mybatis-Mapper通用Mapper
>>
>> 添加Pagehelper分页工具
>>
>> 添加druid数据库连接池
>>
>> 添加Actuator监控服务状态
>>
>> 添加hutool-all工具类
>
>3.品牌信息进行编码实现
>
>> 1.分页获取品牌信息
>>
>> 2.保存品牌信息
>>
>> 3.依据主键编码更新品牌信息
>>
>> 4.依据主键编码删除品牌信息
>>
>> 5.依据主键编码获取指定品牌信息详情
>
>
>
>2022-06-27
>
>1.添加Springdoc作为在线文档
>
>2.添加Knife4j框架增加API在线页面
>
>> OpenApi3学习笔记
>>
>> 1.  @OpenAPIDefinition 类的文档说明
>>
>>     @Info 详情信息
>>
>>     @Contact 联系信息
>>
>>     @License 许可证信息
>>
>>     @Server 远程调用信息
>>
>> 2. @Tag 类、方法标签信息
>>
>>    @Operation 方法描述信息
>>
>>    @Parameter 方法、参数的定义信息
>>
>>    @ApiResponse 方法声明的响应信息
>>
>> 3. @Content 定义参数、请求或响应的内容描述信息
>>
>>    @Schema 定义参数、模型的概要信息
>
>3.会员登录注册编码实现
>
>> 1.依据电话号码生成验证码信息
>>
>> 2.依据电话号码验证相应验证码
>
>4.整合Redis作为缓存处理
>
>> Redis整合笔记
>>
>> 1. 重写RedisTemplate模板
>>
>> 2. 将template类型修改为<String, Object>键值对
>>
>> 3. 设置Redis key(***hash***)序列化配置StringRedisSerializer
>>
>> 4. 设置Redis value(***hash***)序列化配置Jackson2JsonRedisSerializer
>>
>>    a. 用ObjectMapper进行转义
>>
>>    b. 设置任何字段可见

