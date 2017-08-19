package lv.devternity.shortity.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lv.devternity.shortity.application.Preconditions;
import lv.devternity.shortity.utils.SwearwordFilter;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.support.BasicMapId;
import org.springframework.util.StringUtils;


/**
 * Author: jurijsgrabovskis
 * Created at: 18/06/17.
 */
@Table("Shortlink")
public class ShortLink {

    @PrimaryKey
    @JsonProperty("Path")
    private String path;

    @JsonProperty("Destination")
    private String destination;

    private ShortLink(String path, String destination) {
        this.path = path;
        this.destination = destination;
    }

    public String path() {
        return path;
    }

    public String destination() {
        return destination;
    }

    public static class Unchecked {

        private final Path uncheckedPath;
        private final Destination uncheckedDestination;

        public Unchecked(String uncheckedPath, String uncheckedDestination) {
            this.uncheckedPath = new Path(uncheckedPath);
            this.uncheckedDestination = new Destination(uncheckedDestination);
        }

        public ShortLink buildChecked(ShortLinkRepository shortLinkRepository, SwearwordFilter swearwordFilter) {

            Preconditions preconditions = new Preconditions(true);
            preconditions
                .whenNot(uncheckedDestination, ShortLink.Unchecked.Destination::isValid)
                .then("Destination URL seems to be empty or mailformed. Please correct.");
            preconditions
                .whenNot(uncheckedPath, ShortLink.Unchecked.Path::isValid)
                .then("Custom shortlink " + uncheckedPath.rawPath + " is mailformed. Only ciphers 0-9 and letters a-z / A-Z allowed, overall length should not exceed 8 symbols.");
            preconditions
                .whenNot(uncheckedPath, path -> path.unique(shortLinkRepository))
                .then("Provided custom shortlink " + uncheckedPath.rawPath + " is already registered in system. Please, try another one.");
            preconditions
                .whenNot(uncheckedPath, path -> path.abusive(swearwordFilter))
                .then("Provided custom shortlink " + uncheckedPath.rawPath + " looks abusive. Please, try another one.");

            preconditions.check();

            return new ShortLink(uncheckedPath.rawPath, uncheckedDestination.rawUrl);
        }

        public static class Path implements Uniqueness, Abusiveness {
            private String rawPath;

            Path(String rawPath) {
                this.rawPath = rawPath;
            }

            boolean isValid() {
                return !StringUtils.isEmpty(rawPath) && rawPath.length() <= 8 && rawPath.matches("^[a-zA-Z0-9]*$");
            }

            @Override
            public boolean unique(ShortLinkRepository shortLinkRepository) {
                MapId searchKey = BasicMapId.id("path", rawPath);
                return !shortLinkRepository.exists(searchKey);
            }

            @Override
            public boolean abusive(SwearwordFilter swearwordFilter) {
                return swearwordFilter.test(rawPath);
            }
        }

        static class Destination {

            private String rawUrl;

            Destination(String rawUrl) {
                this.rawUrl = rawUrl;
            }

            boolean isValid() {
                if (!StringUtils.isEmpty(rawUrl)) {
                    if (!hasProtocol()) {
                        rawUrl = "http://" + rawUrl;
                    }
                    return new UrlValidator().isValid(rawUrl);
                }
                return false;
            }

            private boolean hasProtocol() {
                return rawUrl.startsWith("http://") || rawUrl.startsWith("https://") || rawUrl.startsWith("ftp://");
            }
        }
    }

    interface Uniqueness {
        boolean unique(ShortLinkRepository shortLinkRepository);
    }

    interface Abusiveness {
        boolean abusive(SwearwordFilter swearwordFilter);
    }

}
