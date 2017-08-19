package lv.devternity.shortity.model;

import com.datastax.driver.core.utils.UUIDs;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;
import java.util.UUID;

/**
 * Author: jurijsgrabovskis
 * Created at: 02/08/2017.
 */
@Table("Visit")
public class Visit {

    @PrimaryKey
    private UUID id;

    @JsonProperty("Visit time")
    private Date vtime;

    private String path;

    @JsonProperty("IP Address")
    private String ipAdress;

    @JsonProperty("Country")
    private String country;

    protected Visit() {
    }

    public Visit(String path, String ipAddress, String country) {
        this.id = UUIDs.random();
        this.vtime = new Date();
        this.path = path;
        this.ipAdress = ipAddress;
        this.country = country;
    }
}
