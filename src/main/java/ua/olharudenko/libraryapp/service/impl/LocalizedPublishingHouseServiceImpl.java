package ua.olharudenko.libraryapp.service.impl;

import ua.olharudenko.libraryapp.dao.LocalizedPublishingHouseDAOImpl;
import ua.olharudenko.libraryapp.dao.PublishingHouseDAOImpl;
import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.models.LocalizedPublishingHouse;
import ua.olharudenko.libraryapp.models.PublishingHouse;
import ua.olharudenko.libraryapp.service.LocalizedPublishingHouseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocalizedPublishingHouseServiceImpl implements LocalizedPublishingHouseService {

    LocalizedPublishingHouseDAOImpl localizedPublishingHouseDAO = new LocalizedPublishingHouseDAOImpl();
    PublishingHouseDAOImpl publishingHouseDAO = new PublishingHouseDAOImpl();
    PublishingHouseServiceImpl publishingHouseService = new PublishingHouseServiceImpl();

    @Override
    public List<LocalizedPublishingHouse> getAll() {
        List<LocalizedPublishingHouse> allPublishingHouse = new ArrayList<LocalizedPublishingHouse>();
        try {
            allPublishingHouse = localizedPublishingHouseDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allPublishingHouse;
    }

    @Override
    public Optional<LocalizedPublishingHouse> getBy(Long id, Locale locale){
        Optional<LocalizedPublishingHouse> localizedPublishingHouse = Optional.of(new LocalizedPublishingHouse());
        try {
            localizedPublishingHouse = localizedPublishingHouseDAO.get(id, locale);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return localizedPublishingHouse;
    }

    @Override
    public LocalizedPublishingHouse save(LocalizedPublishingHouse house, PublishingHouse publishingHouse){
        var savedPublishingHouse = publishingHouseDAO.save(publishingHouse);
        house.setPublishingHouseId(savedPublishingHouse.getId());
        var savedLocalizedPublishingHouse = localizedPublishingHouseDAO.save(house);
        return savedLocalizedPublishingHouse;
    }

    @Override
    public void remove(LocalizedPublishingHouse house){
        try {
            Optional<PublishingHouse> publishingHouse = publishingHouseDAO.get(house.getPublishingHouseId());
            if(publishingHouse.isPresent()) {
                publishingHouseService.remove(publishingHouse.get());
            }
            // todo found - is other locales in PublishingHouse
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
