package com.melodify.controller.dto.request;

public record ProfileRequest(Long accountId, String firstName, String lastName, String email) {
}
