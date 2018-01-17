package by.tr.hotelbooking.controller.servlet;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {
    private String encoding;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        chain.doFilter(request, response);

    }

    @Override
    public void init(FilterConfig fConfig) {
        encoding = fConfig.getInitParameter("characterEncoding");
        ServletContext servletContext = fConfig.getServletContext();
    }
    @Override
    public void destroy() {
    }
}
