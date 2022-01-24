package ua.olharudenko.libraryapp.web.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

public class SetLocaleFilter implements Filter {

    private static final Logger log = LogManager.getLogger(SetLocaleFilter.class);

    private static int count =0;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        count++;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("doFilter in SetLocaleFilter START");
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        if(count == 1) {
            Config.set(((HttpServletRequest) request).getSession(), "javax.servlet.jsp.jstl.fmt.locale", "UK");
            req.getSession().setAttribute("locale", "UK");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}

