package com.melodify.controller.dto.response;

import com.melodify.datasource.entity.ProfileEntity;
import com.melodify.datasource.entity.AccountEntity;

public record UserResponse(AccountEntity account, ProfileEntity profile) {
}
