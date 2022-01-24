package ua.olharudenko.libraryapp.web.command.publishHouse;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.olharudenko.libraryapp.models.LocalizedPublishingHouse;
import ua.olharudenko.libraryapp.service.impl.LocalizedPublishingHouseServiceImpl;
import ua.olharudenko.libraryapp.testFactory.LocalizedPublishingHouseFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddNewLocalizedPublishingHouseCommandTest {
    @Mock
    LocalizedPublishingHouseServiceImpl localizedPublishingHouseService;

    @InjectMocks
    AddNewLocalizedPublishingHouseCommand addNewLocalizedPublishingHouseCommand = new AddNewLocalizedPublishingHouseCommand();

    @Test
    public void addNewPublishingHouse_and_return_Uri_pageWithPublishingHouseList() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        LocalizedPublishingHouse savedLocalizedPublishingHouse = LocalizedPublishingHouseFactory.createLocalizedPublishingHouse();

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("ADMIN");
        when(request.getParameter("city")).thenReturn(savedLocalizedPublishingHouse.getCity());
        when(request.getParameter("adress")).thenReturn(savedLocalizedPublishingHouse.getAdress());
        when(request.getParameter("nameHouse")).thenReturn(savedLocalizedPublishingHouse.getNameHouse());
        when(request.getParameter("locale")).thenReturn(String.valueOf(savedLocalizedPublishingHouse.getLocale()));
        when(request.getParameter("email")).thenReturn("admin@gmail.com");
        when(request.getParameter("phone")).thenReturn("380996778989");
        when(localizedPublishingHouseService.save(any(), any())).thenReturn(savedLocalizedPublishingHouse);

        String forward = addNewLocalizedPublishingHouseCommand.execute(request, response);

        Assertions.assertEquals("templates/publish_house/all_publish_house_edit.jsp", forward);
    }

    @Test
    public void addNewPublishingHouse_and_return_errorPage() throws ServletException, SQLException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        LocalizedPublishingHouse localizedPublishingHouse = LocalizedPublishingHouseFactory.createLocalizedPublishingHouse();

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("ADMIN");
        when(request.getParameter("city")).thenReturn(localizedPublishingHouse.getCity());
        when(request.getParameter("adress")).thenReturn(localizedPublishingHouse.getAdress());
        when(request.getParameter("nameHouse")).thenReturn(localizedPublishingHouse.getNameHouse());
        when(request.getParameter("locale")).thenReturn(String.valueOf(localizedPublishingHouse.getLocale()));
        when(request.getParameter("email")).thenReturn("admin@gmail.com");
        when(request.getParameter("phone")).thenReturn("380996778989");
        when(localizedPublishingHouseService.save(any(), any())).thenReturn(null);

        String forward = addNewLocalizedPublishingHouseCommand.execute(request, response);

        Assertions.assertEquals("templates/error.jsp", forward);
    }
}
