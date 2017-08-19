package lv.devternity.shortity.controls;

import lv.devternity.shortity.application.Command;
import lv.devternity.shortity.application.Reaction;

/**
 * Author: jurijsgrabovskis
 * Created at: 03/08/2017.
 */
public class GetAllShortlinksCommand implements Command<Command.R.String> {
    @Override
    public Class<? extends Reaction> reactionClass() {
        return GetAllShortLinksReaction.class;
    }
}
