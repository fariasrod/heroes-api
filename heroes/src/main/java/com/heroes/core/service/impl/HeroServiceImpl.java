package com.heroes.core.service.impl;

import com.heroes.core.domain.HeroDomain;
import com.heroes.core.mappers.HeroMapper;
import com.heroes.core.service.HeroService;
import com.heroes.infrastructure.entity.Hero;
import com.heroes.infrastructure.repository.HeroRepository;
import com.heroes.infrastructure.repository.dsl.HeroFilterDsl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class HeroServiceImpl implements HeroService {

    private final HeroRepository repository;

    @Autowired
    public HeroServiceImpl(HeroRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "hero-cache", key = "'heroCache'+#id")
    @Override
    public HeroDomain findById(Integer id) {
        log.info(String.format("Trying to find a hero by id { %s }", id));
        return repository.findById(id).map(HeroMapper.INSTANCE::toDomain).orElse(null);
    }

    @Cacheable("heroes-cache")
    @Override
    public List<HeroDomain> findAll(String name, String character) {
        log.info("Trying to find all heroes");

        if (name != null || character != null) {
            return HeroMapper.INSTANCE.toDomains((List<Hero>) repository.findAll(HeroFilterDsl.filter(name, character)));
        } else {
            return HeroMapper.INSTANCE.toDomains(repository.findAll());
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "hero-cache", allEntries = true),
            @CacheEvict(value = "heroes-cache", allEntries = true)})
    @Override
    public HeroDomain create(HeroDomain domain) {
        log.info(String.format("Trying to create a new hero using %s ", domain.toString()));

        Hero hero = new Hero();
        HeroMapper.INSTANCE.merge(domain, hero);
        return HeroMapper.INSTANCE.toDomain(repository.saveAndFlush(hero));
    }

    @Caching(evict = {
            @CacheEvict(value = "hero-cache", allEntries = true),
            @CacheEvict(value = "heroes-cache", allEntries = true)})
    @Override
    public HeroDomain update(Integer id, HeroDomain domain) {
        log.info(String.format("Trying to update a hero by id { %s } using %s ", id, domain.toString()));

        Optional<Hero> hero = repository.findById(id);
        if (hero.isPresent()) {
            HeroMapper.INSTANCE.merge(domain, hero.get());
            return HeroMapper.INSTANCE.toDomain(repository.saveAndFlush(hero.get()));
        } else {
            return null;
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "hero-cache", allEntries = true),
            @CacheEvict(value = "heroes-cache", allEntries = true)})
    @Override
    public void delete(Integer id) {
        log.info(String.format("Trying to delete a hero by id { %s }", id));

        repository.findById(id).ifPresent(value -> repository.deleteById(value.getId()));
    }
}
