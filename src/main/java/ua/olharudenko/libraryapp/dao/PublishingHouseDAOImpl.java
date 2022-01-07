package ua.olharudenko.libraryapp.dao;

import ua.olharudenko.libraryapp.models.PublishingHouse;
import ua.olharudenko.libraryapp.utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PublishingHouseDAOImpl implements ModelDAO<PublishingHouse> {
    @Override
    public Optional<PublishingHouse> get(long id) throws SQLException {
        PublishingHouse publishingHouse = null;
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet resultSet = null;
        String sql = "select * from publishing_houses where id = ?";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            pstatement.setLong(1, id);
            if (pstatement.execute()) {
                resultSet = pstatement.executeQuery();
                while (resultSet.next()) {
                    publishingHouse = new PublishingHouse();
                    publishingHouse.setId(resultSet.getLong("id"));
                    publishingHouse.setEmail(resultSet.getString("email"));
                    publishingHouse.setPhone(resultSet.getString("phone"));
//                    publishingHouse.setPhoto(resultSet.getInt("file_reference_id")); todo create FileReference
                }
            }
        } catch (SQLException e) {
            throw new SQLException("PUBLISHING HOUSE NOT FOUND BY ID", e);
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
        return Optional.ofNullable(publishingHouse);
    }

    @Override
    public List<PublishingHouse> getAll() throws SQLException {
        var allPublishingHouses = new ArrayList<PublishingHouse>();
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet resultSet = null;
        String sql = "select * from publishing_houses";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            resultSet = pstatement.executeQuery();
            while (resultSet.next()) {
                var publishingHouse = new PublishingHouse();
                publishingHouse.setId(resultSet.getLong("id"));
                publishingHouse.setEmail(resultSet.getString("email"));
                publishingHouse.setPhone(resultSet.getString("phone"));
//                    publishingHouse.setPhoto(resultSet.getInt("file_reference_id")); todo create FileReference
                allPublishingHouses.add(publishingHouse);
            }
        } catch (SQLException e) {
            throw new SQLException("PUBLISHING HOUSES NOT FOUND", e);
        } finally {
            try {
                resultSet.close();
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("findAll(): ResultSet or PreparedStatement didn't close", e);
            }
        }
        if (allPublishingHouses.size() == 0) {
            throw new SQLException("table PUBLISHING_HOUSES is empty");
        }
        return allPublishingHouses;
    }

    @Override
    public PublishingHouse save(PublishingHouse publishingHouse) {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sql = "insert into publishing_houses(email, phone) values (?, ?)";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstatement.setString(1, publishingHouse.getEmail());
            pstatement.setString(2, publishingHouse.getPhone());

            if (pstatement.executeUpdate() == 0) {
                throw new SQLException("Adding publishing_house to database failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    publishingHouse.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Adding publishing house to database failed, no ID obtained");
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
        return publishingHouse;
    }

    @Override
    public void update(PublishingHouse publishingHouse) throws SQLException {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sql = "UPDATE publishing_houses SET email=?, phone=? WHERE id =?";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            if (get(publishingHouse.getId()) != null) {
                pstatement.setLong(3, publishingHouse.getId());
                pstatement.setString(1, publishingHouse.getEmail());
                pstatement.setString(2, publishingHouse.getPhone());
                pstatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException("PUBLISHING_HOUSE DIDN'T UPDATE");
        } finally {
            try {
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("update: PreparedStatement didn't close", e);
            }
        }
    }

    @Override
    public void delete(PublishingHouse publishingHouse) throws SQLException {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sql = "delete from publishing_houses where id = ?";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            if (get(publishingHouse.getId()) != null) {
                pstatement.setLong(1, publishingHouse.getId());
                pstatement.executeUpdate();
            } else {
                throw new SQLException("PUBLISHING_HOUSE DIDN'T DELETE");
            }
        } catch (SQLException e) {
            throw new SQLException("PUBLISHING_HOUSE DIDN'T DELETE");
        } finally {
            try {
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("delete: PreparedStatement didn't close", e);
            }
        }
    }
}
