package space.borisgk.taxi.api.exception;

public class IllegalAspectTargetException extends Exception {
    public IllegalAspectTargetException() {
    }

    public IllegalAspectTargetException(String s) {
        super(s);
    }

    public IllegalAspectTargetException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public IllegalAspectTargetException(Throwable throwable) {
        super(throwable);
    }

    public IllegalAspectTargetException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
