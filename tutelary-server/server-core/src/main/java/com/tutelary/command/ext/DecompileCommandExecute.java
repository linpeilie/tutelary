package com.tutelary.command.ext;

import com.tutelary.command.AbstractSingleTemporaryCommandExecuteAdapter;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.message.command.param.DecompileRequest;
import com.tutelary.message.command.result.DecompileResponse;
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
