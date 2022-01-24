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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeleteBookCommandTest {

    @Mock
    BookDAOImpl bookDAO;

    @InjectMocks
    DeleteBookCommand deleteBookCommand = new DeleteBookCommand();

    @Test
    public void deleteBook_and_return_pageWithBookList() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        Book book = BookFactory.createBook();

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("ADMIN");
        when(request.getParameter("id")).thenReturn(String.valueOf(book.getId()));
        when(bookDAO.get(anyLong())).thenReturn(Optional.of(book));
        doNothing().when(bookDAO).delete(any());

        String forward = deleteBookCommand.execute(request, response);

        Assertions.assertEquals("/controller?command=viewAllBooks", forward);
    }

    @Test
    public void deleteBook_and_return_errorPage() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        Book emptyBook = null;

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("ADMIN");
        when(request.getParameter("id")).thenReturn(String.valueOf(10L));
        when(bookDAO.get(anyLong())).thenReturn(Optional.ofNullable(emptyBook));
        doNothing().when(bookDAO).delete(any());

        String forward = deleteBookCommand.execute(request, response);

        Assertions.assertEquals("templates/error.jsp", forward);
    }
}
