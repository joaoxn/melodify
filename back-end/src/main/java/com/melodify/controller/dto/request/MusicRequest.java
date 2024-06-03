package com.melodify.controller.dto.request;

import java.io.File;

public record MusicRequest(String name, String artistName, String artistUser, String[] genres, Boolean downloadOnlySelected, File audio) {
}
