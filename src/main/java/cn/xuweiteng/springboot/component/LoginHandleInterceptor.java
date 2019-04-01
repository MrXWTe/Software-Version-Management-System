package cn.xuweiteng.springboot.component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录检查
 */
public class LoginHandleInterceptor implements HandlerInterceptor {

    /**
     * 在方法执行前进行检查
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object loginAdmin = request.getSession().getAttribute("admin_name");
        if(loginAdmin == null){
            // 还未登录
            request.setAttribute("errorMessage", "还未登录");
            request.getRequestDispatcher("/login.html").forward(request, response);
            return false;
        }else{
            // 已登陆
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
