package kz.bitlab.springboot.techboot.repository;

import kz.bitlab.springboot.techboot.model.TaskCategories;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskCategoriesRepository extends JpaRepository<TaskCategories, Long> {
}
