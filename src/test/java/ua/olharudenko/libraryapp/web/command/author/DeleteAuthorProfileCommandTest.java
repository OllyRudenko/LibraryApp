package ua.olharudenko.libraryapp.web.command.author;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.olharudenko.libraryapp.dao.LocalizedAuthorDAOImpl;
import ua.olharudenko.libraryapp.models.LocalizedAuthor;
import ua.olharudenko.libraryapp.testFactory.LocalizedAuthorFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeleteAuthorProfileCommandTest {
    @Mock
    LocalizedAuthorDAOImpl localizedAuthorDAO;

    @InjectMocks
    DeleteAuthorProfileCommand deleteAuthorProfileCommand = new DeleteAuthorProfileCommand();

    @Test
    public void deleteAuthor_and_return_pageWithAuthorList() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        LocalizedAuthor localizedAuthor = LocalizedAuthorFactory.createLocalizedAuthor();

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("ADMIN");
        when(request.getParameter("id")).thenReturn(String.valueOf(localizedAuthor.getAuthorId()));
        when(request.getParameter("locale")).thenReturn(String.valueOf(localizedAuthor.getLocal()));
        when(request.getParameter("userId")).thenReturn(String.valueOf(10L));
        when(localizedAuthorDAO.get(anyLong(), any())).thenReturn(Optional.of(localizedAuthor));
        doNothing().when(localizedAuthorDAO).delete(any());

        String forward = deleteAuthorProfileCommand.execute(request, response);

        Assertions.assertEquals("/controller?command=viewAllAuthors&role=ADMIN", forward);
    }

    @Test
    public void deleteAuthor_and_return_errorPage() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        LocalizedAuthor localizedAuthor = LocalizedAuthorFactory.createLocalizedAuthor();

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("VISITOR");
        when(request.getParameter("id")).thenReturn(String.valueOf(localizedAuthor.getAuthorId()));
        when(request.getParameter("locale")).thenReturn(String.valueOf(localizedAuthor.getLocal()));
        when(request.getParameter("userId")).thenReturn(String.valueOf(10L));
        when(localizedAuthorDAO.get(anyLong(), any())).thenReturn(Optional.of(localizedAuthor));
        doNothing().when(localizedAuthorDAO).delete(any());

        String forward = deleteAuthorProfileCommand.execute(request, response);

        Assertions.assertEquals("templates/error.jsp", forward);
    }
}
