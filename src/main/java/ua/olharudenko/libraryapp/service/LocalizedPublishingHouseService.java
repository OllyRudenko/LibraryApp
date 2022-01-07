package ua.olharudenko.libraryapp.service;

import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.models.LocalizedPublishingHouse;
import ua.olharudenko.libraryapp.models.PublishingHouse;

import java.util.List;
import java.util.Optional;

public interface LocalizedPublishingHouseService {

    public List<LocalizedPublishingHouse> getAll();

    public Optional<LocalizedPublishingHouse> getBy(Long id, Locale locale);

    public LocalizedPublishingHouse save(LocalizedPublishingHouse house, PublishingHouse publishingHouse);

    public void remove(LocalizedPublishingHouse house);
}
