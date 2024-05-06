package kodlama.io.rentACar.Common.utils.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD}) // You can add more than one element type.
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotFutureYearValidator.class)
public @interface NotFutureYear {
    String message() default "model year value cannot be in the future";
    //? Farkli kullanici gruplarina ozellestirilmis idlemler veya ozellikler tanimlamak icin kullanilir.
    Class<?>[] groups() default {};
    //? Veri transfer nesnelerinde belirli bir frubun mesajlarini tasima icin kullanilir.
    Class<? extends Payload>[] payload() default {};
}
