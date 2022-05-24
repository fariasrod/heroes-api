package com.heroes.unitary.mappers;

import com.example.app.model.HeroResource;
import com.heroes.core.domain.HeroDomain;
import com.heroes.core.mappers.HeroMapper;
import com.heroes.infrastructure.entity.Hero;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.heroes.factory.HeroFactory.domain;
import static com.heroes.factory.HeroFactory.entity;
import static com.heroes.factory.HeroFactory.resource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class HeroMapperTest {

    @Test
    @DisplayName("Given an entity returns a domain")
    void toDomain_from_entity_ok() {
        Hero entity = entity();
        HeroDomain domain = HeroMapper.INSTANCE.toDomain(entity);
        compare(entity, domain);
    }

    @Test
    @DisplayName("Given a resource returns a domain")
    void toDomain_from_resource_ok() {
        HeroResource resource = resource();
        HeroDomain domain = HeroMapper.INSTANCE.toDomain(resource);
        compare(resource, domain);
    }

    @Test
    @DisplayName("Given a domain returns a resource")
    void toResource_from_domain_ok() {
        HeroDomain domain = domain();
        HeroResource resource = HeroMapper.INSTANCE.toResource(domain);
        compare(domain, resource);
    }

    @Test
    @DisplayName("Given a list of entities returns a list of domains")
    void toDomains_from_entities_ok() {
        List<Hero> entities = List.of(entity());
        List<HeroDomain> domains = HeroMapper.INSTANCE.toDomains(entities);
        compare_heroes_and_domains_list(entities, domains);
    }

    @Test
    @DisplayName("Given a list of domains returns a list of resources")
    void toResources_from_domains_ok() {
        List<HeroDomain> domains = List.of(domain());
        List<HeroResource> resources = HeroMapper.INSTANCE.toResources(domains);
        compare_domains_and_resources_list(domains, resources);
    }

    protected void compare_heroes_and_domains_list(List<Hero> entities, List<HeroDomain> domains) {
        assertNotNull(entities);
        assertNotNull(domains);
        for (int i = 0; i < entities.size(); i++)
            compare(entities.get(i), domains.get(i));
    }

    protected void compare_domains_and_resources_list(List<HeroDomain> domains, List<HeroResource> resources) {
        assertNotNull(domains);
        assertNotNull(resources);
        for (int i = 0; i < domains.size(); i++)
            compare(domains.get(i), resources.get(i));
    }

    protected void compare(Hero entity, HeroDomain domain) {
        assertNotNull(entity);
        assertNotNull(domain);
        assertEquals(entity.getName(), domain.getName());
        assertEquals(entity.getCharacter(), domain.getCharacter());
    }

    protected void compare(HeroDomain domain, HeroResource resource) {
        assertNotNull(domain);
        assertNotNull(resource);
        assertEquals(domain.getName(), resource.getName());
        assertEquals(domain.getCharacter(), resource.getCharacter());
    }

    protected void compare(HeroResource resource, HeroDomain domain) {
        assertNotNull(resource);
        assertNotNull(domain);
        assertEquals(resource.getName(), domain.getName());
        assertEquals(resource.getCharacter(), domain.getCharacter());
    }
}
