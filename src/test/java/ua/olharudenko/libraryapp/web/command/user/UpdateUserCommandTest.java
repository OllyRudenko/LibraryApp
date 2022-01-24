package ua.olharudenko.libraryapp.web.command.user;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.olharudenko.libraryapp.dao.UserDAOImpl;
import ua.olharudenko.libraryapp.models.LocalizedAuthor;
import ua.olharudenko.libraryapp.models.User;
import ua.olharudenko.libraryapp.testFactory.LocalizedAuthorFactory;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateUserCommandTest {
    @Mock
    UserDAOImpl userDAO;

    @InjectMocks
    UpdateUserCommand updateUserCommand = new UpdateUserCommand();

    @Test
    public void updateUser_and_return_pageWithAdminProfile() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        User admin = UserFactory.createAdmin();

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("id")).thenReturn(String.valueOf(admin.getId()));
        when(request.getParameter("firstName")).thenReturn(String.valueOf(admin.getFirstName()));
        when(request.getParameter("lastName")).thenReturn(String.valueOf(admin.getLastName()));
        when(request.getParameter("email")).thenReturn(String.valueOf(admin.getEmail()));
        when(request.getParameter("password")).thenReturn(String.valueOf(admin.getPassword()));
        when(request.getParameter("phone")).thenReturn(String.valueOf(admin.getPhone()));
        when(request.getParameter("adress")).thenReturn(String.valueOf(admin.getAdress()));
        when(request.getParameter("userRole")).thenReturn("ADMIN");

        doNothing().when(userDAO).update(any());
        when(userDAO.get(anyLong())).thenReturn(Optional.of(admin));

        String forward = updateUserCommand.execute(request, response);

        Assertions.assertEquals("templates/user/admin_profile.jsp", forward);
    }

    @Test
    public void updateUser_and_return_pageWithLibrarianProfile() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        User librarian = UserFactory.createLibrarian();

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("id")).thenReturn(String.valueOf(librarian.getId()));
        when(request.getParameter("firstName")).thenReturn(String.valueOf(librarian.getFirstName()));
        when(request.getParameter("lastName")).thenReturn(String.valueOf(librarian.getLastName()));
        when(request.getParameter("email")).thenReturn(String.valueOf(librarian.getEmail()));
        when(request.getParameter("password")).thenReturn(String.valueOf(librarian.getPassword()));
        when(request.getParameter("phone")).thenReturn(String.valueOf(librarian.getPhone()));
        when(request.getParameter("adress")).thenReturn(String.valueOf(librarian.getAdress()));
        when(request.getParameter("userRole")).thenReturn("LIBRARIAN");

        doNothing().when(userDAO).update(any());
        when(userDAO.get(anyLong())).thenReturn(Optional.of(librarian));

        String forward = updateUserCommand.execute(request, response);

        Assertions.assertEquals("templates/user/librarian_profile.jsp", forward);
    }

    @Test
    public void updateUser_and_return_pageWithVisitorProfile() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        User visitor = UserFactory.createVisitor();

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("id")).thenReturn(String.valueOf(visitor.getId()));
        when(request.getParameter("firstName")).thenReturn(String.valueOf(visitor.getFirstName()));
        when(request.getParameter("lastName")).thenReturn(String.valueOf(visitor.getLastName()));
        when(request.getParameter("email")).thenReturn(String.valueOf(visitor.getEmail()));
        when(request.getParameter("password")).thenReturn(String.valueOf(visitor.getPassword()));
        when(request.getParameter("phone")).thenReturn(String.valueOf(visitor.getPhone()));
        when(request.getParameter("adress")).thenReturn(String.valueOf(visitor.getAdress()));
        when(request.getParameter("userRole")).thenReturn("VISITOR");

        doNothing().when(userDAO).update(any());
        when(userDAO.get(anyLong())).thenReturn(Optional.of(visitor));

        String forward = updateUserCommand.execute(request, response);

        Assertions.assertEquals("templates/user/visitor_profile.jsp", forward);
    }

    @Test
    public void updateUser_and_return_errorPage() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        User visitor = UserFactory.createVisitor();

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("id")).thenReturn(String.valueOf(visitor.getId()));
        when(request.getParameter("firstName")).thenReturn(String.valueOf(visitor.getFirstName()));
        when(request.getParameter("lastName")).thenReturn(String.valueOf(visitor.getLastName()));
        when(request.getParameter("email")).thenReturn(String.valueOf(visitor.getEmail()));
        when(request.getParameter("password")).thenReturn(String.valueOf(visitor.getPassword()));
        when(request.getParameter("phone")).thenReturn(String.valueOf(visitor.getPhone()));
        when(request.getParameter("adress")).thenReturn(String.valueOf(visitor.getAdress()));
        when(request.getParameter("userRole")).thenReturn("VISITOR");

        doNothing().when(userDAO).update(any());
        when(userDAO.get(anyLong())).thenReturn(Optional.ofNullable(null));

        String forward = updateUserCommand.execute(request, response);

        Assertions.assertEquals("templates/error.jsp", forward);
    }
}
