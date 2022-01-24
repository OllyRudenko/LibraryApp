package ua.olharudenko.libraryapp.web.command.author;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.olharudenko.libraryapp.dao.LocalizedAuthorDAOImpl;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.models.LocalizedAuthor;
import ua.olharudenko.libraryapp.service.impl.BookServiceImpl;
import ua.olharudenko.libraryapp.testFactory.BookFactory;
import ua.olharudenko.libraryapp.testFactory.LocalizedAuthorFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewAuthorProfileCommandTest {

    @Mock
    LocalizedAuthorDAOImpl localizedAuthorDAO;

    @Mock
    BookServiceImpl bookService;

    @InjectMocks
    ViewAuthorProfileCommand viewAuthorProfileCommand = new ViewAuthorProfileCommand();

    @Test
    public void viewBookProfile_and_return_pageWithBookForAdmin() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        LocalizedAuthor localizedAuthor = LocalizedAuthorFactory.createLocalizedAuthor();
        List<Book> books = List.of(BookFactory.createBook());

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("id")).thenReturn(String.valueOf(localizedAuthor.getAuthorId()));
        when(request.getParameter("locale")).thenReturn(String.valueOf(localizedAuthor.getLocal()));
        when(session.getAttribute("userId")).thenReturn(10L);
        when(session.getAttribute("userRole")).thenReturn("ADMIN");

        when(localizedAuthorDAO.get(anyLong(), any())).thenReturn(Optional.ofNullable(localizedAuthor));
        when(bookService.getAllBooksByAuthor(anyLong())).thenReturn(books);

        String forward = viewAuthorProfileCommand.execute(request, response);

        Assertions.assertEquals("templates/author/author_profile_edit.jsp", forward);
    }

    @Test
    public void viewBookProfile_and_return_pageWithBookForVisitor() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        LocalizedAuthor localizedAuthor = LocalizedAuthorFactory.createLocalizedAuthor();
        List<Book> books = List.of(BookFactory.createBook());

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("id")).thenReturn(String.valueOf(localizedAuthor.getAuthorId()));
        when(request.getParameter("locale")).thenReturn(String.valueOf(localizedAuthor.getLocal()));
        when(session.getAttribute("userId")).thenReturn(10L);
        when(session.getAttribute("userRole")).thenReturn("VISITOR");

        when(localizedAuthorDAO.get(anyLong(), any())).thenReturn(Optional.ofNullable(localizedAuthor));
        when(bookService.getAllBooksByAuthor(anyLong())).thenReturn(books);

        String forward = viewAuthorProfileCommand.execute(request, response);

        Assertions.assertEquals("templates/author/author_profile.jsp", forward);
    }

    @Test
    public void viewBookProfile_and_return_errorPage() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        LocalizedAuthor localizedAuthor = LocalizedAuthorFactory.createLocalizedAuthor();;

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("id")).thenReturn(String.valueOf(localizedAuthor.getAuthorId()));
        when(request.getParameter("locale")).thenReturn(String.valueOf(localizedAuthor.getLocal()));
        when(session.getAttribute("userId")).thenReturn(10L);
        when(session.getAttribute("userRole")).thenReturn("VISITOR");

        when(localizedAuthorDAO.get(anyLong(), any())).thenReturn(Optional.ofNullable(null));

        String forward = viewAuthorProfileCommand.execute(request, response);

        Assertions.assertEquals("templates/error.jsp", forward);
    }
}
