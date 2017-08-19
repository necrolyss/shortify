package lv.devternity.shortity.controls;

import lv.devternity.shortity.application.Command;
import lv.devternity.shortity.application.Reaction;
import lv.devternity.shortity.model.ShortLink;
import lv.devternity.shortity.model.ShortLinkRepository;
import lv.devternity.shortity.utils.SwearwordFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Author: jurijsgrabovskis
 * Created at: 24/06/17.
 */
@Component
public class GenerateShortLinkReaction implements Reaction<GenerateShortLinkCommand, Command.R.String> {

    @Autowired
    private ShortLinkRepository shortLinkRepository;

    @Autowired
    private SwearwordFilter swearwordFilter;

    @Override
    public Command.R.String react(GenerateShortLinkCommand command) {
        String shortPath = StringUtils.isEmpty(command.customPath()) ? generatePath() : command.customPath();
        ShortLink.Unchecked uncheckedShortLink = new ShortLink.Unchecked(shortPath, command.inputUrl());
        shortLinkRepository.insert(uncheckedShortLink.buildChecked(shortLinkRepository, swearwordFilter));
        return new Command.R.String("http://localhost:8080/r/" + shortPath);
    }

    private String generatePath() {
        return RandomStringUtils.random(8, true, true);
    }
}
