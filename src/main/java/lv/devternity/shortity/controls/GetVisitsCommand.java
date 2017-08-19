package lv.devternity.shortity.controls;

import lv.devternity.shortity.application.Command;
import lv.devternity.shortity.application.Reaction;

/**
 * Author: jurijsgrabovskis
 * Created at: 03/08/2017.
 */
public class GetVisitsCommand implements Command<Command.R.String> {
    private String shortlink;

    public GetVisitsCommand(String shortlink) {
        this.shortlink = shortlink;
    }

    public String shortlink() {
        return shortlink;
    }

    @Override
    public Class<? extends Reaction> reactionClass() {
        return GetVisitsReaction.class;
    }
}
