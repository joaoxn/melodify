package com.melodify.controller.dto.request;

import java.io.File;

public record MusicRequest(
        String name,
        String artistName,
        String artistAccount,
        String[] genres,
        Boolean downloadOnlySelected,
        File audio
) {
}
