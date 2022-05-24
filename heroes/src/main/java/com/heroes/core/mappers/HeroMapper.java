package com.heroes.core.mappers;

import com.example.app.model.HeroResource;
import com.heroes.core.domain.HeroDomain;
import com.heroes.infrastructure.entity.Hero;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HeroMapper {

    HeroMapper INSTANCE = Mappers.getMapper(HeroMapper.class);

    HeroDomain toDomain(HeroResource resource);

    HeroResource toResource(HeroDomain domain);

    List<HeroResource> toResources(List<HeroDomain> domains);

    HeroDomain toDomain(Hero entity);

    List<HeroDomain> toDomains(List<Hero> entities);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(HeroDomain domain, @MappingTarget Hero entity);
}
