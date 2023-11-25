package cn.easii.tutelary.client.converter;

import cn.easii.tutelary.message.command.domain.BaseThreadInfo;
import cn.easii.tutelary.message.command.domain.LockInfo;
import cn.easii.tutelary.message.command.domain.StackTraceNode;
import cn.easii.tutelary.message.command.domain.VmOption;
import cn.easii.tutelary.message.command.result.ThreadDetail;
import com.sun.management.VMOption;
import java.lang.management.ThreadInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ManagementConverter {

    ManagementConverter CONVERTER = Mappers.getMapper(ManagementConverter.class);

    @Mappings({
        @Mapping(target = "group", expression = "java(thread.getThreadGroup().getName())")
    })
    BaseThreadInfo threadToInfo(Thread thread);

    @Mappings({
        @Mapping(target = "id", source = "threadId"),
        @Mapping(target = "name", source = "threadName"),
        @Mapping(target = "lock", source = "lockInfo")
    })
    ThreadDetail threadInfoToDetail(ThreadInfo threadInfo);

    LockInfo lockInfoTrans(java.lang.management.LockInfo lockInfo);

    @Mappings({
        @Mapping(target = "native", expression = "java(stackTraceElement.isNativeMethod())"),
        @Mapping(target = "declaringClass", expression = "java(stackTraceElement.getClassName())")
    })
    StackTraceNode stackTraceElementToNode(StackTraceElement stackTraceElement);

    VmOption vmOptionTrans(VMOption vmOption);

}
