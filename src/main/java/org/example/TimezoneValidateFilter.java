package org.example;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.TimeZone;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response; // Приведення до HttpServletResponse

        String timezoneParam = request.getParameter("timezone");


        if (timezoneParam != null && !timezoneParam.isEmpty()) {

            TimeZone timeZone = TimeZone.getTimeZone(timezoneParam);
            if ("GMT".equals(timeZone.getID()) && !timezoneParam.equals("GMT")) {

                httpResponse.setContentType("text/html;charset=UTF-8");
                httpResponse.getWriter().println("<html><body><h1>Invalid timezone</h1></body></html>");
                httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Використання статуса 400
                return;
            }
        }


        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
