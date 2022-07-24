package com.tutelary.client.arthas.term;

import com.taobao.arthas.core.shell.cli.Completion;
import com.taobao.arthas.core.shell.handlers.Handler;
import com.taobao.arthas.core.shell.session.Session;
import com.taobao.arthas.core.shell.term.SignalHandler;
import com.taobao.arthas.core.shell.term.Term;
import io.termd.core.function.Function;

/**
 * 暂时适配 arthas，后面考虑删除
 */
public class TutelaryApiTerm implements Term {

    private Session session;

    public TutelaryApiTerm(Session session) {
        this.session = session;
    }

    @Override
    public Term resizehandler(Handler<Void> handler) {
        return this;
    }

    @Override
    public String type() {
        return "tutelary";
    }

    @Override
    public int width() {
        return 1000;
    }

    @Override
    public int height() {
        return 200;
    }

    @Override
    public Term stdinHandler(Handler<String> handler) {
        return this;
    }

    @Override
    public Term stdoutHandler(Function<String, String> handler) {
        return this;
    }

    @Override
    public Term write(String data) {
        return this;
    }

    @Override
    public long lastAccessedTime() {
        return session.getLastAccessTime();
    }

    @Override
    public Term echo(String text) {
        return this;
    }

    @Override
    public Term setSession(Session session) {
        return this;
    }

    @Override
    public Term interruptHandler(SignalHandler handler) {
        return this;
    }

    @Override
    public Term suspendHandler(SignalHandler handler) {
        return this;
    }

    @Override
    public void readline(String prompt, Handler<String> lineHandler) {

    }

    @Override
    public void readline(String prompt, Handler<String> lineHandler, Handler<Completion> completionHandler) {

    }

    @Override
    public Term closeHandler(Handler<Void> handler) {
        return this;
    }

    @Override
    public void close() {

    }

}
