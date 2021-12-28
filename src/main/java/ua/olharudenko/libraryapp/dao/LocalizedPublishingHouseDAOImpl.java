package ua.olharudenko.libraryapp.dao;

import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.models.LocalizedPublishingHouse;
import ua.olharudenko.libraryapp.utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocalizedPublishingHouseDAOImpl implements LocalizedModelDAO<LocalizedPublishingHouse> {
    @Override
    public Optional<LocalizedPublishingHouse> get(long id, Locale locale) throws SQLException {
        LocalizedPublishingHouse localizedPublishHouse = null;
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet resultSet = null;
        String sql = "select * from localized_publishing_houses  where author_id = ? and locale = ?";
        var publishHouse = new PublishingHouseDAOImpl().get(id);
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            pstatement.setLong(1, id);
            pstatement.setString(2, locale.toString());
            if (pstatement.execute()) {
                resultSet = pstatement.executeQuery();
                while (resultSet.next()) {
                    localizedPublishHouse = new LocalizedPublishingHouse();
                    localizedPublishHouse.setLocale(Locale.valueOf(resultSet.getString("locale")));
                    localizedPublishHouse.setPublishingHouseId(resultSet.getLong("publishing_house_id"));
                    localizedPublishHouse.setCity(resultSet.getString("city"));
                    localizedPublishHouse.setAdress(resultSet.getString("adress"));
                    localizedPublishHouse.setNameHouse(resultSet.getString("name_house"));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("LOCALIZED PUBLISHING_HOUSE NOT FOUND BY ID", e);
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
        return Optional.ofNullable(localizedPublishHouse);
    }

    @Override
    public List<LocalizedPublishingHouse> getAll() throws SQLException {
        var allLocalizedPublishHouses = new ArrayList<LocalizedPublishingHouse>();
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet resultSet = null;
        String sql = "select * from localized_publishing_houses";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);
            resultSet = pstatement.executeQuery();
            while (resultSet.next()) {
                var localizedPublishHouse = new LocalizedPublishingHouse();
                localizedPublishHouse.setLocale(Locale.valueOf(resultSet.getString("locale")));
                localizedPublishHouse.setPublishingHouseId(resultSet.getLong("publishing_house_id"));
                localizedPublishHouse.setCity(resultSet.getString("city"));
                localizedPublishHouse.setAdress(resultSet.getString("adress"));
                localizedPublishHouse.setNameHouse(resultSet.getString("name_house"));
                allLocalizedPublishHouses.add(localizedPublishHouse);
            }
        } catch (SQLException e) {
            throw new SQLException("LOCALIZED PUBLISHING_HOUSES NOT FOUND", e);
        } finally {
            try {
                resultSet.close();
                pstatement.close();
            } catch (SQLException e) {
                throw new SQLException("findAll(): ResultSet or PreparedStatement didn't close", e);
            }
        }
        if (allLocalizedPublishHouses.size() == 0) {
            throw new SQLException("table LOCALIZED_PUBLISHING_HOUSES is empty");
        }
        return allLocalizedPublishHouses;
    }

    @Override
    public LocalizedPublishingHouse save(LocalizedPublishingHouse localizedPublishingHouse) {
        Connection connection = null;
        PreparedStatement pstatement = null;
        String sql = "insert into localized_publishing_houses(locale, publishing_house_id, city, adress, name_house) values (?, ?, ?, ?, ?)";
        try {
            connection = DataBaseConnection.getInstance().getConn();
            pstatement = connection.prepareStatement(sql);

            pstatement.setString(1, localizedPublishingHouse.getLocale().toString());
            pstatement.setLong(2, localizedPublishingHouse.getPublishingHouseId());
            pstatement.setString(3, localizedPublishingHouse.getCity());
            pstatement.setString(4, localizedPublishingHouse.getAdress());
            pstatement.setString(5, localizedPublishingHouse.getNameHouse());

            if (pstatement.executeUpdate() == 0) {
                throw new SQLException("Adding localized_publishing_house to database failed, no rows affected.");
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
        return localizedPublishingHouse;
    }

    @Override
    public void update(LocalizedPublishingHouse localizedPublishingHouse) throws SQLException {

    }

    @Override
    public void delete(LocalizedPublishingHouse localizedPublishingHouse) throws SQLException {

    }
}
