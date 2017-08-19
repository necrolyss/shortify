package lv.devternity.shortity.application;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Author: jurijsgrabovskis
 * Created at: 09/07/17.
 */
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ValidationException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        ex = unwrapHystrixException(ex);
        if (((ValidationException) ex).isShowImmediate()) {
            return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
        } else {
            String responseWrapper = "<div style=\"text-align: center;margin-top: 15%;color: red;font-size: 24px;\">" + ex.getMessage() + "<div>";
            return handleExceptionInternal(ex, responseWrapper, new HttpHeaders(), HttpStatus.CONFLICT, request);
        }
    }

    private RuntimeException unwrapHystrixException(RuntimeException ex) {
        if (ex instanceof HystrixRuntimeException) {
            Throwable cause = ex.getCause();
            if (cause instanceof RuntimeException) {
                ex = (RuntimeException) cause;
            }
        }
        return ex;
    }
}