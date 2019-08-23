package pl.coderslab.DAO;

import pl.coderslab.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private String qryCheckUser = "SELECT login, password,role FROM users WHERE login=?";
    private String qryAddUser = "INSERT INTO users (login, password, firstName, lastName, role " +
            "VALUES (?,?,?,?,1)";
    private String qryChangePassword = "UDATE users SET password=? WHERE login=?";
    private String qryChangeRole = "UPDATE users SET role=? WHERE login=?";
    private String qryChangeName = "UPDATE users SET firstName=?, lastName=? WHERE login=?";
    private String qryUserData = "SELECT users.id, users.login, users.firstName, users.lastName, userfunctions.role FROM " +
            "users INNER JOIN usersfunctions ON users.role=usersfunctions.id WHERE users.login=?";

    private static String URL="jdbc:mysql://localhost:3306/book?useSSL=false&characterEncoding=utf8&serverTimezone=CET&allowPublicKeyRetrieval=true";
    private static String USER="root";
    private static String PASSWORD="coderslab";
    private static UserDAO instance;
    private Connection connection;

    private UserDAO() throws SQLException, ClassNotFoundException {
        String classnName = "com.mysql.cj.jdbc.Driver";
        Class.forName(classnName);
        UserDAO.instance=this;
        connection = DriverManager.getConnection(UserDAO.URL,UserDAO.USER,UserDAO.PASSWORD);

    }

    public static UserDAO getInstance() throws SQLException, ClassNotFoundException {
        if (instance==null){
            instance = new UserDAO();
        }
        return instance;
    }

    public boolean addUser(User user) {
        boolean result=false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(qryAddUser);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());

            preparedStatement.executeUpdate();

            result = true;
        }catch (SQLException e){
            e.printStackTrace();
        }


        return result;
    }

    public List<String> userData (String login) throws SQLException{
        ResultSet rs;
        List <String> result;

            PreparedStatement preparedStatement=connection.prepareStatement(qryUserData);
            preparedStatement.setString(1,login);
            rs=preparedStatement.executeQuery();

            result=new ArrayList<>(5);
            int i=0;

            while (rs.next()){
                i=i+1;
                result.add(rs.getString(i));

            }
        return result;
    }



}
