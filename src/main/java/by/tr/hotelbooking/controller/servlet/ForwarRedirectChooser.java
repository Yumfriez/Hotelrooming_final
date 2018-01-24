package by.tr.hotelbooking.controller.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ForwarRedirectChooser {

    private ForwarRedirectChooser(){

    }

    public static void doRedirect(HttpServletResponse response, String servletPath, RequestCommandParameter commandValue){
        try {
            String path = servletPath + "?" + RequestParameter.COMMAND.getValue() + "=" + commandValue.getValue();
            response.sendRedirect(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void doForward(HttpServletRequest request, HttpServletResponse response, String path){
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        if (dispatcher != null) {
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
