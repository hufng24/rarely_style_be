package com.example.rarelystylebe.app.dtos.response;

import com.example.rarelystylebe.app.dtos.response.values.FileUrls;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {

    private List<FileUrls> file;
}