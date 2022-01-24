package ua.olharudenko.libraryapp.web.filter;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EncodingFilterTest {

    @InjectMocks
    private EncodingFilter loggingFilter = new EncodingFilter();

    @Test
    public void testDoFilter() throws IOException, ServletException {
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        ServletRequest request = mock(ServletRequest.class);
        ServletResponse response = mock(ServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getCharacterEncoding()).thenReturn("UTF-8");
        doNothing().when(request).setCharacterEncoding("UTF-8");

        loggingFilter.doFilter(httpRequest, response, filterChain);

       verify(filterChain, times(1)).doFilter(Mockito.eq(httpRequest), Mockito.eq(response));
    }
}
