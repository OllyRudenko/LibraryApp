package ua.olharudenko.libraryapp.web.command.publishHouse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.LocalizedPublishingHouse;
import ua.olharudenko.libraryapp.service.impl.LocalizedPublishingHouseServiceImpl;
import ua.olharudenko.libraryapp.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ViewAllPublishHousesCommand extends Command {
    private final Logger logger = LogManager.getLogger(ViewAllPublishHousesCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        var userId = Long.valueOf(request.getParameter("userId"));
        var userRole = Role.valueOf(request.getParameter("userRole"));

        String errorMessage = null;
        var forward = "templates/error.jsp";

        List<LocalizedPublishingHouse> publishingHouses = new LocalizedPublishingHouseServiceImpl().getAll();

        if (publishingHouses.size() == 0) {
            errorMessage = "PublishingHouse List is empty";
            request.getSession().setAttribute("errorMessage", errorMessage);
            logger.info("errorMessage: " + errorMessage);
            return forward;
        } else {
            request.getSession().setAttribute("publishing_houses", publishingHouses);
            request.getSession().setAttribute("userRole", userRole.toString());
            request.getSession().setAttribute("userId", userId);
            request.getSession().setAttribute("userLocale", Locale.EN.toString());
            if (userRole == Role.ADMIN || userRole == Role.LIBRARIAN)
                forward = "templates/publish_house/all_publish_house_edit.jsp";
            else {
                forward = "templates/publish_house/all_publish_house.jsp";
            }
        }
        return forward;
    }
}
