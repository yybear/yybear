package org.bear.framework.support.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bear.framework.util.RequestUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-6
 */
public class ConfigurableInterceptor implements HandlerInterceptor {
	private final String CACHE_KEY = "hi_" + hashCode() + "_";
    private String[] excludes;
    private String[] includes;
    protected UrlPathHelper urlPathHelper = RequestUtils.URL_PATH_HELPER;
    protected PathMatcher pathMatcher = RequestUtils.PATH_MATCHER;

    public void setExcludes(String[] excludes) {
        this.excludes = excludes;
    }

    public void setIncludes(String[] includes) {
        this.includes = includes;
    }

    public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
        this.urlPathHelper = urlPathHelper;
    }

    public void setPathMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return !needProcess(request) || internalPreHandle(request, response, handler);
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (needProcess(request)) {
            internalPostHandle(request, response, handler, modelAndView);
        }
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (needProcess(request)) {
            internalAfterCompletion(request, response, handler, ex);
        }
        request.removeAttribute(getCacheKey(request));
    }

    public boolean internalPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    public void internalPostHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    public void internalAfterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    private boolean needProcess(HttpServletRequest request) {
        String key = getCacheKey(request);
        Boolean need = (Boolean) request.getAttribute(key);
        if (need != null) {
            return need;
        }
        //先排除不需要处理的请求
        need = !RequestUtils.matchAny(request, urlPathHelper, pathMatcher, excludes);
        if(need){
            need = includes == null || RequestUtils.matchAny(request, urlPathHelper, pathMatcher, includes);//includes为空或者匹配的表示需要处理
        }
        request.setAttribute(key, need);
        return need;
    }

    private String getCacheKey(HttpServletRequest request) {
        return CACHE_KEY + request.getRequestURI();
    }


}
