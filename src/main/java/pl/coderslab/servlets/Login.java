package pl.coderslab.servlets;

import pl.coderslab.DAO.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        System.out.println(login + ", " + password);

        try {
            UserDAO check= UserDAO.getInstance();
            int i = check.checkUser(login,password);
            if (i==0){
                getServletContext().getRequestDispatcher("/MyOwnMyPrecious/registerAccount.html").forward(request,response);
            } else {
                getServletContext().getRequestDispatcher("/MyOwnMyPrecious/showAllBooks.jsp").forward(request,response);
            }

        }catch (SQLException|ClassNotFoundException e){
            e.printStackTrace();
        }



    }

}
