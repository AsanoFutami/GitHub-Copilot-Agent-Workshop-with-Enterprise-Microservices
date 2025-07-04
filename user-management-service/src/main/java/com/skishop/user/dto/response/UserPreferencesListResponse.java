package com.skishop.user.dto.response;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * User preferences list response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferencesListResponse {

    private List<UserPreferenceResponse> preferences;
    private int totalCount;
    private int page;
    private int size;
}
