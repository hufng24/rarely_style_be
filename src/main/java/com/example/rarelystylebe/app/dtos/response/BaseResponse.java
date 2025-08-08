package com.example.rarelystylebe.app.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseResponse {

    private Long id;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
