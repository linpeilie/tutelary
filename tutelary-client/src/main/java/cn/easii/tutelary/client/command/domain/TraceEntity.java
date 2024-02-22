package cn.easii.tutelary.client.command.domain;

import cn.easii.tutelary.message.command.domain.TraceNode;
import cn.easii.tutelary.message.command.result.TraceResponse;
import java.util.List;
import java.util.Stack;
import lombok.Getter;

public class TraceEntity {

    @Getter
    private final TraceNode root;

    private final Stack<TraceNode> nodeStack;

    public TraceEntity(TraceNode root) {
        root.start();
        this.root = root;

        nodeStack = new Stack<>();
        nodeStack.push(root);
    }

    public void start(String className, String methodName, int line) {
        TraceNode parent = nodeStack.peek();

        TraceNode node = parent.findChild(className, methodName, line);
        if (node == null) {
            node = TraceNode.newNode(className, methodName, line);
            parent.addChild(node);
        }
        nodeStack.push(node);
        node.start();
    }

    public void end() {
        TraceNode node = nodeStack.pop();
        node.end();
    }

    public void end(Throwable throwable) {
        TraceNode node = nodeStack.pop();
        node.end(throwable);
    }

    public boolean isFinish() {
        return nodeStack.isEmpty();
    }

    public TraceResponse getTraceResult() {
        TraceResponse traceResult = new TraceResponse();
        traceResult.setNode(root);
        return traceResult;
    }

}
