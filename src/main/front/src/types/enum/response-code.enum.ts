enum ResponseCode {

    // HttpStatus 200
    SUCCESS = "SU",

    // HttpStatus 400
    VALIDATION_FAILED = "VF",
    DUPLICATE_EMAIL = "DE",
    DUPLICATE_NICKNAME = "DN",
    DUPLICATE_TEL_NUMBER = "DT",
    NOT_EXISTED_USER = "NU",
    NOT_EXISTED_BOARD = "NB",

    // HttpStatus 401
    SIGN_IN_FAIL = "SF",
    AUTHORIZATION_FAIL = "AF",

    // HttpStatus 403
    NO_PERMISSION = "NP",

    // HttpStatus 500
    DATABASE_ERROR = "DBE"
}

export default ResponseCode;