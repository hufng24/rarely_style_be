package com.example.rarelystylebe.domain.services.Impl;

import com.eps.shared.models.exceptions.ResponseException;
import com.example.rarelystylebe.app.dtos.filter.ColorParam;
import com.example.rarelystylebe.app.dtos.request.ColorRequest;
import com.example.rarelystylebe.app.dtos.response.ColorResponse;
import com.example.rarelystylebe.domain.entities.Color;
import com.example.rarelystylebe.domain.exceptions.ErrorMessage;
import com.example.rarelystylebe.domain.repositories.ColorRepository;
import com.example.rarelystylebe.domain.repositories.specification.ColorSpecification;
import com.example.rarelystylebe.domain.services.ColorService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;

    private final ObjectMapper objectMapper;

    private final String PREFIX_CODE = "CL";
    @Override
    public ColorResponse create(ColorRequest colorRequest) {
        Color color = objectMapper.convertValue(colorRequest, Color.class);
        color.setCode(PREFIX_CODE + colorRepository.getNextSeq());
        return  objectMapper.convertValue(colorRepository.save(color), ColorResponse.class);
    }

    @Override
    public ColorResponse update(Long id, ColorRequest colorRequest) throws JsonMappingException {
        Color color = findById(id);
        objectMapper.updateValue(color,colorRequest);
        return objectMapper.convertValue(colorRepository.save(color), ColorResponse.class);
    }

    @Override
    public void delete(Long id) {
        Color color = findById(id);
        color.setIsDeleted(true);
        colorRepository.save(color);
    }

    @Override
    public Color findById(Long id) {
        return colorRepository.findById(id).orElseThrow(()->new ResponseException(HttpStatus.NOT_FOUND, ErrorMessage.COLOR_NOT_FOUND));
    }

    @Override
    public Page<ColorResponse> filter(ColorParam colorParam, Pageable pageable) {
        Specification colorSpec = ColorSpecification.filterSpec(colorParam);
        Page<Color> colorPage = colorRepository.findAll(colorSpec, pageable);
        return colorPage.map(color -> objectMapper.convertValue(color, ColorResponse.class));
    }




}
