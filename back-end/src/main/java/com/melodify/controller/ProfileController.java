package com.melodify.controller;

import com.melodify.controller.dto.request.ProfileRequest;
import com.melodify.controller.dto.response.ProfileResponse;
import com.melodify.datasource.entity.ProfileEntity;
import com.melodify.service.ProfileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("profile")
@Slf4j
//TODO: Create ProfileRequest and ProfileResponse
public class ProfileController extends GenericController<ProfileEntity, ProfileRequest, ProfileResponse, ProfileServiceImpl> {
    public ProfileController(ProfileServiceImpl service) {
        super(service);
    }
}
