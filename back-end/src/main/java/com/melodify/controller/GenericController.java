package com.melodify.controller;

import com.melodify.service.GenericService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
public class GenericController<E, REQ, RES, S extends GenericService<E, REQ, RES>> {
    private final S service;

    @GetMapping("{id}")
    public ResponseEntity<RES> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<List<RES>> get() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<RES> post(@RequestBody REQ request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<RES> put(
            @PathVariable Long id,
            @RequestBody REQ request
    ) {
        return ResponseEntity.ok(service.alter(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
