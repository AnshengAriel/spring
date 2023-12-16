package com.ariel.springmvc.config;

import com.ariel.springmvc.filter.TestFilter;
import com.ariel.springmvc.interceptor.TestInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.PathContainer;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.servlet.DispatcherType;
import javax.websocket.WebSocketContainer;
import java.util.EnumSet;
import java.util.List;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new TestInterceptor())
                        .addPathPatterns("/**");
            }

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST");
            }


        };
    }

    @Bean
    public FilterRegistrationBean<TestFilter> testFilter() {
        FilterRegistrationBean<TestFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new TestFilter());
        bean.setUrlPatterns(List.of("/springmvc/user/*"));
        bean.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST));
        return bean;
    }

    @Bean
    public WebSocketContainer webSocketContainer() {
//        WebSocketContainer webSocketContainer = new WebSocketContainer();
        return null;
    }

    public static void main(String[] args) {
        PathPattern pattern = new PathPatternParser().parse("*");
        boolean matches = pattern.matches(PathContainer.parsePath("user"));
        System.out.println("matches = " + matches);
    }

}
