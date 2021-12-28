package ua.olharudenko.libraryapp.dao;

import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.models.Author;
import ua.olharudenko.libraryapp.models.LocalizedAuthor;
import ua.olharudenko.libraryapp.utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocalizedAuthorDAOImpl implements LocalizedModelDAO<LocalizedAuthor> {

    @Override
    public Optional<LocalizedAuthor> get(long id, Locale locale) throws SQLException {
        LocalizedAuthor localizedAuthor = null;
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet resultSet = null;
        String sql = "select * from localized_authors  where author_id = ? and locale = ?";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            pstatement.setLong(1, id);
            pstatement.setString(2, locale.toString());
            if (pstatement.execute()) {
                resultSet = pstatement.executeQuery();
                while (resultSet.next()) {
                    localizedAuthor = new LocalizedAuthor();
                    localizedAuthor.setAuthorId(resultSet.getLong("author_id"));
                    localizedAuthor.setFullName(resultSet.getString("full_name"));
                    localizedAuthor.setLocal(Locale.valueOf(resultSet.getString("locale")));
                    localizedAuthor.setBiografy(resultSet.getString("biografy"));
                    // todo add photo
                }
            }
        } catch (SQLException e) {
            throw new SQLException("LOCALIZED AUTHOR NOT FOUND BY ID", e);
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
        return Optional.ofNullable(localizedAuthor);
    }

    @Override
    public List<LocalizedAuthor> getAll() throws SQLException {
        var allLocalizedAuthors = new ArrayList<LocalizedAuthor>();
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet resultSet = null;
        String sql = "select * from localized_authors";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            resultSet = pstatement.executeQuery();
            while (resultSet.next()) {
                var localizedAuthor = new LocalizedAuthor();
                localizedAuthor.setAuthorId(resultSet.getLong("author_id"));
                localizedAuthor.setFullName(resultSet.getString("full_name"));
                localizedAuthor.setLocal(Locale.valueOf(resultSet.getString("locale")));
                localizedAuthor.setBiografy(resultSet.getString("biografy"));
                allLocalizedAuthors.add(localizedAuthor);
            }
        } catch (SQLException e) {
            throw new SQLException("LOCALIZED AUTHOR NOT FOUND", e);
        } finally {
            try {
                resultSet.close();
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("findAll(): ResultSet or PreparedStatement didn't close", e);
            }
        }
        if (allLocalizedAuthors.size() == 0) {
            throw new SQLException("table LOCALIZED_AUTHORS is empty");
        }
        return allLocalizedAuthors;
    }

    @Override
    public LocalizedAuthor save(LocalizedAuthor localizedAuthor) {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sql = "insert into localized_authors(author_id, full_name, locale, biografy) values (?, ?, ?, ?)";
        var author = new AuthorDAOImpl().save(new Author(localizedAuthor.getFullName(), null));
        localizedAuthor.setAuthorId(author.getId());
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);

            pstatement.setLong(1, localizedAuthor.getAuthorId());
            pstatement.setString(2, localizedAuthor.getFullName());
            pstatement.setString(3, localizedAuthor.getLocal().toString());
            pstatement.setString(4, localizedAuthor.getBiografy());

            if (pstatement.executeUpdate() == 0) {
                throw new SQLException("Adding localized_author to database failed, no rows affected.");
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
        return localizedAuthor;
    }

    @Override
    public void update(LocalizedAuthor localizedAuthor) throws SQLException {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sql = "UPDATE localized_authors SET full_name=?, biografy=? WHERE author_id = ? and locale = ?";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            if (this.get(localizedAuthor.getAuthorId(), localizedAuthor.getLocal()) != null) {
                pstatement.setLong(3, localizedAuthor.getAuthorId());
                pstatement.setString(4, localizedAuthor.getLocal().toString());
                pstatement.setString(1, localizedAuthor.getFullName());
                pstatement.setString(2, localizedAuthor.getBiografy());
                pstatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException("LOCALIZED_AUTHOR DIDN'T UPDATE");
        } finally {
            try {
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("update: PreparedStatement didn't close", e);
            }
        }
    }

    @Override
    public void delete(LocalizedAuthor localizedAuthor) throws SQLException {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sql = "delete from localized_authors where author_id = ? and locale = ?";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            if (this.get(localizedAuthor.getAuthorId(), localizedAuthor.getLocal()) != null) {
                pstatement.setLong(1, localizedAuthor.getAuthorId());
                pstatement.setString(2, localizedAuthor.getLocal().toString());
                pstatement.executeUpdate();
            } else {
                throw new SQLException("LOCALIZED AUTHOR DIDN'T DELETE");
            }
        } catch (SQLException e) {
            throw new SQLException("LOCALIZED AUTHOR DIDN'T DELETE");
        } finally {
            try {
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("delete: PreparedStatement didn't close", e);
            }
        }
    }
}
