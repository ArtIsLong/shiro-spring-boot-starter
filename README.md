[TOC]

# 简介

该项目主要利用Spring Boot的自动化配置特性来实现快速的将Shiro集成到SpringBoot应用中

- 源码地址
  - Github：https://github.com/ArtIsLong/shiro-spring-boot-starter
  - 码云：https://gitee.com/ArtIsLong/shiro-spring-boot-starter
  - 我的博客：https://www.chenmin.info

**自制的小工具，欢迎使用和Star，如果使用过程中遇到问题，可以提出Issue，我会尽力完善该工具**

# 版本基础

- SpringBoot：1.5.X
- Shiro：1.4.0

# 如何使用

- 添加依赖

  pom.xml

  ~~~xml
  <dependency>
      <groupId>com.github.shiro</groupId>
      <artifactId>shiro-spring-boot-starter</artifactId>
      <version>1.0</version>
  </dependency>
  ~~~

  build.gradle

  ~~~groovy
  compile 'com.github.shiro:shiro-spring-boot-starter:1.0'
  ~~~

- 开启自动配置

  在配置类中使用@EnableShiro注解开启Shiro