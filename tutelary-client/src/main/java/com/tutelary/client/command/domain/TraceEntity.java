package com.tutelary.client.command.domain;

import com.tutelary.message.command.result.TraceResponse;
import com.tutelary.message.command.domain.TraceNode;

import java.util.Stack;

public class TraceEntity {

    private final TraceNode root;

    private final Stack<TraceNode> nodeStack;

    public TraceEntity(TraceNode root) {
        root.start();
        this.root = root;

        nodeStack = new Stack<>();
        nodeStack.push(root);
    }

    public void start(String className, String methodName, int line) {
        TraceNode node = TraceNode.newNode(className, methodName, line);
        TraceNode parent = nodeStack.peek();
        parent.addChild(node);
        nodeStack.push(node);
        node.start();
    }

    public void end() {
        TraceNode node = nodeStack.pop();
        node.end();
    }

    public boolean isFinish() {
        return nodeStack.isEmpty();
    }

    public TraceResponse getTraceResult() {
        TraceResponse traceResult = new TraceResponse();
        traceResult.setNode(root);;
        return traceResult;
    }

}
