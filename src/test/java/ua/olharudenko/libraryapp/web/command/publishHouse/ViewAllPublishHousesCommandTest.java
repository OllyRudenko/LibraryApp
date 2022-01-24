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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewAllPublishHousesCommandTest {

    @Mock
    LocalizedPublishingHouseServiceImpl localizedPublishingHouseService;

    @InjectMocks
    ViewAllPublishHousesCommand viewAllPublishHousesCommand;

    @Test
    public void getCommandViewAllPublishingHouses_and_return_pageWithPublishingHouseListForAdmin() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        List<LocalizedPublishingHouse> houses = List.of(LocalizedPublishingHouseFactory.createLocalizedPublishingHouse());

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("userId")).thenReturn(String.valueOf(10L));
        when(request.getSession().getAttribute("userRole")).thenReturn("ADMIN");

        when(localizedPublishingHouseService.getAll()).thenReturn(houses);

        String forward = viewAllPublishHousesCommand.execute(request, response);

        Assertions.assertEquals("templates/publish_house/all_publish_house_edit.jsp", forward);
    }

    @Test
    public void getCommandViewAllPublishingHouses_and_return_pageWithPublishingHouseListForVisitor() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        List<LocalizedPublishingHouse> houses = List.of(LocalizedPublishingHouseFactory.createLocalizedPublishingHouse());

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("userId")).thenReturn(String.valueOf(10L));
        when(request.getSession().getAttribute("userRole")).thenReturn("VISITOR");

        when(localizedPublishingHouseService.getAll()).thenReturn(houses);

        String forward = viewAllPublishHousesCommand.execute(request, response);

        Assertions.assertEquals("templates/publish_house/all_publish_house.jsp", forward);
    }

    @Test
    public void getCommandViewAllPublishingHouses_and_return_errorPage() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        List<LocalizedPublishingHouse> houses = new ArrayList<>();

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("userId")).thenReturn(String.valueOf(10L));
        when(request.getSession().getAttribute("userRole")).thenReturn("VISITOR");

        when(localizedPublishingHouseService.getAll()).thenReturn(houses);

        String forward = viewAllPublishHousesCommand.execute(request, response);

        Assertions.assertEquals("templates/error.jsp", forward);
    }
}
