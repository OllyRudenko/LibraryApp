package ua.olharudenko.libraryapp.dao;

import ua.olharudenko.libraryapp.models.Author;
import ua.olharudenko.libraryapp.utils.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorDAOImpl implements ModelDAO<Author> {

    @Override
    public Optional<Author> get(long id) throws SQLException {
        Author author = null;
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet resultSet = null;
        String sql = "select * from authors  where id = ?";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            pstatement.setLong(1, id);
            if (pstatement.execute()) {
                resultSet = pstatement.executeQuery();
                while (resultSet.next()) {
                    author = new Author();
                    author.setId(resultSet.getLong("id"));
                    author.setFullName(resultSet.getString("full_name"));
//                    author.setPhoto(resultSet.getInt("file_reference_id")); todo create FileReference
                }
            }
        } catch (SQLException e) {
            throw new SQLException("AUTHOR NOT FOUND BY ID", e);
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
        return Optional.ofNullable(author);
    }

    @Override
    public List<Author> getAll() throws SQLException {
        List<Author> allAuthors = new ArrayList<Author>();
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet resultSet = null;
        String sql = "select * from authors";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            resultSet = pstatement.executeQuery();
            while (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getLong("id"));
                author.setFullName(resultSet.getString("full_name"));
//                    author.setPhoto(resultSet.getInt("file_reference_id")); todo create FileReference
                allAuthors.add(author);
            }
        } catch (SQLException e) {
            throw new SQLException("AUTHORS NOT FOUND", e);
        } finally {
            try {
                resultSet.close();
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("findAll(): ResultSet or PreparedStatement didn't close", e);
            }
        }
        if (allAuthors.size() == 0) {
            throw new SQLException("table AUTHORS is empty");
        }
        return allAuthors;
    }

    @Override
    public Author save(Author author) {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sql = "insert into authors(full_name) values (?)";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstatement.setString(1, author.getFullName());

            if (pstatement.executeUpdate() == 0) {
                throw new SQLException("Adding author to database failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    author.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Adding author to database failed, no ID obtained");
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
        return author;
    }

    @Override
    public void update(Author author) throws SQLException {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sql = "UPDATE authors SET full_name=? WHERE id =?";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            if (get(author.getId()) != null) {
                pstatement.setLong(2, author.getId());
                pstatement.setString(1, author.getFullName());
                pstatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException("AUTHOR DIDN'T UPDATE");
        } finally {
            try {
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("update: PreparedStatement didn't close", e);
            }
        }
    }

    @Override
    public void delete(Author author) throws SQLException {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sql = "delete from authors where id = ?";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            if (get(author.getId()) != null) {
                pstatement.setLong(1, author.getId());
                pstatement.executeUpdate();
            } else {
                throw new SQLException("AUTHOR DIDN'T DELETE");
            }
        } catch (SQLException e) {
            throw new SQLException("AUTHOR DIDN'T DELETE");
        } finally {
            try {
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("delete: PreparedStatement didn't close", e);
            }
        }
    }
}
