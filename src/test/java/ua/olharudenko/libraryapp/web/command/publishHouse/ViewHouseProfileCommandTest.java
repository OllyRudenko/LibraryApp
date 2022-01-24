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
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewHouseProfileCommandTest {

    @Mock
    LocalizedPublishingHouseServiceImpl localizedPublishingHouseService;

    @InjectMocks
    ViewHouseProfileCommand viewHouseProfileCommand = new ViewHouseProfileCommand();

    @Test
    public void viewLocalizedPublishingHouseProfile_and_return_pageWithLocalizedPublishingHouseForAdmin() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        LocalizedPublishingHouse localizedPublishingHouse = LocalizedPublishingHouseFactory.createLocalizedPublishingHouse();

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("id")).thenReturn(String.valueOf(10L));
        when(session.getAttribute("userRole")).thenReturn("ADMIN");
        when(request.getParameter("locale")).thenReturn(localizedPublishingHouse.getLocale().toString());

        when(localizedPublishingHouseService.getBy(anyLong(), any())).thenReturn(Optional.ofNullable(localizedPublishingHouse));

        String forward = viewHouseProfileCommand.execute(request, response);

        Assertions.assertEquals("templates/publish_house/publish_house_profile.jsp", forward);
    }

    @Test
    public void viewLocalizedPublishingHouseProfile_and_return_errorPage() throws SQLException, ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        LocalizedPublishingHouse localizedPublishingHouse = LocalizedPublishingHouseFactory.createLocalizedPublishingHouse();

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("id")).thenReturn(String.valueOf(10L));
        when(session.getAttribute("userRole")).thenReturn("ADMIN");
        when(request.getParameter("locale")).thenReturn(localizedPublishingHouse.getLocale().toString());

        when(localizedPublishingHouseService.getBy(anyLong(), any())).thenReturn(Optional.ofNullable(null));

        String forward = viewHouseProfileCommand.execute(request, response);

        Assertions.assertEquals("templates/error.jsp", forward);
    }
}
