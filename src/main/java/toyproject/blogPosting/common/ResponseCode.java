package toyproject.blogPosting.common;

public interface ResponseCode {

    // HttpStatus 200
    String SUCCESS = "SU";

    // HttpStatus 400
    String VALIDATION_FAILED = "VF";
    String DUPLICATE_EMAIL = "DE";
    String DUPLICATE_NICKNAME = "DN";
    String DUPLICATE_TEL_NUMBER = "DT";
    String NOT_EXISTED_USER = "NU";
    String NOT_EXISTED_BOARD = "NB";

    // HttpStatus 401
    String SIGN_IN_FAIL = "SF";
    String AUTHORIZATION_FAIL = "AF";

    // HttpStatus 403
    String NO_PERMISSION = "NP";

    // HttpStatus 500
    String DATABASE_ERROR = "DBE";
}
