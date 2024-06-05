package com.melodify.controller.dto.response;

public record ProfileResponse(String firstName, String lastName, String email, AccountResponse account) {
}
