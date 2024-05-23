package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long idCounter = 1L;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(idCounter++);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Optional<Faculty> getFaculty(Long id) {
        return Optional.ofNullable(faculties.get(id));
    }

    public Collection<Faculty> getAllFaculties() {
        return faculties.values();
    }

    public Faculty updateFaculty(Long id, Faculty faculty) {
        if (faculties.containsKey(id)) {
            faculty.setId(id);
            faculties.put(id, faculty);
            return faculty;
        }
        return null;
    }

    public Faculty deleteFaculty(Long id) {
        return faculties.remove(id);
    }

    public Collection<Faculty> findFacultiesByColor(String color) {
        return faculties.values().stream()
                .filter(faculty -> faculty.getColor().equalsIgnoreCase(color))
                .toList();
    }
}
