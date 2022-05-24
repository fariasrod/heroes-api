package com.heroes.factory;

import com.example.app.model.HeroResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heroes.core.domain.HeroDomain;
import com.heroes.infrastructure.entity.Hero;

public class HeroFactory {

    public static HeroDomain domain() {
        return HeroDomain.builder()
                .name("Tony Stark")
                .character("IRON MAN")
                .build();
    }

    public static Hero entity() {
        return Hero.builder()
                .id(1)
                .name("Tony Stark")
                .character("IRON MAN")
                .build();
    }

    public static HeroResource resource() {
        HeroResource resource = new HeroResource();
        resource.setId(1);
        resource.setName("Tony Stark");
        resource.setCharacter("IRON MAN");
        return resource;
    }

    public static String toJson(HeroDomain domain) throws JsonProcessingException {
        return new ObjectMapper()
                .writer()
                .withDefaultPrettyPrinter()
                .writeValueAsString(domain);
    }

    public static String toJson(HeroResource resource) throws JsonProcessingException {
        return new ObjectMapper()
                .writer()
                .withDefaultPrettyPrinter()
                .writeValueAsString(resource);
    }
}
