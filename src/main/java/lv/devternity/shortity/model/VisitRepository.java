package lv.devternity.shortity.model;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

/**
 * Author: jurijsgrabovskis
 * Created at: 02/08/2017.
 */
public interface VisitRepository extends CassandraRepository<Visit> {

    @Query("SELECT * FROM visit WHERE path=?0;")
    List<Visit> findByPath(String path);
}
