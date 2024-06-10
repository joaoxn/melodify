package com.melodify.controller.dto.request;

import java.io.File;
import java.util.List;

public record SongRequest(
        String name,
        List<String> artistsNames,
        List<String> artistsLogins,
        List<String> genres,
        List<String> downloadPermissionRoles,
        Boolean downloadOnlySelected,
        File audio
) {
}
