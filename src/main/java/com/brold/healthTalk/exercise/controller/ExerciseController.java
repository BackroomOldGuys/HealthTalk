package com.brold.healthTalk.exercise.controller;

import com.brold.healthTalk.exercise.dto.request.ExerciseRequest;
import com.brold.healthTalk.exercise.dto.response.ExerciseResponse;
import com.brold.healthTalk.exercise.service.ExerciseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@Tag(name = "Exercise", description = "운동 자동완성 및 관리 API")
@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService service;

    @Autowired
    public ExerciseController(ExerciseService service) {
        this.service = service;
    }

    /** 자동완성 조회 (최대 10개) */
    @GetMapping
    public ResponseEntity<List<ExerciseResponse>> search(@RequestParam("query") String q) {
        List<ExerciseResponse> list = service.searchExercises(q);
        return ResponseEntity.ok(list);
    }

    /** 생성 (관리자) */
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ExerciseRequest req,
                                       UriComponentsBuilder uriBuilder) {
        Integer id = service.createExercise(req);
        URI location = uriBuilder.path("/api/exercises/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    /** 수정 (관리자) */
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id,
                                       @Valid @RequestBody ExerciseRequest req) {
        service.updateExercise(id, req);
        return ResponseEntity.noContent().build();
    }

    /** 삭제 (관리자) */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }
}
