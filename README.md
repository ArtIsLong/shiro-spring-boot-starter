
# 简介

该项目主要利用Spring Boot的自动化配置特性来实现快速的将Shiro集成到SpringBoot应用中

- 源码地址
  - Github：https://github.com/ArtIsLong/shiro-spring-boot-starter
  - 码云：https://gitee.com/ArtIsLong/shiro-spring-boot-starter
- 我的博客：https://www.chenmin.info

**自制的小工具，欢迎使用和Star，如果使用过程中遇到问题，可以提出Issue，我会尽力完善该工具**

# 功能介绍

1. 修改Shiro默认Authc过滤器，增加在前后台分离项目架构下，配置未登录时跳转路径功能，做未登录时的提示信息之用。

   > **注：** Shiro默认过滤器相关路径跳转都采用重定向，导致在前后台分离的项目架构下，前台不能正确获取未登录的提示验证信息，故将相关路径跳转功能修改为转发跳转。

2. 增加默认过滤器配置功能，可通过配置文件灵活修改Shiro的11个默认过滤器及其属性。

3. 增加11种过滤器过滤规则配置功能

4. 默认使用开源库`org.crazycake:shiro-redis:3.2.2`集成redis

5. 增加shiro的redis独立配置功能，可为shiro单独配置单机redis、cluster、sentinel。

6. 默认集成`spring-boot-starter-data-redis`，可直接使用其redis配置，不需要为shiro单独配置redis，且`spring-boot-starter-data-redis`可拆卸。

7. 使用开源库`com.spring4all:swagger-spring-boot-starter:1.7.0.RELEASE`集成Swagger，默认关闭此开源库的配置，使用自定义Swagger配置。

8. 提供shiro基本的测试接口，可通过Swagger测试登录拦截等功能。

   > **注：** 测试接口可通过security.shiro.test=false关闭

# 版本基础

- SpringBoot：1.5.X
- Shiro：1.4.0

# 如何使用

## 添加依赖

pom.xml

~~~xml
<dependency>
    <groupId>com.github.artislong</groupId>
    <artifactId>shiro-spring-boot-starter</artifactId>
    <version>1.0</version>
</dependency>
~~~

build.gradle

~~~groovy
compile 'com.github.artislong:shiro-spring-boot-starter:1.0'
~~~

## 开启自动配置

在配置类中使用@EnableShiro注解开启Shiro自动配置功能，如：

~~~java
@EnableShiro
@SpringBootApplication
public class ExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }
}
~~~

## 配置详解

### 默认用户名密码配置

~~~yaml
security:
  shiro:
    defaultUserName: admin
    defaultPassword: 123
~~~

通过`defaultUserName`和`defaultPassword`可配置基本的安全验证信息，暂不支持配置从数据库获取验证信息

### ShiroFilter配置

~~~yaml
security:
  shiro:
    shiroFilter:
      filters:
        authc: com.github.artislong.shiro.DefaultFormAuthenticationFilter
      attributes:
        authc:
          noAccessUrl: /default/noLogin   # 未登录时跳转URL
~~~

通过shiroFilter可灵活配置自定义的Filter，配置规则如下：

- filters

  通过filters配置自定义的Filter，可覆盖Shiro默认的Filter；filters以`Map<DefaultFilterType, Class<? extends Filter>>`的格式接收数据，Map的Key为`DefaultFilterType`的枚举，Value为Filter的实现类的全路径，Filter实现类可参考Shiro中`DefaultFilter`中的11种过滤器实现。

- attributes

  通过attributes可配置自定义的Filter的属性值，目前只限于`java.lang.String`类型的属性配置。

**注：** 由于此工具修改了默认的authc过滤器，需要配置未登录时跳转URL(noAccessUrl)

### 过滤规则配置

