package lv.devternity.shortity.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * Author: jurijsgrabovskis
 * Created at: 08/07/17.
 */
public class Preconditions {

    private final Collection<Condition> conditions = new ArrayList<>();
    private boolean showImmediate;

    public Preconditions(boolean showImmediate) {
        this.showImmediate = showImmediate;
    }

    public <T> Condition when(T value, Predicate<T> predicate) {
        Condition<T> condition = new Condition<>(value, predicate);
        conditions.add(condition);
        return condition;
    }

    public <T> Condition whenNot(T value, Predicate<T> predicate) {
        return when(value, predicate.negate());
    }

    public void check() {
        conditions.stream()
            .filter(Condition::isTrue)
            .findFirst()
            .ifPresent(condition -> {
                throw new ValidationException(condition.message(), showImmediate);
            });
    }

    public class Condition<T> {
        private final T item;
        private final Predicate<T> predicate;
        private String failureMessage;

        public Condition(T item, Predicate<T> predicate) {
            this.item = item;
            this.predicate = predicate;
        }

        public void then(String failureMessage) {
            this.failureMessage = failureMessage;
        }

        boolean isTrue() {
            return predicate.test(item);
        }

        String message() {
            return item != null ? failureMessage.replaceAll("%s", item.toString()) : failureMessage;
        }
    }

}
