package manager;


import db.DBConnectionProvider;
import model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserManager {
    private final Connection connection;
    public UserManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public void addUser(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into user (name,surname,email,password) values (?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());

//        es toxi masin moracel einq
        preparedStatement.executeUpdate();


        int id;
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            id = generatedKeys.getInt(1);
            user.setId(id);
        }
    }

    public List<User> getAllUsers() throws  SQLException{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
        List<User> users = new LinkedList<User>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            users.add(user);
        }
        return users;
    }

    public void deleteUserById(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM user WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public void printUsers() throws SQLException {
        getAllUsers().forEach(System.out::println);
    }
}