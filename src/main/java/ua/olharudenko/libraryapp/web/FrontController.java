package ua.olharudenko.libraryapp.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.web.command.Command;
import ua.olharudenko.libraryapp.web.command.CommandContainer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class FrontController extends HttpServlet {

    private final Logger logger = LogManager.getLogger(FrontController.class);

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        logger.info("in FrontController / doGet()");
        try {
            process(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        logger.info("in FrontController / doPost()");
        try {
            process(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method
     */
    private void process(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException, SQLException {

        logger.info("in FrontController.process");

        String commandName = request.getParameter("command");
        logger.info("extract command from request: name = " + commandName);

        Command command = CommandContainer.get(commandName);
        logger.info("get command from CommandContainer: name = " + command);

        String forward = command.execute(request, response);
        logger.info("execute command and go to forward address: " + forward);

        if (forward != null) {
            RequestDispatcher disp = request.getRequestDispatcher(forward);
            disp.forward(request, response);
            logger.info("forward address is not null: " + forward);
        }
    }

}
