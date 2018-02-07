package by.tr.hotelbooking.controller.servlet;

import by.tr.hotelbooking.entities.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CharsetFilter implements Filter {
    private String encoding;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);

        String command = request.getParameter(RequestParameter.COMMAND.getValue());
        if(AdminCommandsProvider.getInstance().isAdminCommand(command)) {
            HttpSession session = ((HttpServletRequest) request).getSession();
            String role = String.valueOf(session.getAttribute(RequestParameter.ROLE.getValue()));
            Role currentRole = Role.valueOf(role);
            if (currentRole != Role.ADMINISTRATOR) {
                request.setAttribute(RequestParameter.INFORMATION.getValue(), "WRONG ACCESS RIGHTS");
                ForwarRedirectChooser.doForward((HttpServletRequest) request, (HttpServletResponse) response, JspPageName.ADMIN_USER_PAGE.getPath());
                return;
            }
        }

        chain.doFilter(request, response);

    }

    @Override
    public void init(FilterConfig fConfig) {
        encoding = fConfig.getInitParameter("characterEncoding");
    }
    @Override
    public void destroy() {
    }
}
