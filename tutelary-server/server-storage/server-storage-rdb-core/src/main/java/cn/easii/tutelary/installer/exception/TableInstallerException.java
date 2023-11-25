package cn.easii.tutelary.installer.exception;

public class TableInstallerException extends RuntimeException {

    public TableInstallerException(String message) {
        super(message);
    }

    public TableInstallerException(String message, Throwable cause) {
        super(message, cause);
    }

}
