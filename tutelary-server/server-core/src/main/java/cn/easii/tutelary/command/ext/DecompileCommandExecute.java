package cn.easii.tutelary.command.ext;

import cn.easii.tutelary.command.AbstractSingleTemporaryCommandExecuteAdapter;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.message.command.param.DecompileRequest;
import cn.easii.tutelary.message.command.result.DecompileResponse;
import org.springframework.stereotype.Component;

@Component
@Extension(commandCode = CommandConstants.decompile)
public class DecompileCommandExecute
    extends AbstractSingleTemporaryCommandExecuteAdapter<DecompileRequest, DecompileResponse> {
    @Override
    public Integer commandCode() {
        return CommandConstants.decompile;
    }
}
