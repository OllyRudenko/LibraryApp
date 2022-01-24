package ua.olharudenko.libraryapp.web.command.publishHouse;

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
import java.util.Optional;

public class DeletePublishHouseCommand extends Command {

    LocalizedPublishingHouseServiceImpl localizedPublishingHouseService = new LocalizedPublishingHouseServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        Role userRole = Role.valueOf((String) request.getSession().getAttribute("userRole"));
        var id = Long.valueOf(request.getParameter("id"));
        var locale = Locale.valueOf(request.getParameter("local"));

        String errorMessage = "DELETING FAILED";
        var forward = "templates/error.jsp";

        if (userRole.equals(Role.ADMIN) || userRole.equals(Role.LIBRARIAN)) {
            Optional<LocalizedPublishingHouse> houseForDelete = localizedPublishingHouseService.getBy(id, locale);
            if (!houseForDelete.isEmpty()) {
                localizedPublishingHouseService.remove(houseForDelete.get());
                request.getSession().setAttribute("userRole", userRole.toString());
                forward = "/controller?command=viewAllPublishHouses&role=".concat(userRole.name());
            }
        }
        return forward;
    }
}
