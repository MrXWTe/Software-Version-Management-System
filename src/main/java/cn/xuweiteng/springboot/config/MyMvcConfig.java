package cn.xuweiteng.springboot.config;


import cn.xuweiteng.springboot.component.LoginHandleInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {


    /**
     * 配置静态资源映射
     * @param registry 资源解析器注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }


    /**
     * 配置html资源访问映射
     * @param registry 视图控制器注册器
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/background-admin.html").setViewName("background-admin");
        registry.addViewController("/background-user-info.html").setViewName("background-user-info");
        registry.addViewController("/background-admin-user.html").setViewName("background-admin-user");
        registry.addViewController("/background-admin-software.html").setViewName("background-admin-software");
    }


    /**
     * 注册拦截器
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandleInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/", "/login.html", "/login/adminLogin", "/login/employeeLogin", "/static/**");
    }
}
