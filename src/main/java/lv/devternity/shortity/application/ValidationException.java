package lv.devternity.shortity.application;

/**
 * Author: jurijsgrabovskis
 * Created at: 08/07/17.
 */
public class ValidationException extends RuntimeException {
    private boolean showImmediate;

    public ValidationException(String faultMessage, boolean showImmediate) {
        super(faultMessage);
        this.showImmediate = showImmediate;
    }

    public boolean isShowImmediate() {
        return showImmediate;
    }
}
