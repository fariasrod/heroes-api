package com.heroes.unitary.controller;

import com.example.app.model.HeroResource;
import com.heroes.core.domain.HeroDomain;
import com.heroes.core.service.HeroService;
import com.heroes.infrastructure.repository.HeroRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.heroes.factory.HeroFactory.domain;
import static com.heroes.factory.HeroFactory.resource;
import static com.heroes.factory.HeroFactory.toJson;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HeroControllerTest {

    public static final String URL = "/heroes";
    public static final String URL_ID = "/heroes/{id}";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private HeroService service;

    @MockBean
    private HeroRepository repository;

    @Test
    @DisplayName("GIVEN an id WHEN trying to find a hero by id THEN return 200")
    public void findById_Return_200() throws Exception {

        HeroResource resource = resource();
        HeroDomain domain = domain();

        given(service.findById(anyInt())).willReturn(domain);

        mvc.perform(get(URL_ID, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(resource.getName())))
                .andExpect(jsonPath("$.character", is(resource.getCharacter())))
                .andExpect(status().isOk());
        verify(service, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("GIVEN a list of heroes WHEN trying to find all heroes THEN return 200")
    public void findAll_Return_200() throws Exception {

        List<HeroResource> resources = List.of(resource());
        List<HeroDomain> domains = List.of(domain());

        given(service.findAll(null, null)).willReturn(domains);

        mvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(resources.get(0).getName())))
                .andExpect(jsonPath("$[0].character", is(resources.get(0).getCharacter())))
                .andExpect(status().isOk());
        verify(service, times(1)).findAll(null, null);
    }

    @Test
    @DisplayName("GIVEN a list of heroes WHEN trying to find all heroes THEN return 200")
    public void findAllWithParameters_Return_200() throws Exception {

        List<HeroResource> resources = List.of(resource());
        List<HeroDomain> domains = List.of(domain());

        given(service.findAll(anyString(), anyString())).willReturn(domains);

        mvc.perform(get(URL)
                        .param("name", anyString())
                        .param("character", anyString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(resources.get(0).getName())))
                .andExpect(jsonPath("$[0].character", is(resources.get(0).getCharacter())))
                .andExpect(status().isOk());
        verify(service, times(1)).findAll(anyString(), anyString());
    }

    @Test
    @DisplayName("GIVEN a json hero WHEN trying to create a hero THEN return 201")
    public void create_Return_201() throws Exception {

        HeroResource resource = resource();
        HeroDomain domain = domain();

        given(service.create(any(HeroDomain.class))).willReturn(domain);

        mvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(resource)))
                .andExpect(jsonPath("$.name", is(resource.getName())))
                .andExpect(jsonPath("$.character", is(resource.getCharacter())))
                .andExpect(status().isCreated());
        verify(service, times(1)).create(any(HeroDomain.class));
    }

    @Test
    @DisplayName("GIVEN a json hero WHEN trying to update a hero THEN return 200")
    public void update_Return_200() throws Exception {

        HeroResource resource = resource();
        HeroDomain domain = domain();

        given(service.update(anyInt(), any(HeroDomain.class))).willReturn(domain);

        mvc.perform(patch(URL_ID, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(resource)))
                .andExpect(jsonPath("$.name", is(resource.getName())))
                .andExpect(jsonPath("$.character", is(resource.getCharacter())))
                .andExpect(status().isOk());
        verify(service, times(1)).update(anyInt(), any(HeroDomain.class));
    }

    @Test
    @DisplayName("GIVEN a hero id WHEN trying to delete a hero THEN return 204")
    public void delete_Return_204() throws Exception {

        mvc.perform(delete(URL_ID, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(service, times(1)).delete(anyInt());
    }
}
