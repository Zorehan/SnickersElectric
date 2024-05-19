package DAL;

import java.sql.Connection;
import java.util.List;

/*
GenericDAO er et generic interface som vi har lavet for at skabe mere overblik i programmet ved at have et interface som kan
benyttes til mere end bare en klasse. Dette gør vi ved at oprette vores metoder med en placeholderklasse "T" istedet for en specifik form for objekt.
 */

public interface GenericDAO<T> {

    default Connection getConnection() {
        return new DatabaseConnector().getConnection();
    }

    List<T> getAll();

    T create(T entity);

    void delete(T entity);

    T update(T entity);
}
