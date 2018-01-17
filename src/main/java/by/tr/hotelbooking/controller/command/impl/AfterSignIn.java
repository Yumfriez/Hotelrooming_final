package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;
import by.tr.hotelbooking.services.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AfterSignIn implements Command {
    private static AfterSignIn instance = new AfterSignIn();

    private AfterSignIn() {
    }

    public static Command getInstance() {
        return instance;
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        ResponseTypeChooser responseTypeChooser = new ResponseTypeChooser();
        responseTypeChooser.doForward(request,response,JspPageName.ADMIN_USER_PAGE.getPath());
    }
}
