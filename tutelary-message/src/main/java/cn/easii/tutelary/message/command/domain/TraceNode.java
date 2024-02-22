package cn.easii.tutelary.message.command.domain;

import cn.easii.tutelary.common.utils.ThreadUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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
    private int count;
    private long minCost = Long.MAX_VALUE;
    private long maxCost = Long.MIN_VALUE;
    private long totalCost = 0;

    private List<TraceNode> children;

    public static TraceNode newNode(String className, String methodName, int line) {
        TraceNode traceNode = new TraceNode();
        traceNode.className = className;
        traceNode.methodName = methodName;
        traceNode.line = line;
        traceNode.count = 0;
        return traceNode;
    }

    public void start() {
        this.count++;
        this.beginTimestamp = System.nanoTime();
    }

    public void end() {
        this.endTimestamp = System.nanoTime();
        long cost = totalTimeSpent();
        if (cost < minCost) {
            minCost = cost;
        }
        if (cost > maxCost) {
            maxCost = cost;
        }
        totalCost += cost;
    }

    public void end(Throwable e) {
        end();
        this.isThrow = true;
        this.mark = e.getClass().getName() + ", cause : [" + e.getMessage() + "]";
    }

    public void addChild(TraceNode traceNode) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(traceNode);
    }

    public TraceNode findChild(String className, String methodName, int line) {
        if (CollectionUtil.isEmpty(children)) {
            return null;
        }
        return children.stream()
            .filter(child -> child.getClassName().equals(className)
                             && child.getMethodName().equals(methodName)
                             && child.getLine() == line)
            .findFirst().orElse(null);
    }

    public long totalTimeSpent() {
        return endTimestamp - beginTimestamp;
    }

}
