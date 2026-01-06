package com.itwizard.starter.modules.sample.controller;

import com.itwizard.starter.exception.UnauthorizedException;
import com.itwizard.starter.modules.sample.dto.request.SampleRequestDto;
import com.itwizard.starter.modules.sample.dto.response.SampleResponseDto;
import com.itwizard.starter.modules.sample.service.SampleService;
import com.itwizard.starter.util.ApiResponse;
import com.itwizard.starter.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/sample")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    @PostMapping
    public ResponseEntity<ApiResponse> createSample(@RequestBody SampleRequestDto request) {

            try {
                if (Objects.equals(request.getName(), "unauth")) {
                    throw new UnauthorizedException("UnauthorizedException errrorrrrr");
                }

                SampleResponseDto resData = sampleService.createSample(request);

                return ResponseUtil.created("Sample created successfully", resData);
            }
            catch (Exception e) {
                return ResponseUtil.error("error.sample.create");
            }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getSampleById(@PathVariable Long id) {
        SampleResponseDto resData = sampleService.getSampleById(id);
        return ResponseUtil.success("Sample retrieved successfully", resData);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllSamples() {
        List<SampleResponseDto> resData = sampleService.getAllSamples();
        return ResponseUtil.success("Samples retrieved successfully", resData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateSample(@PathVariable Long id, @RequestBody SampleRequestDto request) {
        SampleResponseDto resData = sampleService.updateSample(id, request);
        return ResponseUtil.success("Sample updated successfully", resData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSample(@PathVariable Long id) {
        sampleService.deleteSample(id);
        return ResponseUtil.success("Sample deleted successfully");
    }
}

