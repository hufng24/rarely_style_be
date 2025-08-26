package com.example.rarelystylebe.domain.services.Impl;

import com.eps.shared.models.exceptions.ResponseException;
import com.example.rarelystylebe.app.dtos.request.ImageRequest;
import com.example.rarelystylebe.app.dtos.response.ImageResponse;
import com.example.rarelystylebe.app.dtos.response.values.FileUrls;
import com.example.rarelystylebe.domain.entities.Image;
import com.example.rarelystylebe.domain.exceptions.ErrorMessage;
import com.example.rarelystylebe.domain.repositories.ImageRepository;
import com.example.rarelystylebe.domain.services.ImageUploadService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {
    @Autowired
    private ImageRepository imageRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${url.port}")
    private String portServer;

    @Value("${url.host}")
    private String hostServer;

    @Override
    public ImageResponse uploadImage(ImageRequest request) throws IOException {
        List<Image> images = new ArrayList<>();
        List<FileUrls> files = new ArrayList<>();

        if (request.getFiles() == null || request.getFiles().isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessage.FILE_NOT_FOUND);
        }

        List<String> codes = upLoadToServer(request.getFiles());

        for (int i = 0; i < request.getFiles().size(); i++) {
            MultipartFile file = request.getFiles().get(i);
            if (file != null && !file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String code = codes.get(i);

                Image image =
                        createAndSaveImage(code, originalFilename);
                images.add(image);

                FileUrls fileResponse = new FileUrls();
                fileResponse.setUrl(image.getUrl());
                files.add(fileResponse);
            }
        }

        ImageResponse response = new ImageResponse();
        response.setFile(files);


        return response;
    }


    private List<String> upLoadToServer(List<MultipartFile> files) throws IOException {
        if (files == null || files.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessage.FILE_NOT_FOUND);
        }

        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        List<String> codes = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                String code = RandomStringUtils.randomAlphanumeric(10);
                String originalFilename = file.getOriginalFilename();
                Path fileSave = path.resolve(code + "-" + Objects.requireNonNull(originalFilename));
                try (InputStream is = file.getInputStream()) {
                    Files.copy(is, fileSave, StandardCopyOption.REPLACE_EXISTING);
                }
                codes.add(code);
            }
        }

        return codes;
    }

    private String generateImageUrl(String code, String originalFilename) {
        return hostServer + ":" + portServer + "/" + code + "-" + originalFilename;
    }

    private Image createAndSaveImage(
            String code, String originalFilename) {
        Image image =
                Image.builder()
                        .url(generateImageUrl(code, originalFilename))
                        .build();
        return imageRepository.save(image);
    }


}
