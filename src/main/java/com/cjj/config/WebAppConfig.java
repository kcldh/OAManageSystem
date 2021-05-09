package com.cjj.config;

import com.cjj.converter.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

@Component
public class WebAppConfig {

    @Autowired // 处理器映射器(接口适配)
    private RequestMappingHandlerAdapter handlerAdapter;

    /**
     * 此方法解决前台提交的日期参数绑定不正确问题,将自己实现的StringToDateConverter交给spring,让其知道如何进行处理
     */
    @PostConstruct // @PostContruct是java的注解，该注解被用来修饰一个非静态的void（）方法,在方法上加该注解会在项目启动的时候执行该方法，也可以理解为在spring容器初始化的时候执行该方法。
    public void initEditableValidation() {
        // 获取web初始化对象
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter
                .getWebBindingInitializer();
        if (initializer.getConversionService() != null) {
            GenericConversionService genericConversionService = (GenericConversionService) initializer
                    .getConversionService();
            // 添加自定义的转换器
            genericConversionService.addConverter(new DateConverter());
        }
    }
}
