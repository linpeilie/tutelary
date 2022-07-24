package com.tutelary.message.command;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommandResult implements Serializable {

    private Integer jobId;

    private int status;

}
