package lv.devternity.shortity.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Author: jurijsgrabovskis
 * Created at: 24/06/17.
 */
@Primary
@Component
public class ImmediateImpl implements Immediate {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public <C extends Command<R>, R extends Command.R> R execute(C command) {
        return resolveReaction(command).react(command);
    }

    protected <C extends Command<R>, R extends Command.R> Reaction<C, R> resolveReaction(C command) {
        return applicationContext.getBean(command.reactionClass());
    }

}
