package lv.devternity.shortity.controls;

import lv.devternity.shortity.application.Command;
import lv.devternity.shortity.application.Reaction;

/**
 * Author: jurijsgrabovskis
 * Created at: 02/08/2017.
 */
public class TrackVisitCommand implements Command<Command.R.String> {

    private String shortLink;
    private String clientIPAddress;

    public TrackVisitCommand(String shortLink, String clientIPAddress) {
        this.shortLink = shortLink;
        this.clientIPAddress = clientIPAddress;
    }

    public String shortLink() {
        return shortLink;
    }

    public String clientIPAddress() {
        return clientIPAddress;
    }

    @Override
    public Class<? extends Reaction> reactionClass() {
        return TrackVisitReaction.class;
    }
}
