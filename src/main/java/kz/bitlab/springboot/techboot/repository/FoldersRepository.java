package kz.bitlab.springboot.techboot.repository;

import kz.bitlab.springboot.techboot.model.Folders;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface FoldersRepository extends JpaRepository<Folders, Long> {
}
