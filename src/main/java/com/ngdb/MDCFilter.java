package com.ngdb;

import org.apache.log4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.shiro.subject.support.DefaultSubjectContext.PRINCIPALS_SESSION_KEY;

public class MDCFilter implements Filter {

    private FilterConfig config;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        MDC.put("UserPrincipal", getUserPrincipal(request));
        MDC.put("Query", request.getRequestURI());
        chain.doFilter(request, response);
    }

    private static final Map<String, String> knownUsers = new HashMap<String, String>() {{
        put("173.192.34.91", "monitis-us");
        put("37.59.48.24", "monitis-fr");
        put("127.0.0.1", "local");
    }};

    private String getUserPrincipal(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String ip = request.getRemoteAddr();
        if (session == null) {
            String knownUser = knownUsers.get(ip);
            if(knownUser != null) {
                return knownUser;
            }
            return ip;
        }
        Object login = session.getAttribute(PRINCIPALS_SESSION_KEY);
        if(login == null) {
            return ip;
        }
        return login.toString();
    }

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    public void destroy() {
    }

}