package by.tr.hotelbooking.services.impl;

import by.tr.hotelbooking.dao.exception.DAOException;
import by.tr.hotelbooking.dao.factory.DaoFactory;
import by.tr.hotelbooking.dao.impl.UserDAO;
import by.tr.hotelbooking.entities.User;
import by.tr.hotelbooking.services.UserService;
import by.tr.hotelbooking.services.exception.ServiceException;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static final UserServiceImpl instance = new UserServiceImpl();

    private DaoFactory daoFactory = DaoFactory.getInstance();

    private UserServiceImpl() {

    }

    public static UserServiceImpl getInstance() {
        return instance;
    }



    @Override
    public User signUp(String login, String password, String name, String surname, String email, String locale) throws ServiceException {
        User user;
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            user = new User(login, password, name, surname, email, locale);
            userDAO.add(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public User signIn(String login, String password) throws ServiceException {
        User user;
        User authorizedUser;
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            user = new User(login,password);
            authorizedUser = userDAO.signIn(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return authorizedUser;
    }

    @Override
    public User getUserByLogin(String login) throws ServiceException {
        UserDAO userDAO = daoFactory.getUserDAO();
        User user = null;
        try {
            user = userDAO.findUser(login);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public void updateLocale(String userLogin, String locale) throws ServiceException {
        try {
            UserDAO userDAO = daoFactory.getUserDAO();
            userDAO.updateLocale(userLogin, locale);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {

        UserDAO userDAO = daoFactory.getUserDAO();
        List<User> userList = null;
        try {
            userList = userDAO.getAll();
        }  catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userList;
    }

    @Override
    public void changeBlockStatus(String userLogin, boolean blockStatus) throws ServiceException {
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            userDAO.setBlockStatus(userLogin, blockStatus);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }
}
