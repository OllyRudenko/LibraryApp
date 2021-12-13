package ua.olharudenko.libraryapp.dao;

import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDAOImpl implements ModelDAO<Book> {

    // todo DAOException
    // todo add logs

    @Override
    public Optional<Book> get(long id) throws SQLException {
        Book book = null;
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet resultSet = null;
        String sql = "select * from books  where id = ?";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            pstatement.setLong(1, id);
            if (pstatement.execute()) {
                resultSet = pstatement.executeQuery();
                while (resultSet.next()) {
                    book = new Book();
                    book.setId(resultSet.getLong("id"));
                    book.setTitle(resultSet.getString("title"));
                    book.setAuthor(resultSet.getString("author"));
                    book.setIssuingOrganization(resultSet.getString("issuing_organization"));
                    book.setIssueDate(resultSet.getInt("issue_date"));
                    book.setOrdered(resultSet.getBoolean("ordered"));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("BOOK NOT FOUND BY ID", e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("get: ResultSet or PreparedStatement didn't close", e);
            }
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> getAll() throws SQLException {
        List<Book> allBooks = new ArrayList<Book>();
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet resultSet = null;
        String sql = "select * from books";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            resultSet = pstatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setIssuingOrganization(resultSet.getString("issuing_organization"));
                book.setIssueDate(resultSet.getInt("issue_date"));
                book.setOrdered(resultSet.getBoolean("ordered"));
                allBooks.add(book);
            }
        } catch (SQLException e) {
            throw new SQLException("BOOKS NOT FOUND", e);
        } finally {
            try {
                resultSet.close();
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("findAll(): ResultSet or PreparedStatement didn't close", e);
            }
        }
        if (allBooks.size() == 0) {
            throw new SQLException("table BOOKS is empty");
        }
        return allBooks;

    }

    @Override
    public Book save(Book book) {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sql = "insert into books(title, author, issuing_organization, issue_date, ordered) values (?, ?, ?, ?, ?)";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstatement.setString(1, book.getTitle());
            pstatement.setString(2, book.getAuthor());
            pstatement.setString(3, book.getIssuingOrganization());
            pstatement.setInt(4, book.getIssueDate());
            pstatement.setBoolean(5, book.isOrdered());

            if (pstatement.executeUpdate() == 0) {
                throw new SQLException("Adding book to database failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Adding book to database failed, no ID obtained");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return book;
    }

    @Override
    public void update(Book book) throws SQLException {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sql = "UPDATE books SET title=?, author=?, issuing_organization=?, issue_date=?, ordered=? WHERE id =?";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            if (get(book.getId()) != null) {
                pstatement.setLong(6, book.getId());
                pstatement.setString(1, book.getTitle());
                pstatement.setString(2, book.getAuthor());
                pstatement.setString(3, book.getIssuingOrganization());
                pstatement.setInt(4, book.getIssueDate());
                pstatement.setBoolean(5, book.isOrdered());
                pstatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException("BOOK DIDN'T UPDATE");
        } finally {
            try {
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("update: PreparedStatement didn't close", e);
            }
        }
    }

    @Override
    public void delete(Book book) throws SQLException {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sql = "delete from books where id = ?";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            if (get(book.getId()) != null) {
                pstatement.setLong(1, book.getId());
                pstatement.executeUpdate();
            } else {
                throw new SQLException("BOOK DIDN'T DELETE");
            }
        } catch (SQLException e) {
            throw new SQLException("BOOK DIDN'T DELETE");
        } finally {
            try {
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("delete: PreparedStatement didn't close", e);
            }
        }
    }
}
