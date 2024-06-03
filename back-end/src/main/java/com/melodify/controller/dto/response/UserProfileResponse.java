package com.melodify.controller.dto.response;

import com.melodify.datasource.entity.ProfileEntity;
import com.melodify.datasource.entity.UserEntity;

public record UserProfileResponse(UserEntity user, ProfileEntity profile) {
}
