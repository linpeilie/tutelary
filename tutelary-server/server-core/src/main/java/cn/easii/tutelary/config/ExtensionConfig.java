package cn.easii.tutelary.config;

import cn.easii.tutelary.command.CommandExecute;
import cn.easii.tutelary.common.extension.ExtensionExecutor;
import cn.easii.tutelary.common.extension.ExtensionRegister;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExtensionConfig {

    @Bean
    public ExtensionExecutor extensionExecutor(List<CommandExecute> commandExecutes) {
        commandExecutes.forEach(ExtensionRegister::doRegistration);
        return new ExtensionExecutor();
    }

}
