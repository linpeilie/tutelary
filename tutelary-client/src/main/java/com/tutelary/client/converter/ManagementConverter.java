package com.tutelary.client.converter;

import com.tutelary.message.command.domain.BaseThreadInfo;
import com.tutelary.message.command.domain.LockInfo;
import com.tutelary.message.command.domain.StackTraceNode;
import com.tutelary.message.command.result.ThreadDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.lang.management.ThreadInfo;

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

    StackTraceNode stackTraceElementToNode(StackTraceElement stackTraceElement);

}
