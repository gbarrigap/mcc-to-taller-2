package famipics.dao;

import java.util.List;

/**
 * Define el comportamiento base de los objetos con persistencia del programa.
 */
public interface Dao<T> {

    public void create(T t);

    public T retrieve(int id);

    public void update(T t);

    public void delete(T t);

    public List<T> retrieveAll();

}
