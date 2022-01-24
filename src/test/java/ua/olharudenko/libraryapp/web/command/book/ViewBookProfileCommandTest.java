package ua.olharudenko.libraryapp.web.command.book;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.olharudenko.libraryapp.dao.BookDAOImpl;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.testFactory.BookFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewBookProfileCommandTest {

    @Mock
    BookDAOImpl bookDAO;

    @InjectMocks
    ViewBookProfileCommand viewBookProfileCommand = new ViewBookProfileCommand();

    @Test
    public void viewBookProfile_and_return_pageWithBookForAdmin() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        Book book = BookFactory.createBook();

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("id")).thenReturn(String.valueOf(10L));
        when(session.getAttribute("userRole")).thenReturn("ADMIN");

        when(bookDAO.get(anyLong())).thenReturn(Optional.ofNullable(book));

        String forward = viewBookProfileCommand.execute(request, response);

        Assertions.assertEquals("templates/book/book_profile_edit.jsp", forward);
    }

    @Test
    public void viewBookProfile_and_return_pageWithBookForVisitor() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        Book book = BookFactory.createBook();

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("id")).thenReturn(String.valueOf(10L));
        when(session.getAttribute("userRole")).thenReturn("VISITOR");

        when(bookDAO.get(anyLong())).thenReturn(Optional.ofNullable(book));

        String forward = viewBookProfileCommand.execute(request, response);

        Assertions.assertEquals("templates/book/book_profile.jsp", forward);
    }

    @Test
    public void viewBookProfile_and_return_errorPage() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        Book emptyBook = null;

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("id")).thenReturn(String.valueOf(10L));
        when(session.getAttribute("userRole")).thenReturn("VISITOR");

        when(bookDAO.get(anyLong())).thenReturn(Optional.ofNullable(emptyBook));

        String forward = viewBookProfileCommand.execute(request, response);

        Assertions.assertEquals("templates/error.jsp", forward);
    }
}
