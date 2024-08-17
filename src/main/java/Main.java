import db.DBConnectionProvider;
import manager.UserManager;
import model.User;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {

        UserManager userManager = new UserManager();
        User user = new User("Aram","Araqelyan","aram@gmail.com", "aaaa");
        userManager.addUser(user);

        userManager.deleteUserById(1);
        userManager.printUsers();
    }
}