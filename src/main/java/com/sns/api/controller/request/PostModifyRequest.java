package com.sns.api.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostModifyRequest {
    private String title;
    private String body;
}
