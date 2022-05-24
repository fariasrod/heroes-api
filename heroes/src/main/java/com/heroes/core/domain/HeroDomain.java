package com.heroes.core.domain;

import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeroDomain {

    @ToString.Exclude
    private Integer id;

    @NotNull(message = "The name cannot be null!")
    private String name;

    @NotNull(message = "The character cannot be null!")
    private String character;
}
