package cn.xuweiteng.springboot.component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Login check
 */
public class LoginHandleInterceptor implements HandlerInterceptor {

    /**
     * before starting the target method
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object email = request.getSession().getAttribute("admin_email");
        if(email == null){
            // did not login
            request.setAttribute("errorMessage", "还未登录");
            request.getRequestDispatcher("/").forward(request, response);
            return false;
        }else{
            // login
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
