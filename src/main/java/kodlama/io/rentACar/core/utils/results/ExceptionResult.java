package kodlama.io.rentACar.core.utils.results;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResult<T extends Exception> {
    private String type;
    private String message;

    public ExceptionResult(Class<T> type, String message) {
        this.type = type.getSimpleName();
        this.message = message;
    }
}
