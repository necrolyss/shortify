package lv.devternity.shortity.application;

/**
 * Author: jurijsgrabovskis
 * Created at: 24/06/17.
 */
public interface Reaction<C extends Command<R>, R extends Command.R> {
    R react(C command);

    default R fallback(C command) {
        throw new UnsupportedOperationException("Fallback not implemented");
    }
}