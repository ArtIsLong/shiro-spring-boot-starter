/** * $Id: SwaggerConfiguration.java,v 1.0 18/8/3 下午11:41 chenmin Exp $ * <p> */package com.github.shiro.config;import com.spring4all.swagger.EnableSwagger2Doc;import org.springframework.context.annotation.Configuration;import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;/** * @author chenmin * @version $Id: SwaggerConfiguration.java,v 1.1 18/8/3 下午11:41 chenmin Exp $ * Created on 18/8/3 下午11:41 */@Configuration@EnableSwagger2Docpublic class WebMvcConfiguration extends WebMvcConfigurerAdapter {    /**     * Shiro和Swagger集成后，Swagger静态资源也被Shiro拦截，所以此处需要重新加载一下Swagger的相关静态资源     * @param registry     */    @Override    public void addResourceHandlers(ResourceHandlerRegistry registry) {        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");        registry.addResourceHandler("swagger-ui.html")                .addResourceLocations("classpath:/META-INF/resources/");        registry.addResourceHandler("/webjars/**")                .addResourceLocations("classpath:/META-INF/resources/webjars/");    }}