package com.melodify.controller;

import com.melodify.controller.dto.request.AccountRequest;
import com.melodify.controller.dto.request.CustomAccountRequest;
import com.melodify.datasource.entity.UserEntity;
import com.melodify.service.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class UserController extends GenericController<UserEntity, AccountRequest, UserServiceImpl> {
    private UserServiceImpl service;

    public UserController(String controllerMapping, UserServiceImpl service) {
        super(controllerMapping, service);
    }

    @PostMapping
    public ResponseEntity<UserEntity> post(@RequestBody CustomAccountRequest request) {
        return ResponseEntity.ok(service.create(request));
    }
}
