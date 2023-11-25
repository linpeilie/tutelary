package cn.easii.tutelary.client.core.compiler;

public class CharSequenceCompilerException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private DiagnosticList diagnostics;

    private final String qualifiedClassName;

    public CharSequenceCompilerException(String message,
        String qualifiedClassName,
        DiagnosticList diagnostics) {
        super(message);
        this.qualifiedClassName = qualifiedClassName;
        this.diagnostics = diagnostics;
    }

    public CharSequenceCompilerException(Throwable cause,
        String qualifiedClassName,
        DiagnosticList diagnostics) {
        super(cause);
        this.qualifiedClassName = qualifiedClassName;
        this.diagnostics = diagnostics;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "\n Qualified Class Name : " + qualifiedClassName + " \n" +
               diagnostics.getMessages();
    }
}
