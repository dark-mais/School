package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyService {
    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Autowired
    private FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Called method: createFaculty");
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long id) {
        logger.info("Called method: getFaculty");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty updateFaculty(Long id, Faculty faculty) {
        logger.info("Called method: updateFaculty");
        faculty.setId(id);
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(Long id) {
        logger.info("Called method: deleteFaculty");
        facultyRepository.deleteById(id);
        return null;
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Called method: getAllFaculties");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByColor(String color) {
        logger.info("Called method: findByColor");
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> searchFaculties(String name, String color) {
        logger.info("Called method: searchFaculties");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }
}