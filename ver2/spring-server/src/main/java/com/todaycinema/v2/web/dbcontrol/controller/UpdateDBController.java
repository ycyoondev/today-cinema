package com.todaycinema.v2.web.dbcontrol.controller;

import com.todaycinema.v2.web.dbcontrol.dto.DbResultResponse;
import com.todaycinema.v2.web.dbcontrol.service.UpdateDBService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "DBcontrol")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class UpdateDBController {
    private final UpdateDBService updateDBService;

    @PostMapping("/update-movies/{maxPageNum}")
    public ResponseEntity<DbResultResponse> updateDb(@PathVariable("maxPageNum") int maxPageNum) {
        return ResponseEntity.ok(updateDBService.updateDb(maxPageNum));
    }
}
