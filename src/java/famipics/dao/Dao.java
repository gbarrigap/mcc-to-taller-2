package famipics.dao;

import java.util.List;

/**
 * Define el comportamiento base de los objetos con persistencia del programa.
 * @param <T>
 */
public interface Dao<T> {

    public void create(T t) throws RepositoryConnectionException, UniqueConstraintException;

    public T retrieve(int id) throws RepositoryConnectionException, RecordNotFoundException;

    public void update(T t) throws RepositoryConnectionException, RecordNotFoundException, UniqueConstraintException;

    public void delete(T t) throws RepositoryConnectionException, RecordNotFoundException;

    public List<T> retrieveAll() throws RepositoryConnectionException;

}
