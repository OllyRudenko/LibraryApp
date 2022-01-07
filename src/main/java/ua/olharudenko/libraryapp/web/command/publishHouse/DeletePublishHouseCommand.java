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
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        var role = Role.valueOf(request.getParameter("userRole"));
        var id = Long.valueOf(request.getParameter("id"));
        var locale = Locale.valueOf(request.getParameter("local"));

        String errorMessage = "DELETING FAILED";
        var forward = "templates/error.jsp";

        if (role.equals(Role.ADMIN) || role.equals(Role.LIBRARIAN)) {
            Optional<LocalizedPublishingHouse> houseForDelete = new LocalizedPublishingHouseServiceImpl().getBy(id, locale);
            if (!houseForDelete.isEmpty()) {
                new LocalizedPublishingHouseServiceImpl().remove(houseForDelete.get());
                request.getSession().setAttribute("userRole", role);
                forward = "/controller?command=viewAllPublishHouses&role=".concat(role.name());
            }
        }
        return forward;
    }
}
