package ua.olharudenko.libraryapp.web.command.user;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.olharudenko.libraryapp.dao.UserDAOImpl;
import ua.olharudenko.libraryapp.models.User;
import ua.olharudenko.libraryapp.testFactory.UserFactory;

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
public class ViewAllUsersCommandTest {

    @Mock
    UserDAOImpl userDAO;

    @InjectMocks
    ViewAllUsersCommand viewAllUsersCommand = new ViewAllUsersCommand();

    @Test
    public void getCommandViewAllUsers_and_return_pageWithUserListForAdmin() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        List<User> users = List.of(UserFactory.createVisitor());

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("userRole")).thenReturn("ADMIN");
        when(userDAO.getAll()).thenReturn(users);

        String forward = viewAllUsersCommand.execute(request, response);

        Assertions.assertEquals("templates/user/all_users.jsp", forward);
    }

    @Test
    public void getCommandViewAllUsers_and_return_errorPageForAdmin() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        List<User> users = new ArrayList<>();

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("userRole")).thenReturn("ADMIN");
        when(userDAO.getAll()).thenReturn(users);

        String forward = viewAllUsersCommand.execute(request, response);

        Assertions.assertEquals("templates/error.jsp", forward);
    }

    @Test
    public void getCommandViewAllUsers_and_return_errorPage() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        List<User> users = List.of(UserFactory.createVisitor());

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("userRole")).thenReturn("VISITOR");
        when(userDAO.getAll()).thenReturn(users);

        String forward = viewAllUsersCommand.execute(request, response);

        Assertions.assertEquals("templates/error.jsp", forward);
    }
}
