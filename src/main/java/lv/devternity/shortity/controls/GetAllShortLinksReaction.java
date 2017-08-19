package lv.devternity.shortity.controls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.collect.Lists;
import lv.devternity.shortity.application.Command;
import lv.devternity.shortity.application.Reaction;
import lv.devternity.shortity.model.ShortLink;
import lv.devternity.shortity.model.ShortLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Author: jurijsgrabovskis
 * Created at: 03/08/2017.
 */
@Component
public class GetAllShortLinksReaction implements Reaction<GetAllShortlinksCommand, Command.R.String> {

    @Autowired
    private ShortLinkRepository shortLinkRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Command.R.String react(GetAllShortlinksCommand command) {
        Iterable<ShortLink> shortLinksIterable = shortLinkRepository.findAll();
        ArrayList<ShortLink> shortLinks = Lists.newArrayList(shortLinksIterable);
        try {
            ObjectWriter defaultPrettyPrinter = objectMapper.writerWithDefaultPrettyPrinter();
            String shortLinkJSON = defaultPrettyPrinter.writeValueAsString(shortLinks);
            return new Command.R.String("<pre>" + shortLinkJSON + "</pre>");
        } catch (JsonProcessingException e) {
            return new Command.R.String("Internal error : Failed to load statistics");
        }
    }
}
