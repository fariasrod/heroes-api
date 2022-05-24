package com.heroes.core.service;

import com.heroes.core.domain.HeroDomain;

import java.util.List;

public interface HeroService {

    HeroDomain findById(Integer id);

    List<HeroDomain> findAll(String name, String character);

    HeroDomain create(HeroDomain domain);

    HeroDomain update(Integer id, HeroDomain domain);

    void delete(Integer id);
}
