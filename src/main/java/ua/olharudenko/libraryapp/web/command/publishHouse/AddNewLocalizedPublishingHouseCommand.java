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
import java.util.HashMap;
import java.util.Map;

public class AddNewLocalizedPublishingHouseCommand extends Command {
    private final Logger logger = LogManager.getLogger(AddNewLocalizedPublishingHouseCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        Role userRole = Role.valueOf((String) request.getSession().getAttribute("userRole"));


        String errorMessage = null;
        var forward = "templates/error.jsp";

        var publishingHouse = new PublishingHouse();
        var localizedPublishingHouse = new LocalizedPublishingHouse();
        var savedLocalizedPublishingHouse = new LocalizedPublishingHouse();

        if (userRole.equals(Role.ADMIN) || userRole.equals(Role.LIBRARIAN)) {
            Map<String, String> errors = new HashMap<>();
            validationPublishingHouseFields(request, errors, publishingHouse, localizedPublishingHouse);

            if (!errors.isEmpty()) {
                request.setAttribute("lph", localizedPublishingHouse);
                request.setAttribute("ph", publishingHouse);
                request.setAttribute("errors", errors);
                forward = "templates/publish_house/all_publish_house_edit.jsp";
            } else {
                savedLocalizedPublishingHouse = new LocalizedPublishingHouseServiceImpl().save(localizedPublishingHouse, publishingHouse);
                if (localizedPublishingHouse == null) {
                    errorMessage = "Cannot add author, please try againe";
                    request.getSession().setAttribute("errorMessage", errorMessage);
                    logger.info("errorMessage: " + errorMessage);
                    return forward;
                }
                request.getSession().setAttribute("localizedPublishingHouse", savedLocalizedPublishingHouse);
                request.getSession().setAttribute("userRole", userRole.toString());
                request.getSession().setAttribute("userLocale", Locale.EN.toString());
                forward = "templates/publish_house/all_publish_house_edit.jsp";
            }
        }
        return forward;
    }

    private void validationPublishingHouseFields(HttpServletRequest request, Map<String, String> errors, PublishingHouse publishingHouse, LocalizedPublishingHouse localizedPublishingHouse){
        var city = request.getParameter("city");
        if (city.length() == 0) {
            errors.put("city", "City cannot be blank and should be less than 200 simbols");
        } else {
            localizedPublishingHouse.setCity(city);
        }

        var adress = request.getParameter("adress");
        if (adress.length() == 0) {
            errors.put("adress", "Adress cannot be blank and should be less than 300 simbols");
        } else {
            localizedPublishingHouse.setAdress(adress);
        }

        var nameHouse = request.getParameter("nameHouse");
        if (nameHouse.length() == 0) {
            errors.put("nameHouse", "Publishing House Name cannot be blank and should be less than 200 simbols");
        } else {
            localizedPublishingHouse.setNameHouse(nameHouse);
        }

        var locale = request.getParameter("locale");
        if (locale.length() == 0) {
            errors.put("locale", "Please, choose locale");
        } else {
            localizedPublishingHouse.setLocale(Locale.valueOf(locale));
        }

        var email = request.getParameter("email");
        if (email.length() == 0) {
            errors.put("email", "email cannot be blank");
        } else {
            publishingHouse.setEmail(email);
        }

        var phone = request.getParameter("phone");
        if (phone.length() == 0) {
            errors.put("phone", "phone cannot be blank");
        } else {
            publishingHouse.setPhone(phone);
        }
    }
}
