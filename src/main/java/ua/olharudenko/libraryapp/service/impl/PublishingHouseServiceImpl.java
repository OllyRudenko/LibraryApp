package ua.olharudenko.libraryapp.service.impl;

import ua.olharudenko.libraryapp.dao.PublishingHouseDAOImpl;
import ua.olharudenko.libraryapp.models.PublishingHouse;
import ua.olharudenko.libraryapp.service.PublishingHouseService;

import java.sql.SQLException;

public class PublishingHouseServiceImpl implements PublishingHouseService {
    PublishingHouseDAOImpl publishingHouseDAO = new PublishingHouseDAOImpl();

    @Override
    public void remove(PublishingHouse publishingHouse) {
        try {
            if (publishingHouseDAO.get(publishingHouse.getId()).isPresent()) {
                publishingHouseDAO.delete(publishingHouse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
