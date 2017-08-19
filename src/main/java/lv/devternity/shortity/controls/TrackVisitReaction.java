package lv.devternity.shortity.controls;

import com.fasterxml.jackson.databind.ObjectMapper;
import lv.devternity.shortity.application.Command;
import lv.devternity.shortity.application.Preconditions;
import lv.devternity.shortity.application.Reaction;
import lv.devternity.shortity.model.ShortLink;
import lv.devternity.shortity.model.ShortLinkRepository;
import lv.devternity.shortity.model.Visit;
import lv.devternity.shortity.model.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.support.BasicMapId;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

/**
 * Author: jurijsgrabovskis
 * Created at: 02/08/2017.
 */
@Component
public class TrackVisitReaction implements Reaction<TrackVisitCommand, Command.R.String> {

    private static final String COUNTRY_NAME_FIELD = "country_name";
    private static final String GEOIP_SERVICE_HOST = "freegeoip.net";
    private static final int GEO_SERVICE_PORT = 80;
    private static final String UNKNOWN_COUNTRY = "unknown";

    @Autowired
    private ShortLinkRepository shortLinkRepository;

    @Autowired
    private VisitRepository visitRepository;

    private ObjectMapper jsonUnmarshaller = new ObjectMapper();

    @Override
    public Command.R.String react(TrackVisitCommand command) {
        String shortLink = command.shortLink();
        ShortLink record = shortLinkRecord(shortLink);

        Preconditions preconditions = new Preconditions(false);
        preconditions.whenNot(record, Objects::nonNull)
            .then("Ooops, requested shortlink \"" + shortLink + "\" is missing! " +
                "Do you wish to <a href=\"http://localhost:8080/\">generate</a> some ?");
        preconditions.check();

        storeVisitFact(command, locationSource(command));

        return new Command.R.String(record.destination());
    }

    @Override
    public Command.R.String fallback(TrackVisitCommand command) {
        storeVisitFact(command, UNKNOWN_COUNTRY);
        ShortLink shortLink = shortLinkRecord(command.shortLink());
        return new Command.R.String(shortLink.destination());
    }

    private ShortLink shortLinkRecord(String shortLink) {
        return shortLinkRepository.findOne(BasicMapId.id("path", shortLink));
    }

    private void storeVisitFact(TrackVisitCommand command, String countryName) {
        Visit visit = new Visit(command.shortLink(), command.clientIPAddress(), countryName);
        visitRepository.save(visit);
    }

    private String locationSource(TrackVisitCommand command) {
        try {
            URL serviceURL = new URL("http", GEOIP_SERVICE_HOST, GEO_SERVICE_PORT, "/json/" + command.clientIPAddress());
            Map<String, String> parsedCountryData = jsonUnmarshaller.readValue(serviceURL, Map.class);
            return parsedCountryData.get(COUNTRY_NAME_FIELD);
        } catch (IOException e) {
            return UNKNOWN_COUNTRY;
        }
    }
}
