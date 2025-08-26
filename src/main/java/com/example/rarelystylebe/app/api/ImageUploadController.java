package com.example.rarelystylebe.app.api;

import java.io.IOException;

import com.example.rarelystylebe.app.dtos.request.ImageRequest;
import com.example.rarelystylebe.app.dtos.response.ImageResponse;
import com.example.rarelystylebe.domain.services.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/images")
public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping
    public ResponseEntity<ImageResponse> uploadImage(@ModelAttribute ImageRequest dto) throws IOException {
        return ResponseEntity.ok(imageUploadService.uploadImage(dto));
    }


}