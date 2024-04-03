package co.edu.uptc.project_1.exceptions;

import org.springframework.http.HttpStatus;

public enum TypeMessage {
    NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "Not Found", 404),
    NOT_SAVED(HttpStatus.BAD_REQUEST.value(), "Not Saved", 410),
    INFORMATION_INCOMPLETE(HttpStatus.BAD_REQUEST.value(), "Infomation Incomplete", 430),
    NOT_FOUND_FILE(HttpStatus.NOT_FOUND.value(), "Not Found file", 420),
    ILEGAL_ACTION(HttpStatus.BAD_REQUEST.value(), "Ilegal Action", 421),
    SAVE(HttpStatus.OK.value(), "Saved", 210),
    DUPLICATE(HttpStatus.BAD_REQUEST.value(), "Duplicate Group", 210);

    public final String message;
    public final int code;
    public final int codeHttp;

    private TypeMessage(int codeHttp, String message, int code) {
        {
            this.message = message;
            this.code = code;
            this.codeHttp = codeHttp;
        }

    }

}
