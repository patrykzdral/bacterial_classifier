package database.dao;

import java.sql.SQLException;
import java.util.List;

public interface EntityCRUD<T> {
    /**
     * <p>Method returning list of entities.</p>
     *
     * @return a {@link java.util.List} object.
     * @throws java.sql.SQLException if any.
     */
    List<T> getEntities() throws SQLException;

    /**
     * <p>Method saving entity in database.</p>
     *
     * @param entity a T object.
     * @return entity with id assigned by database.
     * @throws java.sql.SQLException if any.
     */
    T saveEntity(T entity) throws SQLException;

    /**
     * <p>Method updating entity in database.</p>
     *
     * @param entity a T object.
     * @return a {@link java.lang.Boolean} object.
     * @throws java.sql.SQLException if any.
     */
    T updateEntity(T entity) throws SQLException;

    /**
     * <p>Method returning entity with given id.</p>
     *
     * @param id a int.
     * @return a T object.
     * @throws java.sql.SQLException if any.
     */
    T getEntityById(int id) throws SQLException;

    /**
     * <p>Method deleting entity with given id.</p>
     *
     * @param id a int.
     * @return a {@link java.lang.Boolean} object.
     * @throws java.sql.SQLException if any.
     */
    Boolean deleteEntityById(int id) throws SQLException;
}

