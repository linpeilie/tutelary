package com.tutelary.server.processor;

import com.tutelary.bean.converter.InstanceGarbageCollectorConverter;
import com.tutelary.bean.converter.InstanceHostConverter;
import com.tutelary.bean.converter.InstanceJvmMemoryConverter;
import com.tutelary.bean.converter.InstanceThreadStatisticConverter;
import com.tutelary.bean.domain.InstanceJvmMemory;
import com.tutelary.bean.domain.InstanceOverview;
import com.tutelary.common.processor.ServerMessageProcessor;
import com.tutelary.message.InstanceInfoReportRequestMessage;
import com.tutelary.message.command.result.Overview;
import com.tutelary.processor.AbstractMessageProcessor;
import com.tutelary.service.InstanceService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.management.MemoryType;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author linpeilie
 */
@Component
@Slf4j
public class InstanceReportProcessor extends AbstractMessageProcessor<InstanceInfoReportRequestMessage> implements ServerMessageProcessor<InstanceInfoReportRequestMessage> {

    private InstanceService instanceService;

    private InstanceHostConverter instanceHostConverter;

    private InstanceThreadStatisticConverter instanceThreadStatisticConverter;

    private InstanceJvmMemoryConverter instanceJvmMemoryConverter;

    private InstanceGarbageCollectorConverter instanceGarbageCollectorConverter;

    @Override
    protected void process(ChannelHandlerContext ctx, InstanceInfoReportRequestMessage message) {
        LocalDateTime reportTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(message.getCurrentTime()), ZoneId.systemDefault());
        String instanceId = message.getInstanceId();
        Overview overview = message.getOverview();

        InstanceOverview instanceOverview = new InstanceOverview();
        instanceOverview.setHost(instanceHostConverter.hostToInstanceHost(overview.getHostInfo(), instanceId, reportTime));
        instanceOverview.setThreadStatistic(instanceThreadStatisticConverter
                .threadStatisticToDomain(overview.getThreadStatistic(), instanceId, reportTime));
        List<InstanceJvmMemory> instanceJvmMemories = new ArrayList<>();
        instanceJvmMemories.addAll(overview.getHeapMemory().stream().map(memory ->
                instanceJvmMemoryConverter.jvmMemoryToDomain(memory, instanceId, reportTime, MemoryType.HEAP.name()))
                .collect(Collectors.toList()));
        instanceJvmMemories.addAll(overview.getNonHeapMemory().stream().map(memory ->
                instanceJvmMemoryConverter.jvmMemoryToDomain(memory, instanceId, reportTime, MemoryType.NON_HEAP.name()))
                .collect(Collectors.toList()));
        instanceOverview.setJvmMemories(instanceJvmMemories);
        instanceOverview.setGarbageCollectors(overview.getGarbageCollectors().stream().map(garbageCollector ->
                instanceGarbageCollectorConverter.garbageCollectorToDomain(garbageCollector, instanceId, reportTime))
                .collect(Collectors.toList()));
        instanceService.saveReportData(instanceOverview);
    }

    @Autowired
    public void setInstanceService(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @Autowired
    public void setInstanceConverter(InstanceHostConverter instanceHostConverter) {
        this.instanceHostConverter = instanceHostConverter;
    }

    @Autowired
    public void setInstanceThreadStatisticConverter(InstanceThreadStatisticConverter instanceThreadStatisticConverter) {
        this.instanceThreadStatisticConverter = instanceThreadStatisticConverter;
    }

    @Autowired
    public void setInstanceJvmMemoryConverter(InstanceJvmMemoryConverter instanceJvmMemoryConverter) {
        this.instanceJvmMemoryConverter = instanceJvmMemoryConverter;
    }

    @Autowired
    public void setInstanceGarbageCollectorConverter(InstanceGarbageCollectorConverter instanceGarbageCollectorConverter) {
        this.instanceGarbageCollectorConverter = instanceGarbageCollectorConverter;
    }
}
