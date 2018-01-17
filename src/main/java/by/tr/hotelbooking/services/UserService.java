package by.tr.hotelbooking.services;

import by.tr.hotelbooking.entities.User;
import by.tr.hotelbooking.services.exception.ServiceException;

import java.util.List;

public interface UserService {
    User signUp(String login, String password, String name, String surname, String email, String locale) throws ServiceException;

    User signIn(String login, String password) throws ServiceException;

    User getUserByLogin(String login) throws ServiceException;

    void updateLocale(String userLogin, String locale) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;
}
