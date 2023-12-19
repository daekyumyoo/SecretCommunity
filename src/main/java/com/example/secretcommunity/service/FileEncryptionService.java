package com.example.secretcommunity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import java.util.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileEncryptionService {

  /*  @Value("${encryption.password}")
    private String password; // 암호화에 사용할 비밀번호

    @Value("${encryption.salt}")
    private String salt; // 암호화에 사용할 salt
*/
    public void encryptFile(MultipartFile inputFile, File outputFile, String password, String salt) throws IOException {

        try (InputStream inputStream = inputFile.getInputStream()) {
            byte[] fileBytes = inputStream.readAllBytes();
            salt = KeyGenerators.string().generateKey();
            log.info("솔트 : " + salt);
            log.info("솔트 : " + salt.getBytes());
            BytesEncryptor encryptor = Encryptors.standard(password, salt);
            byte[] encryptedBytes = encryptor.encrypt(fileBytes);

            try (OutputStream outputStream = Files.newOutputStream(outputFile.toPath())) {
                outputStream.write(salt.getBytes());
                outputStream.write(encryptedBytes);
            }
        }
    }

    /*public byte[] decryptFile(Path encryptedFilePath) throws IOException {

        byte[] encryptedBytes = Files.readAllBytes(encryptedFilePath);
        TextEncryptor textEncryptor = Encryptors.text(password, salt);
        return textEncryptor.decrypt(new String(encryptedBytes)).getBytes();
    }*/
    public String decryptFile(File encryptedFile, String password) throws IOException {
        try (InputStream inputStream = Files.newInputStream(encryptedFile.toPath())) {
            // 처음 32바이트를 읽어와서 salt 값 추출
            byte[] saltBytes = new byte[16];
            inputStream.read(saltBytes);
            String salt = new String(saltBytes);

            // 나머지 부분을 읽어와서 복호화
            byte[] encryptedBytes = inputStream.readAllBytes();
            BytesEncryptor decryptor = Encryptors.standard(password, salt);
            return Base64.getEncoder().encodeToString(decryptor.decrypt(encryptedBytes));
        }
    }

}
