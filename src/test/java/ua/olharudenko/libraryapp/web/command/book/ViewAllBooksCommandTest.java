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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewAllBooksCommandTest {

    @Mock
    BookDAOImpl bookDAO;

    @InjectMocks
    ViewAllBooksCommand viewAllBooksCommand = new ViewAllBooksCommand();

    @Test
    public void getCommandViewAllBooks_and_return_pageWithBookListForAdmin() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        List<Book> books = List.of(BookFactory.createBook());

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("ADMIN");
        when(request.getParameter("userId")).thenReturn(String.valueOf(10L));
        when(bookDAO.getAll()).thenReturn(books);

        String forward = viewAllBooksCommand.execute(request, response);

        Assertions.assertEquals("templates/book/all_books_edit.jsp", forward);
    }

    @Test
    public void getCommandViewAllBooks_and_return_pageWithBookListForVisitor() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        List<Book> books = List.of(BookFactory.createBook());

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("VISITOR");
        when(request.getParameter("userId")).thenReturn(String.valueOf(10L));
        when(bookDAO.getAll()).thenReturn(books);

        String forward = viewAllBooksCommand.execute(request, response);

        Assertions.assertEquals("templates/book/all_books.jsp", forward);
    }

    @Test
    public void getCommandViewAllBooks_and_return_errorPage() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        List<Book> books = new ArrayList<>();

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("VISITOR");
        when(request.getParameter("userId")).thenReturn(String.valueOf(10L));
        when(bookDAO.getAll()).thenReturn(books);

        String forward = viewAllBooksCommand.execute(request, response);

        Assertions.assertEquals("templates/error.jsp", forward);
    }
}
