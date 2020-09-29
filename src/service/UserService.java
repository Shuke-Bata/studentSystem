package service;

import dao.UserDao;
import entify.User;

public class UserService {
    public User login(User user) {
        UserDao userDao = new UserDao();
        User _user = userDao.login(user.getUserName());
        if (_user!=null && _user.getPassword().equals(user.getPassword())) {
            return  user;
        } else {
            return null;
        }
    }
}
