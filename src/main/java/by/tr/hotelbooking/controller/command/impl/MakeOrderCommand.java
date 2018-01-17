package by.tr.hotelbooking.controller.command.impl;

import by.tr.hotelbooking.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MakeOrderCommand implements Command {

    private final static MakeOrderCommand instance = new MakeOrderCommand();

    private MakeOrderCommand(){}

    public static MakeOrderCommand getInstance(){
        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

    }
}
