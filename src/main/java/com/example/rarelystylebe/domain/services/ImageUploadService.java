package com.example.rarelystylebe.domain.services;

import com.example.rarelystylebe.app.dtos.request.ImageRequest;
import com.example.rarelystylebe.app.dtos.response.ImageResponse;

import java.io.IOException;

public interface ImageUploadService {
    ImageResponse uploadImage(ImageRequest req) throws IOException;
}
