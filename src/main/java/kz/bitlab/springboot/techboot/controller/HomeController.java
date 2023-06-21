package kz.bitlab.springboot.techboot.controller;

import kz.bitlab.springboot.techboot.model.Folders;
import kz.bitlab.springboot.techboot.model.Task;
import kz.bitlab.springboot.techboot.model.TaskCategories;
import kz.bitlab.springboot.techboot.service.FolderSerivce;
import kz.bitlab.springboot.techboot.service.TaskCategoriesService;
import kz.bitlab.springboot.techboot.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final FolderSerivce folderSerivce;
    private final TaskCategoriesService taskCategoriesService;
    private final TaskService taskService;
    private Boolean error = false;

    @GetMapping(value = "/")
    public String indexPage(Model model){
        model.addAttribute("folders", folderSerivce.getFolders());
        return "index";
    }

    @GetMapping(value = "/categories")
    public String categoriesPage(Model model){
        model.addAttribute("categories", taskCategoriesService.getTaskCategories());
        return "categories";
    }

    @PostMapping(value = "/add-folder")
    public String addFolder(Folders folder){
        folderSerivce.addFolder(folder);
        return "redirect:/";
    }

    @PostMapping(value = "/add-category")
    public String addCategory(TaskCategories category){
        taskCategoriesService.addTaskCategory(category);
        return "redirect:/categories";
    }

    @GetMapping(value = "/category-details/{category_id}")
    public String categoryDetails(@PathVariable(name = "category_id") Long id, Model model){
        TaskCategories category = taskCategoriesService.getTaskCategory(id);
        model.addAttribute("category", category);

        return "category-details";
    }

    @PostMapping(value = "/save-category")
    public String saveCategory(@RequestParam(name = "task_id") Long id,
                               TaskCategories category){
        category.setId(id);
        taskCategoriesService.saveCategory(category);
        return "redirect:/categories";
    }

    @PostMapping(value = "/delete-category")
    public String deleteCategory(@RequestParam(name = "category_id") Long id){
        taskCategoriesService.deleteTaskCategory(id);
        return "redirect:/categories";
    }

    @GetMapping(value = "/details/{folder_id}")
    public String folderDetails(@PathVariable(name = "folder_id") Long id, Model model){
        Folders folder = folderSerivce.getFolder(id);
        model.addAttribute("folder", folder);

        model.addAttribute("error", error);
        error = false;

        List<TaskCategories> categories = taskCategoriesService.getTaskCategories();
        categories.removeAll(folder.getCategories());
        model.addAttribute("categories", categories);

        List<Task> tasks = taskService.getTasksByFolderId(id);
        model.addAttribute("tasks", tasks);

        return "details";
    }

    @GetMapping(value = "/task-details/{task_id}")
    public String taskDetails(@PathVariable(name = "task_id") Long taskId, Model model){

        Task task = taskService.getTask(taskId);
        model.addAttribute("task", task);

        Folders folder = task.getFolder();
        model.addAttribute("folder", folder);

        return "task-details";
    }

    @PostMapping(value ="/delete-folder")
    public String deleteFolder(@RequestParam(name = "folder_id") Long folderId){

        List<TaskCategories> categories = taskCategoriesService.getTaskCategories();
        if(taskService.getTasksByFolderId(folderId).size() != 0){

            error = true;
            return "redirect:/details/"+folderId;
        }else {
            folderSerivce.deleteFolder(folderId, categories);
            return "redirect:/";
        }
    }

    @PostMapping(value = "/save-task")
    public String saveTask(@RequestParam(name = "task_id") Long taskId, Task task,
                           @RequestParam(name= "folder_id") Long folderId){
        task.setId(taskId);
        Folders folder =  new Folders();
        folder.setId(folderId);
        task.setFolder(folder);
        taskService.saveTask(task);
        return "redirect:/details/"+folderId;
    }

    @PostMapping(value = "/delete-task")
    public String deleteTask(@RequestParam(name = "task_id") Long taskId,
                             @RequestParam(name= "folder_id") Long folderId){
        taskService.deleteTask(taskId);
        return "redirect:/details/"+folderId;
    }

    @PostMapping(value = "/add-task")
    public String addTask(Task task,
                          @RequestParam(name= "folder_id") Long folderId){
        taskService.addTask(task, folderId);
        return "redirect:/details/"+folderId;
    }

    @PostMapping(value = "/assign-category")
    public String assignCategory(@RequestParam(name = "folder_id") Long folderId,
                                 @RequestParam(name = "category_id") Long categoryId){

        folderSerivce.assignCategory(folderId, categoryId);

        return "redirect:/details/"+folderId;
    }

    @PostMapping(value = "/unassign-category")
    public String unassignCategory(@RequestParam(name = "folder_id") Long folderId,
                                   @RequestParam(name = "category_id") Long categoryId){

        folderSerivce.unassignCategory(folderId, categoryId);

        return "redirect:/details/"+folderId;
    }
}
