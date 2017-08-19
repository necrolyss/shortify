package lv.devternity.shortity.application;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import org.springframework.stereotype.Component;

import static com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy.THREAD;

/**
 * Author: jurijsgrabovskis
 * Created at: 03/08/2017.
 */
@Component
public class SafeRemoteImmediateImpl extends ImmediateImpl {

    private static final int CONCURRENCY_LIMIT = 10;

    @Override
    public <C extends Command<R>, R extends Command.R> R execute(C command) {
        HystrixCommand.Setter commandOptions = hystrixOptions(command);
        HystrixCommand<R> hystrixCommand = new HystrixCommand<R>(commandOptions) {
            @Override
            protected R run() throws Exception {
                return SafeRemoteImmediateImpl.super.execute(command);
            }

            @Override
            protected R getFallback() {
                return resolveReaction(command).fallback(command);
            }
        };
        return hystrixCommand.execute();
    }

    private HystrixCommand.Setter hystrixOptions(Command command) {
        String commandName = command.getClass().getSimpleName();
        return HystrixCommand.Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey(commandName))
            .andCommandKey(HystrixCommandKey.Factory.asKey(commandName))
            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                .withExecutionIsolationStrategy(THREAD)
                .withExecutionTimeoutInMilliseconds(1000))
            .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                .withCoreSize(CONCURRENCY_LIMIT));
    }
}
