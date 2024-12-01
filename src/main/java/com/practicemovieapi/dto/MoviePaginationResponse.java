package com.practicemovieapi.dto;

import java.util.List;

public record MoviePaginationResponse(List<MovieDto> moviedto,
                                      Integer PageNum,
                                      Integer PageSize,
                                      long totalElements,
                                      int totalPages,
                                      boolean isLast) {
}
