package by.tr.hotelbooking.controller.servlet;

import by.tr.hotelbooking.controller.command.CommandName;

import java.util.HashSet;
import java.util.Set;

public class AdminCommandsProvider {

    private final static AdminCommandsProvider INSTANCE = new AdminCommandsProvider();

    private Set<CommandName> adminRepository = new HashSet<>();

    private AdminCommandsProvider(){
        adminRepository.add(CommandName.SHOW_USERS);
        adminRepository.add(CommandName.SHOW_PAGE_FOR_ADD_HOTELROOM);
        adminRepository.add(CommandName.SHOW_EDIT_HOTELROOM_PAGE);
        adminRepository.add(CommandName.ADD_HOTELROOM);
        adminRepository.add(CommandName.REMOVE_HOTELROOM);
        adminRepository.add(CommandName.EDIT_HOTELROOM);
        adminRepository.add(CommandName.FIND_HOTELROOMS);
        adminRepository.add(CommandName.BLOCK_USER);
        adminRepository.add(CommandName.UNBLOCK_USER);
    }

    public static AdminCommandsProvider getInstance(){
        return INSTANCE;
    }

    public boolean isAdminCommand(String command){
        CommandName commandName = CommandName.valueOf(command.toUpperCase());
        return adminRepository.contains(commandName);
    }
}
