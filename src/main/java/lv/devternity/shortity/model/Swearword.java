package lv.devternity.shortity.model;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * Author: jurijsgrabovskis
 * Created at: 01/08/2017.
 */
@Table("swearwords")
public class Swearword {

    @PrimaryKey
    private String word;

    public String asText() {
        return word;
    }
}
