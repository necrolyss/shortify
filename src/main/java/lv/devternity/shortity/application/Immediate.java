package lv.devternity.shortity.application;

/**
 * Author: jurijsgrabovskis
 * Created at: 24/06/17.
 */
public interface Immediate {

    <C extends Command<R>, R extends Command.R> R execute(C command);

}
