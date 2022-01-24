package ua.olharudenko.libraryapp.web.command.author;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.dao.LocalizedAuthorDAOImpl;
import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.models.LocalizedAuthor;
import ua.olharudenko.libraryapp.service.impl.BookServiceImpl;
import ua.olharudenko.libraryapp.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ViewAuthorProfileCommand extends Command {
    private final Logger logger = LogManager.getLogger(ViewAuthorProfileCommand.class);

    LocalizedAuthorDAOImpl localizedAuthorDAO = new LocalizedAuthorDAOImpl();

    BookServiceImpl bookService = new BookServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        HttpSession session = request.getSession();
        Long authorId = Long.valueOf(request.getParameter("id"));
        Locale local = Locale.valueOf(request.getParameter("locale"));
        Long userId = (Long) session.getAttribute("userId");
        Role userRole = Role.valueOf((String) session.getAttribute("userRole"));

        String errorMessage = null;
        String forward = "templates/error.jsp";

        Optional<LocalizedAuthor> author = localizedAuthorDAO.get(authorId, local);

        if (!author.isPresent()) {
            errorMessage = "Author isn't exsist";
            request.getSession().setAttribute("errorMessage", errorMessage);
            logger.info("errorMessage: " + errorMessage);
            return forward;
        }
        List<Book> books = bookService.getAllBooksByAuthor(author.get().getAuthorId());
        if(books.size() != 0){
            request.getSession().setAttribute("books", books);
        }
        request.getSession().setAttribute("author", author.get());
        request.getSession().setAttribute("userId", userId);
        request.getSession().setAttribute("userRole", userRole.toString());
        if(userRole.equals(Role.VISITOR)) {
            forward = "templates/author/author_profile.jsp";
        }else{
            forward = "templates/author/author_profile_edit.jsp";
        }

        return forward;
    }
}
