package org.putri.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.modelmapper.ModelMapper;
import org.putri.dto.task.TaskDeleteDto;
import org.putri.dto.task.TaskListDto;
import org.putri.dto.task.TaskUpsertDto;
import org.putri.entity.Task;
import org.putri.repository.TaskRepository;
import org.putri.response.ApiResponse;

import java.util.Date;
import java.util.List;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {
    
    @Inject
    TaskRepository taskRepository;
    
    ModelMapper modelMapper = new ModelMapper();
    @GET
    @Operation(summary = "Get All task", description = "Semua Detail task")
    public Response getAllTasks() {
        List<Task> tasks = taskRepository.find("deleted_at is null").list();
        List<TaskListDto> dtos = tasks.stream()
                .map(task -> modelMapper.map(task, TaskListDto.class))
                .toList();
        return Response.ok(new ApiResponse<>("Success", 200, dtos)).build();
    }

    @POST
    @Transactional
    @Operation(summary = "Create new task", description = "Membuat task baru dengan detail yang diberikan")
    @APIResponse(responseCode = "201", description = "Succes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskUpsertDto.class)))
    public Response createTask(TaskUpsertDto taskDto) {
        Task task = new Task();
        task.title = taskDto.getTitle();
        task.description = taskDto.getDescription();
        task.dueDate = taskDto.getDueDate();
        task.status = taskDto.getStatus();
        task.userId = taskDto.getUserId();
        task.created_at = new Date();

        taskRepository.persist(task);
        taskDto.setTaskId(task.id);
        return Response.ok(new ApiResponse<>("Success", 201, taskDto)).build();

    }

    @PUT
    @Transactional
    @Operation(summary = "Update task", description = "Menubah task yang sudah terdaftar")
    @APIResponse(responseCode = "200", description = "Succes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskUpsertDto.class)))
    public Response updateTask(TaskUpsertDto taskDto) {
        Task task = taskRepository.findById(taskDto.getTaskId());

        if (task == null) {
            return Response.ok(new ApiResponse<>(taskDto.getTitle() + " not found", 404, null)).build();
        }

        task.id = taskDto.getTaskId();
        task.title = taskDto.getTitle();
        task.description = taskDto.getDescription();
        task.dueDate = taskDto.getDueDate();
        task.status = taskDto.getStatus();
        task.userId = taskDto.getUserId();
        task.updated_at = new Date();

        return Response.ok(new ApiResponse<>("Success", 200, taskDto)).build();

    }

    @DELETE
    @Transactional
    @Operation(summary = "Delete task", description = "Menghapus task yang sudah terdaftar")
    @APIResponse(responseCode = "200", description = "Succes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDeleteDto.class)))
    public Response deleteTask(TaskDeleteDto taskDeleteDto) {
        Task task = taskRepository.findById(taskDeleteDto.getTaskId());

        if (task == null) {
            return Response.ok(new ApiResponse<>("Task not found", 404, null)).build();
        }

        task.id = taskDeleteDto.getTaskId();
        task.deleted_at = new Date();

        return Response.ok(new ApiResponse<>("Success", 200, taskDeleteDto)).build();

    }
}
