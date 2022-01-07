package ua.olharudenko.libraryapp.web.command.publishHouse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.dao.LocalizedAuthorDAOImpl;
import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.LocalizedAuthor;
import ua.olharudenko.libraryapp.models.LocalizedPublishingHouse;
import ua.olharudenko.libraryapp.models.PublishingHouse;
import ua.olharudenko.libraryapp.service.impl.LocalizedPublishingHouseServiceImpl;
import ua.olharudenko.libraryapp.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AddNewLocalizedPublishingHouseCommand extends Command {
    private final Logger logger = LogManager.getLogger(AddNewLocalizedPublishingHouseCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        var userRole = Role.valueOf(request.getParameter("userRole"));
        var city = request.getParameter("city");
        var adress = request.getParameter("adress");
        var nameHouse = request.getParameter("nameHouse");
        var locale = Locale.valueOf(request.getParameter("locale"));
        var email = request.getParameter("email");
        var phone = request.getParameter("phone");

        String errorMessage = null;
        var forward = "templates/error.jsp";

        var localizedPublishingHouse = new LocalizedPublishingHouse();
        var publishingHouse = new PublishingHouse(email, phone);

        if (userRole.equals(Role.ADMIN) || userRole.equals(Role.LIBRARIAN)) {
            localizedPublishingHouse = new LocalizedPublishingHouseServiceImpl().save(new LocalizedPublishingHouse(locale, city, adress, nameHouse), publishingHouse);
            if (localizedPublishingHouse == null) {
                errorMessage = "Cannot add author, please try againe";
                request.getSession().setAttribute("errorMessage", errorMessage);
                logger.info("errorMessage: " + errorMessage);
                return forward;
            }
        }
        request.getSession().setAttribute("localizedPublishingHouse", localizedPublishingHouse);
        request.getSession().setAttribute("userRole", userRole.toString());
        request.getSession().setAttribute("userLocale", Locale.EN.toString());
        forward = "templates/publish_house/all_publish_house_edit.jsp";

        return forward;
    }
}
