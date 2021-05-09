package com.cjj.config;


import com.cjj.interceptor.LoginInterceptor;
import com.cjj.interceptor.PowerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan("com.cjj")
@Configuration
public class MyConfig {

    // 登陆拦截器
    @Autowired
    private LoginInterceptor loginInterceptor;

    // 权限拦截器
    @Autowired
    private PowerInterceptor powerInterceptor;


    /**
     *  开启rest风格自定义转换器
     * @return
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        hiddenHttpMethodFilter.setMethodParam("_rest");
        return hiddenHttpMethodFilter;
    }


    /**
     *  配置webMVC
     * @return  相应的配置
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {

        return new WebMvcConfigurer() {

            // 添加拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {

                // 配置登陆拦截器
                registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login.html", "/templates/**", "/index.html", "/test",
                        "/login", "/css/**", "/js/**", "/fonts/**", "/images/**", "/lib/layui/**");

                // 配置权限拦截器
                registry.addInterceptor(powerInterceptor)
                        .addPathPatterns("/*.html")
                        .excludePathPatterns("/", "/login.html","/index.html", "/5xx.html");
            }

//            @Override
//            public void configurePathMatch(PathMatchConfigurer configurer) {
//                UrlPathHelper urlPathHelper = new UrlPathHelper();
//                urlPathHelper.setRemoveSemicolonContent(false);
//                configurer.setUrlPathHelper(urlPathHelper);
//            }
        };
    }

}
