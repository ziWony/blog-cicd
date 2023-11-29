package shop.mtcoding.blogv2._core.error.ex;

import lombok.Getter;

@Getter
public class Exception401 extends RuntimeException{

    private boolean isBack;

    public Exception401(String msg, boolean isBack) {
        super(msg);
        this.isBack = isBack;
    }
}
