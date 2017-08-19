package lv.devternity.shortity.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/**
 * Author: jurijsgrabovskis
 * Created at: 18/06/17.
 */
@Configuration
@PropertySource("application.properties")
@EnableCassandraRepositories(basePackages = "lv.shortlink.model")
public class CassandraConfig extends AbstractCassandraConfiguration {

    private String cassandraHost = "localhost";

    @Override
    protected String getKeyspaceName() {
        return "dev";
    }

    @Bean
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster =
            new CassandraClusterFactoryBean();
        cluster.setContactPoints(cassandraHost);
        cluster.setPort(9042);
        return cluster;
    }

    @Bean
    public CassandraMappingContext cassandraMapping() throws ClassNotFoundException {
        return new BasicCassandraMappingContext();
    }

    @Value("${cassandra.host}")
    public void setCassandraHost(String cassandraHost) {
        this.cassandraHost = cassandraHost;
    }
}