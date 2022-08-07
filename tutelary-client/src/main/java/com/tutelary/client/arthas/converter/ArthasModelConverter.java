package com.tutelary.client.arthas.converter;

import com.taobao.arthas.core.command.model.*;
import com.tutelary.message.command.*;
import com.tutelary.message.command.BlockingThreadCommandResultMessage;
import com.tutelary.message.command.domain.*;
import com.tutelary.message.command.domain.StackTraceElement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ArthasModelConverter {

    ArthasModelConverter CONVERTER = Mappers.getMapper(ArthasModelConverter.class);

    DashboardRuntime runtimeInfoVoToDashboardRuntime(RuntimeInfoVO runtimeInfoVO);

    GarbageCollector gcInfoToGarbageCollector(GcInfoVO gcInfoVO);

    List<GarbageCollector> gcInfoListToGarbageCollectorList(List<GcInfoVO> gcInfoVOS);

    ThreadInfo threadVoToThreadDetailInfo(ThreadVO threadVO);

    List<ThreadInfo> threadVosToThreadDetailInfoList(List<ThreadVO> threadVOS);

    MemoryUsage memoryEntryToMemoryUsage(MemoryEntryVO memoryEntryVO);

    List<MemoryUsage> memoryEntriesToMemoryUsageList(List<MemoryEntryVO> memoryEntryVOS);

    StackTraceElement stackTraceElementTrans(java.lang.StackTraceElement stackTraceElement);

    MonitorInfo monitorInfoTrans(java.lang.management.MonitorInfo monitorInfo);

    ThreadInfo threadInfoToThreadDetailInfo(java.lang.management.ThreadInfo threadInfo);

    BlockingThreadCommandResultMessage arthasBlockingLockInfoToTutelary(com.taobao.arthas.core.command.model.BlockingLockInfo blockingLockInfo);



}
