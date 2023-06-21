package kz.bitlab.springboot.techboot.service;

import kz.bitlab.springboot.techboot.model.TaskCategories;
import kz.bitlab.springboot.techboot.repository.TaskCategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TaskCategoriesService {

    private final TaskCategoriesRepository taskCategoriesRepository;

    public List<TaskCategories> getTaskCategories(){
        return taskCategoriesRepository.findAll();
    }

    public TaskCategories getTaskCategory(Long id){
        return taskCategoriesRepository.findById(id).orElse(null);
    }

    public TaskCategories addTaskCategory(TaskCategories category){
        return taskCategoriesRepository.save(category);
    }

    public TaskCategories saveCategory(TaskCategories category){
        return taskCategoriesRepository.save(category);
    }

    public void deleteTaskCategory(Long id){
        taskCategoriesRepository.deleteById(id);
    }
}
