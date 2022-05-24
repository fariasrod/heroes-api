package com.heroes.infrastructure.configuration.helper;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AspectLogging {
    private String requestId;
    private String host;
    private String method;
    private String uri;
    private String api;
    private String arguments;
    private LocalDateTime time;

    @Setter
    private long executionTime;
}
