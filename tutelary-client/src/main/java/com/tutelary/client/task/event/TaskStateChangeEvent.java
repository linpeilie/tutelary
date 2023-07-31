package com.tutelary.client.task.event;

import com.tutelary.client.task.TaskState;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskStateChangeEvent implements Serializable {

    private String taskId;

    private TaskState currentState;

}
