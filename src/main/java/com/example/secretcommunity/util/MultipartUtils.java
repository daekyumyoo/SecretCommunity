package com.example.secretcommunity.util;

import com.example.secretcommunity.service.FileEncryptionService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@Slf4j
@UtilityClass
public class MultipartUtils {

    FileEncryptionService fileEncryptionService = new FileEncryptionService();

    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
            "image/jpeg",
            "image/png"
    );

    private static final List<String> ALL_TYPES = Arrays.asList(

    );

    public static boolean isImage(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        return contentType != null && ALLOWED_IMAGE_TYPES.contains(contentType);
    }

    public static File saveImage(MultipartFile multipartFile, File directory, String fileName, String password, String salt) throws Exception {
        return saveFile(multipartFile, directory, fileName, password, salt);
    }

    private static File saveFile(MultipartFile multipartFile, File directory, String fileName, String password, String salt) throws Exception {
        /*log.info(multipartFile.getOriginalFilename() + " -> " + fileName + " 저장 시작");

        int dotIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
        String fileExtension = multipartFile.getOriginalFilename().substring(dotIndex + 1);

        if (!directory.exists() && !directory.mkdirs()) {
            throw new Exception("디렉토리 생성 실패: " + directory.getAbsolutePath());
        }

        File newFile = new File(directory, fileName + "." + fileExtension);

        // 동일한 이름의 파일이 존재하는 경우 이름을 변경
        int count = 1;
        while (newFile.exists()) {
            newFile = new File(directory, fileName + "_" + count + "." + fileExtension);
            count++;
        }

        multipartFile.transferTo(newFile);

        log.info(multipartFile.getOriginalFilename() + " -> " + fileName + " 저장 완료 (" + newFile.getAbsolutePath() + ")");

        return newFile;*/
        int dotIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
        String fileExtension = multipartFile.getOriginalFilename().substring(dotIndex + 1);

        if (!directory.exists() && !directory.mkdirs()) {
            throw new Exception("디렉토리 생성 실패: " + directory.getAbsolutePath());
        }

        File newFile = new File(directory, fileName + "." + fileExtension);

        // 동일한 이름의 파일이 존재하는 경우 이름을 변경
        int count = 1;
        while (newFile.exists()) {
            newFile = new File(directory, fileName + "_" + count + "." + fileExtension);
            count++;
        }

        // 파일을 암호화하여 저장
        fileEncryptionService.encryptFile(multipartFile, newFile, password, salt);
        return newFile;
    }

}