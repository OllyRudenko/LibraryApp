package ua.olharudenko.libraryapp.web.command.publishHouse;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.models.LocalizedPublishingHouse;
import ua.olharudenko.libraryapp.service.impl.LocalizedPublishingHouseServiceImpl;
import ua.olharudenko.libraryapp.testFactory.BookFactory;
import ua.olharudenko.libraryapp.testFactory.LocalizedPublishingHouseFactory;

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
public class DeletePublishHouseCommandTest {
    @Mock
    LocalizedPublishingHouseServiceImpl localizedPublishingHouseService;

    @InjectMocks
    DeletePublishHouseCommand deletePublishHouseCommand = new DeletePublishHouseCommand();

    @Test
    public void deletePublishingHouse_and_return_pageWithPublishingHouseList() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        LocalizedPublishingHouse localizedPublishingHouse = LocalizedPublishingHouseFactory.createLocalizedPublishingHouse();

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("ADMIN");
        when(request.getParameter("id")).thenReturn(String.valueOf(localizedPublishingHouse.getPublishingHouseId()));
        when(request.getParameter("local")).thenReturn(String.valueOf(localizedPublishingHouse.getLocale()));
        when(localizedPublishingHouseService.getBy(anyLong(), any())).thenReturn(Optional.of(localizedPublishingHouse));
        doNothing().when(localizedPublishingHouseService).remove(any());

        String forward = deletePublishHouseCommand.execute(request, response);

        Assertions.assertEquals("/controller?command=viewAllPublishHouses&role=ADMIN", forward);
    }

    @Test
    public void deletePublishingHouse_and_return_errorPage() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        LocalizedPublishingHouse localizedPublishingHouse = LocalizedPublishingHouseFactory.createLocalizedPublishingHouse();

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("ADMIN");
        when(request.getParameter("id")).thenReturn(String.valueOf(localizedPublishingHouse.getPublishingHouseId()));
        when(request.getParameter("local")).thenReturn(String.valueOf(localizedPublishingHouse.getLocale()));
        when(localizedPublishingHouseService.getBy(anyLong(), any())).thenReturn(Optional.ofNullable(null));
        doNothing().when(localizedPublishingHouseService).remove(any());

        String forward = deletePublishHouseCommand.execute(request, response);

        Assertions.assertEquals("templates/error.jsp", forward);
    }
}
