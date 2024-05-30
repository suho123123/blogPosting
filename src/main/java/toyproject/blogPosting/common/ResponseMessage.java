package toyproject.blogPosting.common;

public interface ResponseMessage {

    // HttpStatus 200
    String SUCCESS = "Success.";

    // HttpStatus 400
    String VALIDATION_FAILED = "Validation Failed.";
    String DUPLICATE_EMAIL = "Duplicate email.";
    String DUPLICATE_NICKNAME = "Duplicate nickname.";
    String DUPLICATE_TEL_NUMBER = "Duplicate tel number.";
    String NOT_EXISTED_USER = "This user does not exist.";
    String NOT_EXISTED_BOARD = "This board does not exist.";

    // HttpStatus 401
    String SIGN_IN_FAIL = "Login information mismatch.";
    String AUTHORIZATION_FAIL = "Authorization Failed.";

    // HttpStatus 403
    String NO_PERMISSION = "Do not have permission.";

    // HttpStatus 500
    String DATABASE_ERROR = "Database error.";
}
