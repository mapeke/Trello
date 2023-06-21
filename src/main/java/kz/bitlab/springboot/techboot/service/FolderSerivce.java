package kz.bitlab.springboot.techboot.service;

import kz.bitlab.springboot.techboot.model.Folders;
import kz.bitlab.springboot.techboot.model.TaskCategories;
import kz.bitlab.springboot.techboot.repository.FoldersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FolderSerivce {

    private final FoldersRepository foldersRepository;
    private final TaskCategoriesService taskCategoriesService;

    public Folders addFolder(Folders folder){
        return foldersRepository.save(folder);
    }

    public List<Folders> getFolders(){
        return foldersRepository.findAll();
    }

    public Folders getFolder(Long id){
        return foldersRepository.findById(id).orElse(null);
    }

    public Folders saveFolder(Folders folder){
        return foldersRepository.save(folder);
    }

    public void assignCategory(Long folderId, Long categoryId){
        Folders folder = getFolder(folderId);
        TaskCategories category = taskCategoriesService.getTaskCategory(categoryId);

        if(folder.getCategories() != null && folder.getCategories().size() > 0){
            if(!folder.getCategories().contains(category)) {
                folder.getCategories().add(category);
            }
        }else{
            List<TaskCategories> taskCategoriesList = new ArrayList<>();
            taskCategoriesList.add(category);
            folder.setCategories(taskCategoriesList);
        }

        foldersRepository.save(folder);
    }

    public void unassignCategory(Long folderId, Long categoryId){
        Folders folder = getFolder(folderId);
        TaskCategories category = taskCategoriesService.getTaskCategory(categoryId);

        if(folder.getCategories() != null && folder.getCategories().size() > 0){
            folder.getCategories().remove(category);
        }

        foldersRepository.save(folder);
    }

    public void deleteFolder(Long folderId, List<TaskCategories> categories){
        Folders folder = getFolder(folderId);
        if(folder.getCategories() != null && folder.getCategories().size() > 0){
            folder.getCategories().removeAll(categories);
        }

        foldersRepository.delete(folder);
    }

}
