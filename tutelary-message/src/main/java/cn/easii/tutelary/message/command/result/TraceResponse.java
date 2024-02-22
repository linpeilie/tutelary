package cn.easii.tutelary.message.command.result;

import cn.easii.tutelary.annotation.Command;
import cn.easii.tutelary.common.utils.ThreadUtil;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.message.command.domain.BaseThreadInfo;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandResponse;
import cn.easii.tutelary.message.command.domain.TraceNode;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.TRACE_METHOD)
public class TraceResponse extends CommandResponse {

    private TraceNode node;
    private BaseThreadInfo thread;
    private String tccl;

    public TraceResponse() {
        // thread
        Thread currentThread = Thread.currentThread();
        BaseThreadInfo thread = new BaseThreadInfo();
        thread.setId(currentThread.getId());
        thread.setName(currentThread.getName());
        thread.setGroup(currentThread.getThreadGroup().getName());
        thread.setPriority(currentThread.getPriority());
        thread.setDaemon(currentThread.isDaemon());
        this.thread = thread;
        // tccl
        this.tccl = ThreadUtil.getTCCL(currentThread);
    }

    public static TraceResponse newInstance(TraceNode node) {
        TraceResponse traceResponse = new TraceResponse();
        traceResponse.setNode(node);
        return traceResponse;
    }

}
