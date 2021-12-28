package ua.olharudenko.libraryapp.dao;

import ua.olharudenko.libraryapp.enums.Locale;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface LocalizedModelDAO<T> {

    Optional<T> get(long id, Locale locale) throws SQLException;

    List<T> getAll() throws SQLException;

    T save(T t);

    void update(T t) throws SQLException;

    void delete(T t) throws SQLException;
}
