package com.example.rarelystylebe.domain.services.Impl;

import com.eps.shared.models.exceptions.ResponseException;
import com.example.rarelystylebe.app.dtos.filter.MaterialParam;
import com.example.rarelystylebe.app.dtos.request.MaterialRequest;
import com.example.rarelystylebe.app.dtos.response.MaterialResponse;
import com.example.rarelystylebe.app.dtos.response.projection.MaterialProjection;
import com.example.rarelystylebe.domain.entities.Material;
import com.example.rarelystylebe.domain.exceptions.ErrorMessage;
import com.example.rarelystylebe.domain.repositories.MaterialRepository;
import com.example.rarelystylebe.domain.repositories.specification.MaterialSpecification;
import com.example.rarelystylebe.domain.services.MaterialService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    private final ObjectMapper objectMapper;

    private final String PREFIX_CODE = "MT";


    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("123456"));
    }

    @Override
    @Transactional
    public MaterialResponse create(MaterialRequest materialRequest) {
        Material material = objectMapper.convertValue(materialRequest, Material.class);
        material.setCode(PREFIX_CODE + materialRepository.getNextSeq());
        return objectMapper.convertValue(materialRepository.save(material), MaterialResponse.class);
    }

    @Override
    @Transactional
    public MaterialResponse update(Long id, MaterialRequest materialRequest) throws JsonMappingException {
        Material material = findById(id);
        objectMapper.updateValue(material,materialRequest);
        return objectMapper.convertValue(materialRepository.save(material), MaterialResponse.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Material material = findById(id);
        material.setIsDeleted(true);
        materialRepository.save(material);
    }

    @Override
    public Material findById(Long id) {
        return materialRepository
                .findById(id)
                .orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND , ErrorMessage.MATERIAL_NOT_FOUND));
    }

//    @Override
//    public MaterialProjection findProjectionById(Long id) {
//        return materialRepository
//                .findByIdResponse(id)
//                .orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND , ErrorMessage.MATERIAL_NOT_FOUND));
//    }


    @Override
    public Page<MaterialResponse> filter(MaterialParam materialParam, Pageable pageable) {
        Specification<Material> materialSpec = MaterialSpecification.filterSpec(materialParam);
        Page<Material> materialPage = materialRepository.findAll(materialSpec,pageable);
        return materialPage.map(material -> objectMapper.convertValue(material, MaterialResponse.class));
    }


}
