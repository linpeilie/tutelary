package com.tutelary.client.command.vmoption;

import com.sun.management.HotSpotDiagnosticMXBean;
import com.sun.management.VMOption;
import com.tutelary.client.command.Command;
import com.tutelary.client.command.ManagementFactory;
import com.tutelary.client.converter.ManagementConverter;
import com.tutelary.message.command.domain.VmOption;
import com.tutelary.message.command.param.VmOptionRequest;
import com.tutelary.message.command.result.VmOptionResponse;
import java.util.List;
import java.util.stream.Collectors;

public class VmOptionCommand implements Command<VmOptionResponse> {

    private final VmOptionRequest request;

    public VmOptionCommand(VmOptionRequest request) {
        this.request = request;
    }

    @Override
    public VmOptionResponse execute() {
        HotSpotDiagnosticMXBean hotSpotDiagnosticMXBean = ManagementFactory.getHotSpotDiagnosticMXBean();

        List<VMOption> diagnosticOptions = hotSpotDiagnosticMXBean.getDiagnosticOptions();

        List<VmOption> vmOptions = diagnosticOptions.stream()
            .map(ManagementConverter.CONVERTER::vmOptionTrans)
            .collect(Collectors.toList());

        VmOptionResponse vmOptionResponse = new VmOptionResponse();
        vmOptionResponse.setOptions(vmOptions);
        return vmOptionResponse;
    }

    @Override
    public void terminated() {

    }
}
