package com.heroes.unitary.service;

import com.heroes.core.domain.HeroDomain;
import com.heroes.core.service.impl.HeroServiceImpl;
import com.heroes.infrastructure.entity.Hero;
import com.heroes.infrastructure.repository.HeroRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.heroes.factory.HeroFactory.domain;
import static com.heroes.factory.HeroFactory.entity;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
public class HeroServiceTest {

    @Mock
    private HeroRepository repository;

    @InjectMocks
    private HeroServiceImpl service;

    @Test
    @DisplayName("GIVEN an id WHEN trying to find a hero by id THEN return a hero")
    public void findById_Return_hero() {

        Hero mockedHero = entity();
        when(repository.findById(1)).thenReturn(Optional.of(mockedHero));

        HeroDomain domain = service.findById(1);

        assertEquals(mockedHero.getName(), domain.getName());
        assertEquals(mockedHero.getCharacter(), domain.getCharacter());
    }

    @Test
    @DisplayName("WHEN trying to find all heroes THEN return a list of heroes")
    public void findAll_Return_heroes() {

        List<Hero> mockedHeroes = List.of(entity());
        when(repository.findAll()).thenReturn(mockedHeroes);

        List<HeroDomain> domains = service.findAll(null, null);

        assertEquals(mockedHeroes.get(0).getName(), domains.get(0).getName());
        assertEquals(mockedHeroes.get(0).getCharacter(), domains.get(0).getCharacter());
    }

    @Test
    @DisplayName("WHEN trying to create a new hero THEN return a hero")
    public void create_Return_hero() {

        HeroDomain domain = domain();
        Hero entity = entity();
        when(repository.saveAndFlush(any(Hero.class))).thenReturn(entity);
        HeroDomain result = service.create(domain);

        assertNotNull(result);
        assertThat(result.getName(), equalTo(domain.getName()));
        assertThat(result.getCharacter(), equalTo(domain.getCharacter()));
    }

    @Test
    @DisplayName("WHEN trying to update a hero THEN return a hero")
    public void update_Return_hero() {

        HeroDomain domain = domain();
        Hero entity = entity();
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.saveAndFlush(any(Hero.class))).thenReturn(entity);
        HeroDomain result = service.update(1, domain);

        assertNotNull(result);
        assertThat(result.getName(), equalTo(domain.getName()));
        assertThat(result.getCharacter(), equalTo(domain.getCharacter()));
    }

    @Test
    @DisplayName("WHEN trying to delete a hero THEN void")
    public void delete_void() {

        Hero entity = entity();
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        service.delete(1);
        verify(repository, times(1)).deleteById(1);
    }
}
