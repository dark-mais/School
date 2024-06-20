package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/avatars")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadAvatar(@RequestParam MultipartFile file, @RequestParam Long studentId) {
        try {
            avatarService.saveAvatar(file, studentId);
            return ResponseEntity.ok("Avatar uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading avatar");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long id) {
        Avatar avatar = avatarService.getAvatar(id);
        if (avatar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        return new ResponseEntity<>(avatar.getData(), headers, HttpStatus.OK);
    }

    @GetMapping("/file/{studentId}")
    public ResponseEntity<byte[]> getAvatarFromFile(@PathVariable Long studentId) {
        try {
            // Предполагаем, что файлы хранятся в папке avatars и названы по шаблону studentId_filename
            File folder = new File("avatars");
            File[] files = folder.listFiles((dir, name) -> name.startsWith(studentId + "_"));
            if (files == null || files.length == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            File avatarFile = files[0];
            byte[] data = avatarService.getAvatarFromFile(avatarFile.getPath());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // замените на правильный тип, если нужно
            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
