package by.tr.hotelbooking.controller.command;

import by.tr.hotelbooking.controller.command.impl.*;
import by.tr.hotelbooking.controller.servlet.RequestParameter;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandInvoker {
    private static Logger logger = Logger.getLogger(CommandInvoker.class);

    private final static CommandInvoker instance = new CommandInvoker();
    private Map<CommandName, Command> repository = new HashMap<>();

    private CommandInvoker() {
        repository.put(CommandName.SIGN_IN, SignIn.getInstance());
        repository.put(CommandName.AFTER_SIGN_IN, AfterSignIn.getInstance());
        repository.put(CommandName.AFTER_SIGN_UP, AfterSignUp.getInstance());
        repository.put((CommandName.SIGN_UP), SignUp.getInstance());
        repository.put(CommandName.REDIRECT, Redirect.getInstance());
        repository.put(CommandName.CHANGE_LOCALE, ChangeLocale.getInstance());
        repository.put(CommandName.EXIT, ExitFromAccount.getInstance());
        repository.put(CommandName.SHOW_USERS, ShowUsers.getInstance());
        repository.put(CommandName.SHOW_HOTELROOMS, ShowHotelroomsCommand.getInstance());
        repository.put(CommandName.SHOW_PAGE_FOR_ADD_HOTELROOM, ShowPageForAddHotelroomCommand.getInstance());
        repository.put(CommandName.SHOW_EDIT_HOTELROOM_PAGE, ShowPageForEditHotelroomCommand.getInstance());
        repository.put(CommandName.ADD_HOTELROOM, AddHotelroomCommand.getInstance());
        repository.put(CommandName.AFTER_HOTELROOM_OPERATION, AfterHotelroomOperationCommand.getInstance());
        repository.put(CommandName.REMOVE_HOTELROOM, RemoveHotelroomCommand.getInstance());
        repository.put(CommandName.EDIT_HOTELROOM, EditHotelroomCommand.getInstance());
        repository.put(CommandName.MAKE_ORDER, MakeOrderCommand.getInstance());
        repository.put(CommandName.SHOW_PAGE_FOR_MAKE_ORDER, ShowPageForMakeOrder.getInstance());
        repository.put(CommandName.SHOW_ORDERS, ShowOrdersCommand.getInstance());
        repository.put(CommandName.SHOW_COMMENTS, ShowCommentsCommand.getInstance());
        repository.put(CommandName.ADD_COMMENT, AddCommentCommand.getInstance());
        repository.put(CommandName.DELETE_COMMENT, DeleteCommentCommand.getInstance());
        repository.put(CommandName.FIND_HOTELROOMS, FindHotelroomsCommand.getInstance());
    }

    public static CommandInvoker getInstance() {
        return instance;
    }

    public Command getCommand(HttpServletRequest request) {
        Command command;
        String requestCommand = request.getParameter(RequestParameter.COMMAND.getValue());
        logger.info("Command from cliend: " + requestCommand);
        CommandName commandName = CommandName.valueOf(requestCommand.toUpperCase().replace('-', '_'));
        command = repository.get(commandName);
        return command;
    }
}
