package ua.olharudenko.libraryapp.web.command.author;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.olharudenko.libraryapp.dao.LocalizedAuthorDAOImpl;
import ua.olharudenko.libraryapp.models.Author;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.models.LocalizedAuthor;
import ua.olharudenko.libraryapp.testFactory.AuthorFactory;
import ua.olharudenko.libraryapp.testFactory.BookFactory;
import ua.olharudenko.libraryapp.testFactory.LocalizedAuthorFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewAllAuthorsCommandTest {

    @Mock
    LocalizedAuthorDAOImpl localizedAuthorDAO;

    @InjectMocks
    ViewAllAuthorsCommand viewAllAuthorsCommand = new ViewAllAuthorsCommand();

    @Test
    public void getCommandViewAllBooks_and_return_pageWithAuthorListForAdmin() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        List<LocalizedAuthor> authors = List.of(LocalizedAuthorFactory.createLocalizedAuthor());

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("ADMIN");
        when(request.getParameter("userId")).thenReturn(String.valueOf(10L));
        when(localizedAuthorDAO.getAll()).thenReturn(authors);

        String forward = viewAllAuthorsCommand.execute(request, response);

        Assertions.assertEquals("templates/author/all_authors_edit.jsp", forward);
    }

    @Test
    public void getCommandViewAllBooks_and_return_pageWithAuthorListForVisitor() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        List<LocalizedAuthor> authors = List.of(LocalizedAuthorFactory.createLocalizedAuthor());

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("VISITOR");
        when(request.getParameter("userId")).thenReturn(String.valueOf(10L));
        when(localizedAuthorDAO.getAll()).thenReturn(authors);

        String forward = viewAllAuthorsCommand.execute(request, response);

        Assertions.assertEquals("templates/author/all_authors.jsp", forward);
    }

    @Test
    public void getCommandViewAllBooks_and_return_errorPage() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        List<LocalizedAuthor> authors = new ArrayList<>();

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("VISITOR");
        when(request.getParameter("userId")).thenReturn(String.valueOf(10L));
        when(localizedAuthorDAO.getAll()).thenReturn(authors);

        String forward = viewAllAuthorsCommand.execute(request, response);

        Assertions.assertEquals("templates/error.jsp", forward);
    }
}
