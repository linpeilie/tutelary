package com.tutelary.client.arthas.converter;

import com.taobao.arthas.core.command.model.GcInfoVO;
import com.taobao.arthas.core.command.model.MemoryEntryVO;
import com.taobao.arthas.core.command.model.RuntimeInfoVO;
import com.taobao.arthas.core.command.model.ThreadVO;
import com.tutelary.message.command.DashboardRuntime;
import com.tutelary.message.command.GarbageCollector;
import com.tutelary.message.command.MemoryUsage;
import com.tutelary.message.command.ThreadDetailInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ArthasModelConverter {

    ArthasModelConverter CONVERTER = Mappers.getMapper(ArthasModelConverter.class);

    DashboardRuntime runtimeInfoVoToDashboardRuntime(RuntimeInfoVO runtimeInfoVO);

    GarbageCollector gcInfoToGarbageCollector(GcInfoVO gcInfoVO);

    List<GarbageCollector> gcInfoListToGarbageCollectorList(List<GcInfoVO> gcInfoVOS);

    ThreadDetailInfo threadVoToThreadDetailInfo(ThreadVO threadVO);

    List<ThreadDetailInfo> threadVosToThreadDetailInfoList(List<ThreadVO> threadVOS);

    MemoryUsage memoryEntryToMemoryUsage(MemoryEntryVO memoryEntryVO);

    List<MemoryUsage> memoryEntriesToMemoryUsageList(List<MemoryEntryVO> memoryEntryVOS);

}
