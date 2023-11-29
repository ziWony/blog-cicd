package shop.mtcoding.blogv2._core.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import shop.mtcoding.blogv2._core.error.ex.*;
import shop.mtcoding.blogv2._core.util.Script;

@ControllerAdvice
public class MyExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(Exception400.class)
    public String ex400(Exception400 e) {
        return Script.back(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler(Exception401.class)
    public String ex401(Exception401 e) {
        if (e.isBack()) {
            return Script.back(e.getMessage());
        } else {
            return Script.href("/loginForm", e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    @ExceptionHandler(Exception403.class)
    public String ex403(Exception403 e) {
        return Script.back(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(Exception404.class)
    public String ex404(Exception404 e) {
        return Script.back(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(Exception500.class)
    public String ex500(Exception500 e) {
        return Script.back(e.getMessage());
    }
}
