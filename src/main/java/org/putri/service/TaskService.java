package org.putri.service;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.putri.dto.task.TaskDeleteDto;
import org.putri.dto.task.TaskListDto;
import org.putri.dto.task.TaskUpsertDto;
import org.putri.entity.Task;
import org.putri.repository.TaskRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TaskService {
    @Inject
    TaskRepository taskRepository;

    ModelMapper modelMapper = new ModelMapper();

    public List<TaskListDto> getAllTask() {
        List<Task> tasks = taskRepository.find("deleted_at is null").list();
        return tasks.stream()
                .map(task -> modelMapper.map(task, TaskListDto.class))
                .toList();
    }

    public void createTask(TaskUpsertDto taskUpsertDto) {
        Task task = new Task();
        task.title = taskUpsertDto.getTitle();
        task.description = taskUpsertDto.getDescription();
        task.dueDate = taskUpsertDto.getDueDate();
        task.status = taskUpsertDto.getStatus();
        task.userId = taskUpsertDto.getUserId();
        task.created_at = new java.util.Date();

        taskRepository.persist(task);
        taskUpsertDto.setTaskId(task.id);
    }

    public void updateTask(TaskUpsertDto taskUpsertDto, Task task) {
        task.id = taskUpsertDto.getTaskId();
        task.title = taskUpsertDto.getTitle();
        task.description = taskUpsertDto.getDescription();
        task.dueDate = taskUpsertDto.getDueDate();
        task.status = taskUpsertDto.getStatus();
        task.userId = taskUpsertDto.getUserId();
        task.updated_at = new java.util.Date();

    }

    public void deleteTask(Task task, TaskDeleteDto taskDeleteDto) {
        task.deleted_at = new Date();
        task.id = taskDeleteDto.getTaskId();
    }
}
