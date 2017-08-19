package lv.devternity.shortity.model;

import org.springframework.data.cassandra.repository.CassandraRepository;

/**
 * Author: jurijsgrabovskis
 * Created at: 01/08/2017.
 */
public interface SwearwordRepository extends CassandraRepository<Swearword> {
}
