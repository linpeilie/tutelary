package com.tutelary;

import com.taobao.arthas.core.advisor.AdviceListener;
import com.taobao.arthas.core.command.model.ResultModel;
import com.taobao.arthas.core.shell.cli.CliToken;
import com.taobao.arthas.core.shell.command.CommandProcess;
import com.taobao.arthas.core.shell.handlers.Handler;
import com.taobao.arthas.core.shell.session.Session;
import com.taobao.middleware.cli.CommandLine;

import java.lang.instrument.ClassFileTransformer;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TutelaryCommandProcess implements CommandProcess {
    @Override
    public List<CliToken> argsTokens() {
        return null;
    }

    @Override
    public List<String> args() {
        return null;
    }

    @Override
    public CommandLine commandLine() {
        return null;
    }

    @Override
    public Session session() {
        return null;
    }

    @Override
    public boolean isForeground() {
        return false;
    }

    @Override
    public String type() {
        return null;
    }

    @Override
    public int width() {
        return 0;
    }

    @Override
    public int height() {
        return 0;
    }

    @Override
    public CommandProcess stdinHandler(Handler<String> handler) {
        return null;
    }

    @Override
    public CommandProcess interruptHandler(Handler<Void> handler) {
        return null;
    }

    @Override
    public CommandProcess suspendHandler(Handler<Void> handler) {
        return null;
    }

    @Override
    public CommandProcess resumeHandler(Handler<Void> handler) {
        return null;
    }

    @Override
    public CommandProcess endHandler(Handler<Void> handler) {
        return null;
    }

    @Override
    public CommandProcess write(String data) {
        return null;
    }

    @Override
    public CommandProcess backgroundHandler(Handler<Void> handler) {
        return null;
    }

    @Override
    public CommandProcess foregroundHandler(Handler<Void> handler) {
        return null;
    }

    @Override
    public CommandProcess resizehandler(Handler<Void> handler) {
        return null;
    }

    @Override
    public void end() {

    }

    @Override
    public void end(int status) {

    }

    @Override
    public void end(int status, String message) {

    }

    @Override
    public void register(AdviceListener listener, ClassFileTransformer transformer) {

    }

    @Override
    public void unregister() {

    }

    @Override
    public AtomicInteger times() {
        return null;
    }

    @Override
    public void resume() {

    }

    @Override
    public void suspend() {

    }

    @Override
    public void echoTips(String tips) {

    }

    @Override
    public String cacheLocation() {
        return null;
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void appendResult(ResultModel result) {

    }
}
