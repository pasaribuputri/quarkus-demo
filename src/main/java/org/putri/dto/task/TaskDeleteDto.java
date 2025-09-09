package org.putri.dto.task;

import lombok.Data;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@Schema(description = "Task")
public class TaskDeleteDto {

    @Schema(description = "Unique identifier of the task", examples = "1")
    private Long taskId;

}
