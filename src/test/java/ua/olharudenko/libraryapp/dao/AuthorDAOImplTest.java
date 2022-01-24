package ua.olharudenko.libraryapp.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.olharudenko.libraryapp.models.Author;
import ua.olharudenko.libraryapp.testFactory.AuthorFactory;
import ua.olharudenko.libraryapp.utils.ConnectionPool;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorDAOImplTest {
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @Mock
    DataSource dataSource;


    @InjectMocks
    private AuthorDAOImpl authorDAO = new AuthorDAOImpl();

    Author author = AuthorFactory.createAuthor();

    public AuthorDAOImplTest() throws NamingException {
    }

//    @Test
//    public void shouldReturnAuthorById() throws SQLException {
//        mock(ConnectionPool.class);
//        when(ConnectionPool.getInstance().getConn()).thenReturn(connection);
//        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
//        when(resultSet.getLong("id")).thenReturn(author.getId());
//        when(resultSet.getString("full_name")).thenReturn(author.getFullName());
//        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//
//        Author actualAuthor = authorDAO.get(author.getId()).get();
//        Assertions.assertEquals(author, actualAuthor);
//    }

}
