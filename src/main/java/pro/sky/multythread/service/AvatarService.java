package pro.sky.multythread.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.multythread.model.Avatar;
import pro.sky.multythread.model.Student;
import pro.sky.multythread.repository.AvatarRepository;
import pro.sky.multythread.repository.AvatarsPagins;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        logger.info("call AvatarService method");
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {

        logger.info("call uploadAvatar method");

        Student student = studentService.getStudent(studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatarRepository.save(avatar);
    }

    private String getExtension(String fileName) {
        logger.info("call getExtensions method");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Avatar findAvatar(Long studentId) {
        logger.info("call findAvatar method");
        return avatarRepository.findByStudentId(studentId).orElseThrow();
    }

//    private byte[] generateSmallAvatar(Path filePath) throws IOException {
//        try (InputStream is = Files.newInputStream(filePath);
//             BufferedInputStream bis = new BufferedInputStream(is, 1024);
//             ByteArrayOutputStream baos = new ByteArrayOutputStream()
//        ) {
//            BufferedImage image = ImageIO.read(bis);
//
//            int height = image.getHeight() / (image.getWidth() / 100);
//            BufferedImage smallAvatar = new BufferedImage(100, height, image.getType());
//            Graphics2D graphics = smallAvatar.createGraphics();
//            graphics.drawImage(image, 0, 0, 100, height, null);
//            graphics.dispose();
//
//            ImageIO.write(smallAvatar, getExtension(filePath.getFileName().toString()), baos);
//            return baos.toByteArray();
//        }
//    }



    public Collection<AvatarsPagins> downloadAllAvatars(int pageNumber, int pageSize) {
        logger.info("call downloadAllAvatars method");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return avatarRepository.downloadAllAvatars(pageRequest);
    }


}
