package ua.olharudenko.libraryapp.web.command.book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.dao.BookDAOImpl;
import ua.olharudenko.libraryapp.dao.LocalizedAuthorDAOImpl;
import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.models.LocalizedAuthor;
import ua.olharudenko.libraryapp.service.BookService;
import ua.olharudenko.libraryapp.service.impl.BookServiceImpl;
import ua.olharudenko.libraryapp.web.command.Command;
import ua.olharudenko.libraryapp.web.command.author.AddNewAuthorCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class AddNewBookCommand extends Command {
    private final Logger logger = LogManager.getLogger(AddNewBookCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        var userRole = Role.valueOf((String) request.getSession().getAttribute("userRole"));

        request.getSession().setAttribute("userRole", userRole.toString());
        request.getSession().setAttribute("userLocale", Locale.EN.toString());

        String errorMessage = null;
        var forward = "templates/error.jsp";

        var book = new Book();
        var savedBook = new Book();

        if (userRole == Role.ADMIN || userRole == Role.LIBRARIAN) {
            Map<String, String> errors = new HashMap<>();
            validationBookFields(request, errors, book);

            if (!errors.isEmpty()) {
                request.setAttribute("book", book);
                request.setAttribute("errors", errors);
                forward = "templates/book/all_books_edit.jsp";
            } else {
                savedBook = new BookServiceImpl().save(book);
                if (book == null) {
                    errorMessage = "Cannot add book, please try againe";
                    request.getSession().setAttribute("errorMessage", errorMessage);
                    logger.info("errorMessage: " + errorMessage);
                    return forward;
                }
                request.getSession().setAttribute("book", savedBook);
                forward = "templates/book/book_profile_edit.jsp";
            }
        }

        return forward;
    }

    private void validationBookFields(HttpServletRequest request, Map<String, String> errors, Book book) throws SQLException {
        var title = request.getParameter("title");
        if (title.length() == 0) {
            errors.put("title", "Title cannot be blank and should be less than 200 simbols");
        } else {
            book.setTitle(title);
        }

        var publishLocale = request.getParameter("publishLocale");
        Locale locale = null;
        if (publishLocale == null || publishLocale.length() != 2) {
            errors.put("publishLocale", "PublishLocale cannot be blank");
        } else {
            locale = Locale.valueOf(publishLocale.toString());
            book.setPublishLocale(locale);
        }

        var authorId = request.getParameter("authorId");
        if (authorId == null || !isNumeric(authorId)) {
            errors.put("authorId", "field Author cannot be empty");
        } else {
            if (locale != null && authorId != null) {
                // todo Service if by locale not found - back default locale
                LocalizedAuthor author = new LocalizedAuthorDAOImpl().get(Long.parseLong(authorId), book.getPublishLocale()).get();
                book.setLocalizedAuthor(author);
            }
        }

        var issueDate = request.getParameter("issueDate");
        if (issueDate == null || !isNumeric(issueDate) || issueDate.length() != 4) {
            errors.put("issueDate", "field Issue Date cannot be empty, should be write year");
        } else {
            book.setIssueDate(Integer.valueOf(issueDate));
        }

        var description = request.getParameter("description");
        if (description.length() == 0 || description.length() > 200) {
            errors.put("description", "Description cannot be blank and should be less than 2000 simbols");
        } else {
            book.setDescription(description);
        }

        var items = request.getParameter("items");
        if (items == null || !isNumeric(items)) {
            errors.put("items", "field Items cannot be empty, should be write year");
        } else {
            book.setItems(Integer.parseInt(items));
        }

        var publishHouseId = request.getParameter("publishHouseId");
        if (publishHouseId == null || !isNumeric(publishHouseId)) {
            errors.put("publishHouseId", "field publishHouseId cannot be empty");
        } else {
            book.setPublishHouseId(Long.valueOf(publishHouseId));
        }
    }
}