~~~yaml
security:
  shiro:
    filterPattern:
      anon:   # 不需要Shiro拦截的请求URL
        - /api/v1/**  # swagger接口文档
        - /v2/api-docs
        - /swagger-ui.html
        - /webjars/**
        - /swagger-resources/**
      authc:   # 需要Shiro拦截的请求URL
        - /**
~~~

通过filterPattern配置Shiro各个类型的过滤规则，filterPattern以`Map<DefaultFilterType, List<String>>`的数据格式接收配置数据，Map的Key为`DefaultFilterType`的枚举，Value为对应的过滤路径集合。

### Shiro Session配置

~~~yaml
security:
  shiro:
    session:
      globalSessionTimeout: 30  # 登录过期时长(分钟)
      deleteInvalidSessions: true # 删除过期的session
      sessionIdCookieEnabled: false # session是否可以被保存到cookie中
      sessionIdUrlRewritingEnabled: false  # 是否去掉URL中的JSESSIONID
      sessionValidationSchedulerEnabled: true  # 是否定时检查session
~~~

当前Shiro的Session缓存管理器默认使用Redis来管理，暂不支持配置，后续会提供配置功能，以便灵活管理Session缓存。

### Redis配置

#### 配置spring-boot-starter-data-redis的redis配置

~~~yaml
spring:
  redis:
    database: 0
    host: localhost
    password:  # Redis服务器若设置密码，此处必须配置
    port: 6379
    timeout: 10000 # 连接超时时间（毫秒）
    pool:
      max-active: 8 # 连接池最大连接数（使用负数表示没有限制）
      max-idle: 8 # 连接池中的最大空闲连接
      min-idle: 0 # 连接池中的最小空闲连接
      max-wait: -1 # 连接池最大阻塞等待时间（使用负数表示没有限制）
~~~

spring-boot-starter-data-redis的redis配置此处不一一列举了，具体配置方式可自行查询资料。

#### Shiro单独的Redis配置

~~~yaml
security:
  shiro:
    redis:
      enabled: false  # 是否开启Redis单独配置
      database: 1
      host: localhost
      port: 6379
      password:  # Redis服务器若设置密码，此处必须配置
      timeout: 10000 # 连接超时时间（毫秒）
      pool:
        max-active: 8 # 连接池最大连接数（使用负数表示没有限制）
        max-idle: 8 # 连接池中的最大空闲连接
        min-idle: 0 # 连接池中的最小空闲连接
        max-wait: -1 # 连接池最大阻塞等待时间（使用负数表示没有限制）
      cluster:  # Redis集群配置
        nodes:
          - localhost:6380
          - localhost:6381
          - localhost:6382
      sentinel:  # Redis哨兵集群配置
        nodes: localhost:26380,localhost:26381,localhost:26382
        master: shiroRedis
~~~

Shiro单独的Redis配置与spring-boot-starter-data-redis的redis配置的配置基本一致，需要注意的是：

​	`security.shiro.redis.enabled`为是否开启Redis单独配置，如果开启，则使用此配置来作为Shiro的Redis存储服务，如果不开启，则使用spring-boot-starter-data-redis的redis配置。

### Swagger配置

在前后台分离的项目架构下，由于没有页面的存在，后台接口调试存在不便，此工具中默认集成Swagger，以便更加快捷方便的测试Shiro的相关配置。

~~~yaml
swagger:
  title: 测试Demo
  description: 测试Demo
  version: 1.0.RELEASE
  license: Apache License, Version 2.0
  license-url: https://www.apache.org/licenses/LICENSE-2.0.html
  terms-of-service-url: https://github.com/dyc87112/spring-boot-starter-swagger
  base-package: com.github.artislong.test
  base-path: /**
  exclude-path: /error, /ops/**
  docket:
    shiroDocket:
      title: 测试Demo
      description: 测试Demo
      version: 1.0.RELEASE
      license: Apache License, Version 2.0
      license-url: https://www.apache.org/licenses/LICENSE-2.0.html
      terms-of-service-url: https://github.com/dyc87112/spring-boot-starter-swagger
      base-package: com.github.artislong.web
      base-path: /**
      exclude-path: /error, /ops/**
~~~

使用开源库[com.spring4all:swagger-spring-boot-starter:1.7.0.RELEASE](https://github.com/SpringForAll/spring-boot-starter-swagger)集成Swagger，具体配置方式自行查看资料。工具中默认关闭此配置自动配置功能，只使用其中的参数配置功能，可通过`shiroDocket`覆盖默认测试接口配置功能。

由于项目中提供了默认的测试接口及Swagger配置功能，可通过以下配置开启或关闭默认配置

~~~yaml
security:
  shiro:
    test: false  # 是否开启默认测试接口
~~~

# 贡献者

- [ArtIsLong-陈敏](https://github.com/artislong)