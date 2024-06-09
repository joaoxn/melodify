package com.melodify.controller.dto.response;

import java.util.List;

public record AccountResponse(String login, List<String> rolesNames) {
}
