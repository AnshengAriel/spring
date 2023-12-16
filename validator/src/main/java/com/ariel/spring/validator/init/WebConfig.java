package com.ariel.spring.validator.init;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

@Configuration
@EnableWebMvc
public class WebConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                /*registry.addInterceptor(jwtInterceptor())
                        .addPathPatterns(API_APP + "/**")
                        .excludePathPatterns(API_APP_V1 + "/account/out/**");
                registry.addInterceptor(logInterceptor())
                        .addPathPatterns(API_APP + "/**");*/
            }

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true).maxAge(3600);
            }
        };
    }

    /**
     * 文件上传配置
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件数据大小
        factory.setMaxFileSize(DataSize.ofMegabytes(50));
        // 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(200));
        return factory.createMultipartConfig();
    }

//    @Bean
//    public LocalValidatorFactoryBean localValidatorFactoryBean() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
//        factoryBean.setConstraintValidatorFactory(factory.getConstraintValidatorFactory());
//
//        Validation.byDefaultProvider().configure().addValueExtractor()
//        return factoryBean;
//    }
}
