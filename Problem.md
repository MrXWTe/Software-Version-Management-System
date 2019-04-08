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



------



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



## 6、JdbcTemplate下划线转驼峰问题

下划线转驼峰是经常会遇到的问题，数据库字段命名一般都用下划线，比如`admin_name`，而pojo命名一般使用符合Java规范驼峰命名规则，如adminName。

在使用MyBatis时，我们可以直接在主配置文件中配置如下语句，就可以愉快的进行自动映射了（这种配置是真的爽）。

```xml
<settings>
    <setting name="mapUnderscoreToCamelCase" value="true" />
</settings>
```

在spring boot使用JdbcTemplate时，我们可以通过写sql语句进行下划线转驼峰的映射

```sql
select soft_name as softName, soft_info as softInfo, soft_author as softAuthor, soft_last_modified_date as softLastModifiedDate from tb_software
```



## 7、mysql操作

- 修改列属性

  ```mysql
  alter table table_name modify column column_name datetime not null;
  ```

- 



## 8、跳转页面问题

从`controller`跳转到其他页面时，**一定要确认跳转的页面没有用thyme leaf模板引擎获取其他参数**，否则就会报如下错误：

```java
An error happened during template parsing (template: "class path resource [templates//background-admin-user.html]"
```

看到这种错误就会一直以为自己的跳转路径是否有错，`controller`是跳转重定向问题还是内部转发问题，**一开始是真没想到跳转的页面用了模板引擎获取值**



## 9、删除用户碰到的问题

我在显示用户列表时使用的thyme leaf模板引擎中的`th:each`标签，每一条记录的最右边包含修改和删除按钮，单机删除按钮即可删除记录。具体html代码如下：

```html
<table class="table table-striped ">
    <thead>
        <tr>
            <th>userId</th>
            <th>userName</th>
            <th>userEmail</th>
            <th>userEnrollDate</th>
            <th>userStatus</th>
            <th>operation</th>
        </tr>
    </thead>
    <tbody>
        <tr th:each="user : ${userList}">
            <td th:text="${user.userId}"></td>
            <td th:text="${user.userName}"></td>
            <td th:text="${user.userEmail}"></td>
            <td th:text="${user.userEnrollDate}"></td>
            <td th:text="${user.userStatus}"></td>
            <td>
                <input type="submit" class="btn btn-sm btn-success" value="修改"> 
                <form th:action="@{/admin/deleteUser/(userId=${user.userId}}" 								  method="get">
                	<button class="btn btn-sm btn-danger deleteBtn">删除</button>    
                </form>
            </td>
        </tr>
    </tbody>
</table>
```

**在这里的`button`中如果直接使用`form`表单包含起来，这样不但会使得渲染后的页面达不到预期的效果，并且使得整体页面过于臃肿**。

因此，推荐使用如下方法

```html
<table class="table table-striped ">
    <thead>
        <tr>
            <th>userId</th>
            <th>userName</th>
            <th>userEmail</th>
            <th>userEnrollDate</th>
            <th>userStatus</th>
            <th>operation</th>
        </tr>
    </thead>
    <tbody>
        <tr th:each="user : ${userList}">
            <td th:text="${user.userId}"></td>
            <td th:text="${user.userName}"></td>
            <td th:text="${user.userEmail}"></td>
            <td th:text="${user.userEnrollDate}"></td>
            <td th:text="${user.userStatus}"></td>
            <td>
                <input type="submit" class="btn btn-sm btn-success" value="修改"> 
                <button th:attr="del_uri=@{/admin/deleteUser/(userId=${user.userId})}"
                        class="btn btn-sm btn-danger deleteBtn">删除</button>
            </td>
        </tr>
    </tbody>
</table>

<form id="deleteForm" method="post"> </form>
<script>
    $(".deleteBtn").click(function () {
        var r = confirm("确认是否删除该用户");
        if(r == true) {
            $("#deleteForm").attr("action", $(this).attr("del_uri")).submit();
        }
    });
</script>
```

这里用JS定义按钮的单击事件，并且用一个`confirm`进行二次确认，以免误删。然后获取表单id号，用.attr()方法设置`action`属性为`$(this).attr("del_uri")`。

这里有个小技巧，使用thyme leaf模板引擎中的`th:attr`标签可以自定义html标签属性，`th:attr="del_uri=@{/admin/deleteUser/(userId=${user.userId})}"`，该语句便定义了del_uri属性为跳转的URL，因此删除功能就能够完成了。



## 10、前后端数据传送的日期转换问题

#### 1）、前端传后端

问：前端传输的日期数据一般是String形式的，如何转换成pojo中的Date形式？

在相关字段上使用JsonFormat注解

```java
@JsonFormat(pattern = “yyyy-MM-dd HH:mm:ss”, timezone = “GMT+8”) 
private Date time;
```

#### 2)、后端传前端

问：后端传输的日期数据一般是时间戳形式的，如何以指定的形式返回？

解决一：直接在`application.properties`中进行全局配置，**但这种方法会使得全局使用该配置**

```properties
spring.jackson.time-zone=GMT+8
spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
```

解决二：在相关字段上使用JsonFormat注解

```java
@JsonFormat(pattern = “yyyy-MM-dd HH:mm:ss”, timezone = “GMT+8”) 
private Date time;
```



