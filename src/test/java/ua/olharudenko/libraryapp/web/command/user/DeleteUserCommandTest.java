package ua.olharudenko.libraryapp.web.command.user;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.olharudenko.libraryapp.dao.UserDAOImpl;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.models.User;
import ua.olharudenko.libraryapp.testFactory.BookFactory;
import ua.olharudenko.libraryapp.testFactory.UserFactory;

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
public class DeleteUserCommandTest {

    @Mock
    UserDAOImpl userDAO;

    @InjectMocks
    DeleteUserCommand deleteUserCommand = new DeleteUserCommand();

    @Test
    public void deleteUser_and_return_pageWithUserList() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        User user = UserFactory.createVisitor();

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("userRole")).thenReturn("ADMIN");
        when(request.getParameter("id")).thenReturn(String.valueOf(user.getId()));
        when(userDAO.get(anyLong())).thenReturn(Optional.of(user));
        doNothing().when(userDAO).delete(any());

        String forward = deleteUserCommand.execute(request, response);

        Assertions.assertEquals("/controller?command=viewAllUsers&role=ADMIN", forward);
    }

    @Test
    public void deleteUser_and_return_errorPage() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        User user = UserFactory.createVisitor();

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("userRole")).thenReturn("ADMIN");
        when(request.getParameter("id")).thenReturn(String.valueOf(user.getId()));
        when(userDAO.get(anyLong())).thenReturn(Optional.ofNullable(null));

        String forward = deleteUserCommand.execute(request, response);

        Assertions.assertEquals("templates/error.jsp", forward);
    }
}
