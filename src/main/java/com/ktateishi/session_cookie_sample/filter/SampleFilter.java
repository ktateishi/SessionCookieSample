package com.ktateishi.session_cookie_sample.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

import static com.ktateishi.session_cookie_sample.symbol.Symbols.COOKIE_KEY;

@Component
public class SampleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        // クッキーがなければログインしていないとみなしログイン画面へ遷移
        if (!"/login".equals(((HttpServletRequest) servletRequest).getServletPath())
                && Arrays.stream(((HttpServletRequest) servletRequest).getCookies())
                .noneMatch(cookie -> COOKIE_KEY.equals(cookie.getName()))) {
            RequestDispatcher rd = servletRequest.getRequestDispatcher("/");
            rd.forward(servletRequest, servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
