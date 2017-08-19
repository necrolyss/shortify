package lv.devternity.shortity.controls;

import lv.devternity.shortity.application.Command;
import lv.devternity.shortity.application.Reaction;

/**
 * Author: jurijsgrabovskis
 * Created at: 24/06/17.
 */
public class GenerateShortLinkCommand implements Command<Command.R.String> {

    @Override
    public Class<? extends Reaction> reactionClass() {
        return GenerateShortLinkReaction.class;
    }

    private final String inputUrl;

    private final String customPath;

    public GenerateShortLinkCommand(String inputUrl, String customPath) {
        this.inputUrl = inputUrl;
        this.customPath = customPath;
    }

    public String inputUrl() {
        return inputUrl;
    }

    public String customPath() {
        return customPath;
    }
}
