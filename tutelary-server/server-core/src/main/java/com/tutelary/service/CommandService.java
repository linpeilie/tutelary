package com.tutelary.service;

import com.tutelary.bean.domain.CommandTask;
import com.tutelary.bean.domain.CommandTaskCreate;

public interface CommandService {

    CommandTask createCommand(CommandTaskCreate commandTaskCreate);

}
