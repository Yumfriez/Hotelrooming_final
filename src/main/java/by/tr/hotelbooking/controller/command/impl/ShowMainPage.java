package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.servlet.JspPageName;
import by.tr.hotelbooking.controller.servlet.ResponseTypeChooser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowMainPage implements Command {
    private static final ShowMainPage instance = new ShowMainPage();

    private ShowMainPage() {
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