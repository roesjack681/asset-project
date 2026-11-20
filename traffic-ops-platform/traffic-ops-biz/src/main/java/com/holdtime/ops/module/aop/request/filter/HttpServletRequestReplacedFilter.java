package com.holdtime.ops.module.aop.request.filter;

import com.holdtime.ops.module.aop.request.wrapper.RequestWrapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
@WebFilter(filterName="bodyReaderFilter",urlPatterns="/*")
public class HttpServletRequestReplacedFilter implements Filter {
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ServletRequest requestWrapper = null;

        // 判断是否为multipart/form-data请求
        String contentType = request.getContentType();
        boolean isMultipart = contentType != null && contentType.toLowerCase().startsWith("multipart/form-data");

        if(request instanceof HttpServletRequest && !isMultipart) {
            requestWrapper = new RequestWrapper((HttpServletRequest) request);
        }
        
        
        //获取请求中的流如何，将取出来的字符串，再次转换成流，然后把它放入到新request对象中。
        // 在chain.doFiler方法中传递新的request对象
        if (requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
	public void destroy() {
	}
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
