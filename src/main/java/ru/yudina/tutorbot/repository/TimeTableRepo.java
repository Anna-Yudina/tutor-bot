package ru.yudina.tutorbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yudina.tutorbot.entity.timetable.TimeTable;

@Repository
public interface TimeTableRepo extends JpaRepository<TimeTable, Long> {
}
