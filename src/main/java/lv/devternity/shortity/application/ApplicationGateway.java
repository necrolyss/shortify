package lv.devternity.shortity.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author: jurijsgrabovskis
 * Created at: 24/06/17.
 */
@Component
public class ApplicationGateway implements Immediate {

    @Autowired
    private ImmediateImpl directReactionExecutor;

    @Autowired
    private SafeRemoteImmediateImpl remoteCommandExecutor;

    @Override
    public <C extends Command<R>, R extends Command.R> R execute(C command) {
        return directReactionExecutor.execute(command);
    }

    public <C extends Command<R>, R extends Command.R> R executeRemote(C command) {
        return remoteCommandExecutor.execute(command);
    }
}
