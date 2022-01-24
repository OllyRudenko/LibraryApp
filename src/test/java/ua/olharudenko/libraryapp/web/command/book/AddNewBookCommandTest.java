package ua.olharudenko.libraryapp.web.command.book;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.olharudenko.libraryapp.dao.LocalizedAuthorDAOImpl;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.service.impl.BookServiceImpl;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddNewBookCommandTest {

    @Mock
    BookServiceImpl bookService;

    @Mock
    LocalizedAuthorDAOImpl localizedAuthorDAO;

    @InjectMocks
    AddNewBookCommand addNewBookCommand = new AddNewBookCommand();


    @Test
    public void addNewBook_and_return_Uri_BookProfileEdit() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        Book book = BookFactory.createBook();

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("ADMIN");
        when(request.getParameter("title")).thenReturn(book.getTitle());
        when(request.getParameter("publishLocale")).thenReturn(book.getPublishLocale().toString());
        when(request.getParameter("authorId")).thenReturn(book.getLocalizedAuthor().getAuthorId().toString());
        when(request.getParameter("issueDate")).thenReturn(String.valueOf(book.getIssueDate()));
        when(request.getParameter("description")).thenReturn(book.getDescription());
        when(request.getParameter("items")).thenReturn(String.valueOf(book.getItems()));
        when(request.getParameter("publishHouseId")).thenReturn(String.valueOf(book.getPublishHouseId()));
        when(localizedAuthorDAO.get(anyLong(), any())).thenReturn(Optional.ofNullable(book.getLocalizedAuthor()));
        when(bookService.save(any())).thenReturn(book);

        String forward = addNewBookCommand.execute(request, response);

        Assertions.assertEquals("templates/book/book_profile_edit.jsp", forward);
    }

    @Test
    public void addNewBook_and_back_not_validated_fields() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        Book book = BookFactory.createBook();

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("ADMIN");
        when(request.getParameter("title")).thenReturn("");
        when(request.getParameter("publishLocale")).thenReturn(book.getPublishLocale().toString());
        when(request.getParameter("authorId")).thenReturn(book.getLocalizedAuthor().getAuthorId().toString());
        when(request.getParameter("issueDate")).thenReturn(String.valueOf(book.getIssueDate()));
        when(request.getParameter("description")).thenReturn(book.getDescription());
        when(request.getParameter("items")).thenReturn(String.valueOf(book.getItems()));
        when(request.getParameter("publishHouseId")).thenReturn(String.valueOf(book.getPublishHouseId()));
        when(localizedAuthorDAO.get(anyLong(), any())).thenReturn(Optional.ofNullable(book.getLocalizedAuthor()));
        when(bookService.save(mock(Book.class))).thenReturn(book);

        String forward = addNewBookCommand.execute(request, response);

        Assertions.assertEquals("templates/book/all_books_edit.jsp", forward);
    }

    @Test
    public void addNewBook_and_back_errorPage() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        Book book = BookFactory.createBook();

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("ADMIN");
        when(request.getParameter("title")).thenReturn(book.getTitle());
        when(request.getParameter("publishLocale")).thenReturn(book.getPublishLocale().toString());
        when(request.getParameter("authorId")).thenReturn(book.getLocalizedAuthor().getAuthorId().toString());
        when(request.getParameter("issueDate")).thenReturn(String.valueOf(book.getIssueDate()));
        when(request.getParameter("description")).thenReturn(book.getDescription());
        when(request.getParameter("items")).thenReturn(String.valueOf(book.getItems()));
        when(request.getParameter("publishHouseId")).thenReturn(String.valueOf(book.getPublishHouseId()));
        when(localizedAuthorDAO.get(anyLong(), any())).thenReturn(Optional.ofNullable(book.getLocalizedAuthor()));
        when(bookService.save(mock(Book.class))).thenReturn(null);

        String forward = addNewBookCommand.execute(request, response);

        Assertions.assertEquals("templates/error.jsp", forward);
    }
}
