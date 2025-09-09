package org.putri.dto.task;

import lombok.Data;
import java.util.Date;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@Schema(description = "Task")
public class TaskUpsertDto {

    @Schema(description = "Unique identifier of the task", examples = "null")
    private Long taskId;

    @Schema(description = "Judul task", examples = "Task 1")
    private String title;

    @Schema(description = "Description task", examples = "Job about the day")
    private String description;

    @Schema(description = "Status Task (1. Draft, 2.Process, 3.Finish)", examples = "1")
    private Integer status;

    @Schema(description = "Tenggat task", examples = "2025-10-09")
    private Date dueDate;

    @Schema(description = "User identifier of the task", examples = "1")
    private Integer userId;

}
