package ua.olharudenko.libraryapp.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ModelConDAO<T> {

    Optional<T> get(long id) throws SQLException;

    List<T> getAll() throws SQLException;

    T save(T t);

    void update(T t) throws SQLException;

    void delete(T t) throws SQLException;
}
