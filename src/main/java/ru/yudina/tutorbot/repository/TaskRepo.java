package ru.yudina.tutorbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yudina.tutorbot.entity.task.Task;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
}
