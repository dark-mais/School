package ru.hogwarts.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AvatarService {
    @Autowired
    private AvatarRepository avatarRepository;

    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public Avatar saveAvatar(MultipartFile file, Long studentId) throws IOException {
        String filePath = "avatars/" + studentId + "_" + file.getOriginalFilename();
        File avatarFile = new File(filePath);
        file.transferTo(avatarFile);

        Avatar avatar = new Avatar();
        avatar.setFilePath(filePath);
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        // Здесь вы можете установить студента, используя studentId

        return avatarRepository.save(avatar);
    }

    public Avatar getAvatar(Long id) {
        return avatarRepository.findById(id).orElse(null);
    }

    public byte[] getAvatarFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    public Page<Avatar> getAvatars(Pageable pageable) {
        return avatarRepository.findAll(pageable);
    }
}
