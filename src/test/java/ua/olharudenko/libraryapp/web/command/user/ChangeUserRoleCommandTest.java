package ua.olharudenko.libraryapp.web.command.user;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.olharudenko.libraryapp.models.User;
import ua.olharudenko.libraryapp.service.impl.AdminServiceImpl;
import ua.olharudenko.libraryapp.testFactory.UserFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChangeUserRoleCommandTest {

    @Mock
    AdminServiceImpl adminService;

    @InjectMocks
    ChangeUserRoleCommand changeUserRoleCommand = new ChangeUserRoleCommand();

    @Test
    public void changeuserRole() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        String[] idForLibrarian = {"1", "2", "3"};
        String[] idForAdmin = {"4", "5", "6"};
        String[] idForVisitor = {"7", "8", "9"};
        List<User> librarians = List.of(UserFactory.createLibrarian());
        List<User> admins = List.of(UserFactory.createAdmin());
        List<User> visitors = List.of(UserFactory.createVisitor());

        when(request.getSession()).thenReturn(session);
        when(request.getParameterValues("idForLibrarian")).thenReturn(idForLibrarian);
        when(request.getParameterValues("idForAdmin")).thenReturn(idForAdmin);
        when(request.getParameterValues("idForVisitor")).thenReturn(idForVisitor);
        when(request.getSession().getAttribute("userId")).thenReturn(10L);
        when(request.getSession().getAttribute("userRole")).thenReturn("ADMIN");

        when(adminService.createLibrarian(any())).thenReturn(librarians);
        when(adminService.backVisitorRole(any())).thenReturn(visitors);
        when(adminService.confirmNewAdmin(any())).thenReturn(admins);

        String forward = changeUserRoleCommand.execute(request, response);

        Assertions.assertEquals("templates/user/all_users.jsp", forward);
    }
}
