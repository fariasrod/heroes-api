package com.heroes.integrated;

import com.heroes.core.domain.HeroDomain;
import com.heroes.infrastructure.entity.Hero;
import com.heroes.infrastructure.repository.HeroRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.heroes.factory.HeroFactory.domain;
import static com.heroes.factory.HeroFactory.entity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // lifecycle will be per class and not per method
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HeroTest {

    public static final String URL = "/heroes";

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private HeroRepository repository;

    Hero entity;
    HeroDomain domain;

    @BeforeAll
    public void init() {
        entity = entity();
        domain = domain();
    }

    @Test
    @DisplayName("WHEN trying to create a new hero THEN return a hero | integrated test")
    public void create_Return_hero() {
        HttpEntity<HeroDomain> httpEntity = new HttpEntity<>(domain);

        ResponseEntity<HeroDomain> response = this.template
                .exchange(URL, HttpMethod.POST, httpEntity, HeroDomain.class);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getName(), entity.getName());
        assertEquals(response.getBody().getCharacter(), entity.getCharacter());
    }

    @Test
    @DisplayName("WHEN trying to find all heroes THEN return a list of heroes | integrated test")
    public void findAll_Return_heroes() {

        ResponseEntity<HeroDomain[]> response = this.template
                .exchange(URL, HttpMethod.GET, null, HeroDomain[].class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("GIVEN an id WHEN trying to find a hero by id THEN return a hero | integrated test")
    public void findById_Return_hero() {
        Hero hero = this.repository.saveAndFlush(this.entity);

        ResponseEntity<HeroDomain> response = this.template
                .exchange(URL + "/" + hero.getId(), HttpMethod.GET, null, HeroDomain.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getName(), hero.getName());
        assertEquals(response.getBody().getCharacter(), hero.getCharacter());
    }

    @Test
    @DisplayName("WHEN trying to delete a hero THEN void | integrated test")
    public void delete_void() {

        Hero hero = this.repository.saveAndFlush(this.entity);

        ResponseEntity<Void> response = this.template
                .exchange(URL + "/" + hero.getId(), HttpMethod.DELETE, null, Void.class);

        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }
}
