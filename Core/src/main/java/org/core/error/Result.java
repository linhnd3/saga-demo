package org.core.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Result<T> {

    private ErrorMessage errorMessage;
    private T content;

    public static final Result<Void> SUCCESS = new Result<>(ErrorMessage.SUCCESS);

    public Result(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Result(T content) {
        this.errorMessage = ErrorMessage.SUCCESS;
        this.content = content;
    }

    public boolean isSucceed() {
        return errorMessage == ErrorMessage.SUCCESS;
    }

    public boolean isFailed() {
        return !isSucceed();
    }
}
