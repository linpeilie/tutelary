package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ToString
@EqualsAndHashCode
@ProtobufClass
@Getter
public class TraceNode implements Serializable {

    private String className;
    private String methodName;
    private int line;
    private boolean isThrow;
    private String mark;
    private long beginTimestamp;
    private long endTimestamp;

    private List<TraceNode> children;

    public static TraceNode newNode(String className, String methodName, int line) {
        TraceNode traceNode = new TraceNode();
        traceNode.className = className;
        traceNode.methodName = methodName;
        traceNode.line = line;
        return traceNode;
    }

    public void start() {
        this.beginTimestamp = System.nanoTime();
    }

    public void end() {
        this.endTimestamp = System.nanoTime();
    }

    public void end(Throwable e) {
        end();
        this.isThrow = true;
        this.mark = e.getMessage();
    }

    public void addChild(TraceNode traceNode) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(traceNode);
    }

}
