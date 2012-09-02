package com.ngdb.service.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.apache.shiro.subject.support.DefaultSubjectContext.PRINCIPALS_SESSION_KEY;

public class MDCFilter implements Filter {

    private static Log logger = LogFactory.getLog(MDCFilter.class);

    private FilterConfig config;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        MDC.put("UserPrincipal", getUserPrincipal(request));
        MDC.put("Query", request.getRequestURI());
        chain.doFilter(request, response);
    }

    private String getUserPrincipal(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "";
        }
        Object login = session.getAttribute(PRINCIPALS_SESSION_KEY);
        if(login == null) {
            return "";
        }
        return login.toString();
    }

    public void init(FilterConfig config) throws ServletException {
        logger.debug("Création du filtre MDCFilter");
        this.config = config;
    }

    public void destroy() {
        logger.debug("Destruction du filtre MDCFilter");
    }

}