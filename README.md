## 1、在RestController中返回String值无法访问到html资源

最开始我的代码是下面这样的：

```java
@RestController
public class Login {
    @RequestMapping(value = "/")
    public String login(){
        return "/Login";
    }
}
```

一开始以为是Thymeleaf模板引擎的配置问题，然后各种查阅Thymeleaf相关配置。。。

后来才知道是`@RestController`注解问题，这个注解包含了`@Controller`和`@ResponseBody`两个注解，因此每次返回String对象都只是显示字符串，而不会访问对应名称的HTML资源。只要将`@RestController`注解改为`@Controller`注解就OK了，修改后的代码如下：

```java
@Controller
public class Login {
    @RequestMapping(value = "/")
    public String login(){
        return "/Login";
    }
}
```



***



## 2、静态资源（CSS，JS）无法访问

#### 1）、没有配置资源映射处理器

按照之前所学，spring boot会将访问静态资源路径自动映射到`classpath:static`文件夹下。**但这只是映射一层目录，访问过程中不会递归查找子目录**。因此想不做其他配置而直接访问成功，需要将所有静态文件全部放置在该目录下。这很明显对之后的管理十分不便。

所以我们需要手动配置映射路径：该配置的意思是将所有访问`/static/**`（即static目录和子目录）下的文件都会映射到`classpath:/static/`下，然后就与spring boot为我们自动映射的目录相一致了，就可以访问静态资源了。

```java
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
```

还有一点需要注意的是：在HTML文件中资源路径名最好写全，即写完整路径，这样出错的机率会更小一些

```html
<link th:href="@{/static/css/style.css}" rel='stylesheet' type='text/css' media="all"/>
```

#### 2）、拦截器配置没有排除静态资源

在写登陆页面时，我们都会写好拦截器用来拦截直接后台访问。此时如果不注意我们就会把静态资源同时拦截。因此，除了排除登录页面等，我们还需要加上静态资源。

```java
 /**
  * 注册拦截器
  * @param registry
  */
@Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginHandleInterceptor()).addPathPatterns("/**")
        .excludePathPatterns("/", "/login.html", "/loginTest", "/static/**");//这里配置了静态资源目录
}
```



## 3、spring boot配置MySQL8注意事项

首先是maven依赖：需要使用8.0版本的MySQL依赖

```xml
<!--数据库驱动-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.11</version>
</dependency>
<!--jdbc驱动，使用jdbcTemplate-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```

其次是数据源配置，对于高版本的MySQL数据源配置稍稍有些不同

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/gz_db?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=123456
```

- 首先是驱动包的不同，由下图可以看出`com.mysql`包下有`cj`和`jdbc`包，并且`jdbc`包基本已经没用了所有重要类都在`cj`包下，因此配置驱动的时候需要修改原先的写法
- ![1553917434702](C:\Users\MrXu\AppData\Roaming\Typora\typora-user-images\1553917434702.png)

- 其次是url的写法：需要在url后面加上`?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true`这一段。



## 4、spring boot的自定义配置

以前使用SSM时，我们都会配置一个springmvc.xml的配置文件，里面会配置一些静态资源文件映射，拦截器等信息。

在spring boot中，自动配置往往只能满足基本需求，我们还会需要自定义配置更多信息。我们可以建立一个目录：`config/MyMvcConfig.java`，在该配置类中自定义我们想要的配置，如下所示

```java
// @EnableWebMvc如果使用该注解，则代表完全不使用自动配置，所有配置都自己定义
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    /**
     * 配置静态资源映射
     * @param registry 注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
    /**
     * 配置html资源访问映射
     * @param registry 注册器
     */
    @Override   
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/background.html").setViewName("success");
    }
```



## 5、`@EnableWebMvc`源码分析

首先查看`@EnableWebMvc`注解源码：

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DelegatingWebMvcConfiguration.class)	//重要
public @interface EnableWebMvc {}
```

可以看出该注解导入了`DelegatingWebMvcConfiguration.class`。换句话说，如果使用该注解，spring容器中就会有这个类的bean。

然后看这个类的声明如下：可以知道该类的父类是`WebMvcConfigurationSupport`。

```java
@Configuration
public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {...}
```

再来看看spring boot为我们提供的MVC自动配置类：该类里面使用了大量的`@Bean`注解，为自动配置注入了大量的过滤器、自动映射器等。

重要的一句是`@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)`，`@ConditionalOnMissingBean`这个注解的意思是，如果spring容器中不存在该bean，则使用自动配置，如果存在则不使用。

```java
@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class })
@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)	//重要
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
@AutoConfigureAfter({ DispatcherServletAutoConfiguration.class,
		TaskExecutionAutoConfiguration.class, ValidationAutoConfiguration.class })
public class WebMvcAutoConfiguration {...}
```

综上所述：**使用`@EnableWebMvc`注解则会导入`DelegatingWebMvcConfiguration`，该类是`WebMvcConfigurationSupport`的子类，则表示spring容器中存在`WebMvcConfigurationSupport`，然后使得`@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)`配置生效，导致spring boot为我们提供的自动配置全部无效**





