package kodlama.io.rentACar.Common.constants;


public class Messages {
    public static class Car {
        public static final String NotExists = "CAR_NOT_EXISTS";
        public static final String NotAvailable = "CAR_NOT_AVAILABLE";
        public static final String PlateExists = "PLATE_ALREADY_EXISTS";
        public static final String PlateNotValid = "plate number must match the pattern";
    }

    public static class Model {
        public static final String NotExists = "MODEL_NOT_EXISTS";
        public static final String Exists = "MODEL_ALREADY_EXISTS";
    }

    public static class Brand {
        public static final String NotExists = "BRAND_NOT_EXISTS";
        public static final String Exists = "BRAND_ALREADY_EXISTS";
    }

    public static class Invoice {
        public static final String NotFound = "INVOICE_NOT_FOUND";
    }

    public static class Maintenance {
        public static final String NotExists = "MAINTENANCE_NOT_EXISTS";
        public static final String CarExists = "CAR_IS_CURRENTLY_UNDER_MAINTENANCE";
        public static final String CarNotExists = "CAR_NOT_REGISTERED_FOR_MAINTENANCE";
        public static final String CarIsRented = "CAR_IS_CURRENTLY_RENTED_AND_CANNOT_BE_SERVICED_FOR_MAINTENANCE";
    }

    public static class Payment {
        public static final String NotFound = "PAYMENT_NOT_FOUND";
        public static final String CardNumberAlreadyExists = "CARD_NUMBER_ALREADY_EXISTS";
        public static final String NotValid = "NOT_A_VALID_PAYMENT";
        public static final String InsufficientBalance = "INSUFFICIENT_BALANCE";
        public static final String Failed = "PAYMENT_FAILED";
    }

    public static class Rental {
        public static final String NotExists = "RENTAL_NOT_EXISTS";
    }

}
