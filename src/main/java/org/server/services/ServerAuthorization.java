package org.server.services;

import org.server.ServerStartup;
import org.server.models.User;

public class ServerAuthorization {

    public static User login(String login, String password) {
        User dbUser = ServerStartup.userDao.getByLogin(login);
        if (dbUser != null) {
            if (PasswordEncryptionService.checkPassword(password, dbUser.getPassword(), dbUser.getSalt())) {
                return dbUser;
            }
        }
        return null;
    }
}
