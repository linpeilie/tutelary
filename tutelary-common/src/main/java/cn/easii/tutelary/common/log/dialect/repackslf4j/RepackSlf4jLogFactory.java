package cn.easii.tutelary.common.log.dialect.repackslf4j;

import cn.easii.tutelary.deps.org.slf4j.LoggerFactory;
import cn.easii.tutelary.deps.org.slf4j.helpers.NOPLoggerFactory;
import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class RepackSlf4jLogFactory extends LogFactory {

    public RepackSlf4jLogFactory() {
        this(true);
    }

    /**
     * 构造
     *
     * @param failIfNOP 如果未找到桥接包是否报错
     */
    public RepackSlf4jLogFactory(boolean failIfNOP) {
        super("Repack-Slf4j");
        checkLogExist(LoggerFactory.class);
        if (false == failIfNOP) {
            return;
        }

        // SFL4J writes it error messages to System.err. Capture them so that the user does not see such a message on
        // the console during automatic detection.
        final StringBuilder buf = new StringBuilder();
        final PrintStream err = System.err;
        try {
            System.setErr(new PrintStream(new OutputStream() {
                @Override
                public void write(int b) {
                    buf.append((char) b);
                }
            }, true, "US-ASCII"));
        } catch (UnsupportedEncodingException e) {
            throw new Error(e);
        }

        try {
            if (LoggerFactory.getILoggerFactory() instanceof NOPLoggerFactory) {
                throw new NoClassDefFoundError(buf.toString());
            } else {
                err.print(buf);
                err.flush();
            }
        } finally {
            System.setErr(err);
        }
    }

    @Override
    public Log createLog(String name) {
        return new RepackSlf4jLog(name);
    }

    @Override
    public Log createLog(Class<?> clazz) {
        return new RepackSlf4jLog(clazz);
    }

}
