package cn.easii.tutelary.client.command.vmoption;

import cn.easii.tutelary.client.converter.ManagementConverter;
import cn.easii.tutelary.message.command.domain.VmOption;
import cn.easii.tutelary.message.command.param.VmOptionRequest;
import cn.easii.tutelary.message.command.result.VmOptionResponse;
import com.sun.management.HotSpotDiagnosticMXBean;
import com.sun.management.VMOption;
import cn.easii.tutelary.client.command.Command;
import cn.easii.tutelary.client.command.ManagementFactory;
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
