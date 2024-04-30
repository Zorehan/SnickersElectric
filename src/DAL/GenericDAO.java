package DAL;

import java.sql.Connection;
import java.util.List;

public interface GenericDAO<T> {

    default Connection getConnection() {
        return new DatabaseConnector().getConnection();
    }

    List<T> getAll();

    T create(T entity);

    void delete(T entity);

    T update(T entity);
}
