package com.tutelary.client.command.vmoption;

import com.sun.management.HotSpotDiagnosticMXBean;
import com.sun.management.VMOption;
import com.tutelary.client.command.Command;
import com.tutelary.client.command.ManagementFactory;
import com.tutelary.client.converter.ManagementConverter;
import com.tutelary.message.command.domain.VmOption;
import com.tutelary.message.command.param.SetVmOptionRequest;
import com.tutelary.message.command.result.SetVmOptionResponse;

public class SetVmOptionCommand implements Command<SetVmOptionResponse> {

    private final SetVmOptionRequest request;

    public SetVmOptionCommand(SetVmOptionRequest request) {
        this.request = request;
    }

    @Override
    public SetVmOptionResponse execute() {
        HotSpotDiagnosticMXBean hotSpotDiagnosticMXBean = ManagementFactory.getHotSpotDiagnosticMXBean();

        VMOption previousVmOption = hotSpotDiagnosticMXBean.getVMOption(request.getName());

        hotSpotDiagnosticMXBean.setVMOption(request.getName(), request.getValue());

        VMOption latestVmOption = hotSpotDiagnosticMXBean.getVMOption(request.getName());

        SetVmOptionResponse setVmOptionResponse = new SetVmOptionResponse();
        setVmOptionResponse.setPreviousVmOption(ManagementConverter.CONVERTER.vmOptionTrans(previousVmOption));
        setVmOptionResponse.setLatestVmOption(ManagementConverter.CONVERTER.vmOptionTrans(latestVmOption));
        return setVmOptionResponse;
    }

    @Override
    public void terminated() {

    }
}
