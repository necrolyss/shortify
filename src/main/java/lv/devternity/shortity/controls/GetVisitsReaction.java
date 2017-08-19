package lv.devternity.shortity.controls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lv.devternity.shortity.application.Command;
import lv.devternity.shortity.application.Reaction;
import lv.devternity.shortity.model.Visit;
import lv.devternity.shortity.model.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Author: jurijsgrabovskis
 * Created at: 03/08/2017.
 */
@Component
public class GetVisitsReaction implements Reaction<GetVisitsCommand, Command.R.String> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    @Autowired
    private VisitRepository visitRepository;

    private ObjectMapper objectMapper;

    public GetVisitsReaction() {
        this.objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(DATE_FORMAT);
    }

    @Override
    public Command.R.String react(GetVisitsCommand command) {
        List<Visit> visitList = visitRepository.findByPath(command.shortlink());
        return new Command.R.String("<pre>" + visitJSONStatistics(visitList) + "</pre>");
    }

    private String visitJSONStatistics(List<Visit> visitList) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(visitList);
        } catch (JsonProcessingException e) {
            return "Internal error : Failed to load statistics";
        }
    }
}
