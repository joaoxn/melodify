package com.melodify.controller;

import com.melodify.controller.dto.request.AccountRequest;
import com.melodify.controller.dto.request.RoleRequest;
import com.melodify.controller.dto.response.AccountResponse;
import com.melodify.datasource.entity.AccountEntity;
import com.melodify.service.AccountServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("account")
@Slf4j
public class AccountController extends GenericController<AccountEntity, AccountRequest, AccountResponse, AccountServiceImpl> {
    private final AccountServiceImpl service;

    public AccountController(AccountServiceImpl service) {
        super(service);
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> postRole(@PathVariable Long id, RoleRequest request) {
        return ResponseEntity.ok(service.addRole(id, request.role()));
    }
}
