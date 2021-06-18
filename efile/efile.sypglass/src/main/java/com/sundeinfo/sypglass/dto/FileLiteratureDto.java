package com.sundeinfo.sypglass.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class FileLiteratureDto {

    private List<FileAccessoryHistoryDto> fileAccessoryHistoryDto;

    private List<FileMetadataHistoryDto> fileMetadataHistoryDto;
}
