package ua.olharudenko.libraryapp.web.command.publishHouse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.LocalizedPublishingHouse;
import ua.olharudenko.libraryapp.service.impl.LocalizedPublishingHouseServiceImpl;
import ua.olharudenko.libraryapp.web.command.Command;
import ua.olharudenko.libraryapp.web.command.book.ViewBookProfileCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ViewHouseProfileCommand extends Command {
    private final Logger logger = LogManager.getLogger(ViewBookProfileCommand.class);

    LocalizedPublishingHouseServiceImpl localizedPublishingHouseService = new LocalizedPublishingHouseServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        var id = Long.valueOf(request.getParameter("id"));
        Role userRole = Role.valueOf((String) request.getSession().getAttribute("userRole"));
        var locale = Locale.valueOf(request.getParameter("locale"));

        String errorMessage = null;
        var forward = "templates/error.jsp";

        Optional<LocalizedPublishingHouse> localizedPublishingHouse = localizedPublishingHouseService.getBy(id, locale);

        if (localizedPublishingHouse.isEmpty()) {
            errorMessage = "Publishing House isn't exsist";
            request.getSession().setAttribute("errorMessage", errorMessage);
            logger.info("errorMessage: " + errorMessage);
            return forward;
        }

        request.getSession().setAttribute("publishing_house", localizedPublishingHouse.get());
        request.getSession().setAttribute("userRole", userRole.toString());
        if (userRole.equals(Role.VISITOR))
            forward = "templates/publish_house/publish_house_profile.jsp";
        if (userRole.equals(Role.ADMIN) || userRole.equals(Role.LIBRARIAN))
            forward = "templates/publish_house/publish_house_profile.jsp";

        return forward;
    }
}
