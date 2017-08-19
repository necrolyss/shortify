package lv.devternity.shortity.application;

/**
 * Author: jurijsgrabovskis
 * Created at: 24/06/17.
 */
public interface Command<T extends Command.R> {

    Class<? extends Reaction> reactionClass();

    interface R {

        class Void implements Command.R {
        }

        class String implements Command.R {
            private java.lang.String response;

            public String(java.lang.String response) {
                this.response = response;
            }

            public java.lang.String response() {
                return response;
            }
        }
    }
}