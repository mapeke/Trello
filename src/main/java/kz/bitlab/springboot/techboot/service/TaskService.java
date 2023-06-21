package kz.bitlab.springboot.techboot.service;

import kz.bitlab.springboot.techboot.model.Folders;
import kz.bitlab.springboot.techboot.model.Task;
import kz.bitlab.springboot.techboot.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task addTask(Task task, Long folderId) {

        Folders folder = new Folders();
        folder.setId(folderId);
        task.setFolder(folder);
        return taskRepository.save(task);
    }

    public Task saveTask(Task task){
        return taskRepository.save(task);
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }
    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByFolderId(Long folderId) {
        return taskRepository.findByFolderId(folderId);
    }

}
