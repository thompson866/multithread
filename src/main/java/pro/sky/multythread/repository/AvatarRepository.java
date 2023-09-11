package pro.sky.multythread.repository;



import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.multythread.model.Avatar;

import java.util.Collection;
import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);



//
    @Query(value = "select id, file_path as path, file_size as size, media_type as type, student_id as studentId from avatar", nativeQuery = true)
    Collection<AvatarsPagins> downloadAllAvatars(PageRequest pageRequest);

}
