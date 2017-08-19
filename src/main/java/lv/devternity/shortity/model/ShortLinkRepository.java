package lv.devternity.shortity.model;

import org.springframework.data.cassandra.repository.CassandraRepository;

/**
 * Author: jurijsgrabovskis
 * Created at: 18/06/17.
 */
public interface ShortLinkRepository extends CassandraRepository<ShortLink> {

}

