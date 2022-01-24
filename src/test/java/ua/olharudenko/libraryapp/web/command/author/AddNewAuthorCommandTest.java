package ua.olharudenko.libraryapp.web.command.author;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.olharudenko.libraryapp.dao.AuthorDAOImpl;
import ua.olharudenko.libraryapp.dao.LocalizedAuthorDAOImpl;
import ua.olharudenko.libraryapp.models.Author;
import ua.olharudenko.libraryapp.models.LocalizedAuthor;
import ua.olharudenko.libraryapp.testFactory.AuthorFactory;
import ua.olharudenko.libraryapp.testFactory.LocalizedAuthorFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddNewAuthorCommandTest {

    @Mock
    LocalizedAuthorDAOImpl localizedAuthorDAO;

    @Mock
    AuthorDAOImpl authorDAO = mock(AuthorDAOImpl.class);

    @InjectMocks
    AddNewAuthorCommand addNewAuthorCommand = new AddNewAuthorCommand();

    @Test
    public void addNewLocalizedAuthor_and_return_Uri_AuthorProfileEdit() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        LocalizedAuthor localizedAuthor = LocalizedAuthorFactory.createLocalizedAuthor();
        Author author = AuthorFactory.createAuthor();

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("ADMIN");
        when(request.getParameter("fullName")).thenReturn("Name Surname");
        when(request.getParameter("biografy")).thenReturn("Biography");
        when(request.getParameter("local")).thenReturn("EN");
        when(localizedAuthorDAO.save(any())).thenReturn(localizedAuthor);
        when(authorDAO.save(any())).thenReturn(author);

        String forward = addNewAuthorCommand.execute(request, response);

        Assertions.assertEquals("templates/author/author_profile_edit.jsp", forward);
    }

    @Test
    public void addNewLocalizedAuthor_and_back_not_validated_fields() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        LocalizedAuthor localizedAuthor = LocalizedAuthorFactory.createLocalizedAuthor();
        Author author = AuthorFactory.createAuthor();

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("ADMIN");
        when(request.getParameter("fullName")).thenReturn("");
        when(request.getParameter("biografy")).thenReturn("Biography");
        when(request.getParameter("local")).thenReturn("EN");
        when(localizedAuthorDAO.save(mock(LocalizedAuthor.class))).thenReturn(localizedAuthor);
        when(authorDAO.save(any())).thenReturn(author);

        String forward = addNewAuthorCommand.execute(request, response);

        Assertions.assertEquals("templates/author/all_authors_edit.jsp", forward);
    }
}
