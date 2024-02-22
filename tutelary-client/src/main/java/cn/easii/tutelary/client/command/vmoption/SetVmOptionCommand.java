package cn.easii.tutelary.client.command.vmoption;

import cn.easii.tutelary.client.converter.ManagementConverter;
import cn.easii.tutelary.message.command.param.SetVmOptionRequest;
import cn.easii.tutelary.message.command.result.SetVmOptionResponse;
import com.sun.management.HotSpotDiagnosticMXBean;
import com.sun.management.VMOption;
import cn.easii.tutelary.client.command.Command;
import cn.easii.tutelary.common.utils.ManagementFactory;

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
