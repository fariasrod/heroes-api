package com.heroes.controller;

import com.example.app.api.HeroesApi;
import com.example.app.model.HeroResource;
import com.heroes.controller.helper.BaseController;
import com.heroes.core.mappers.HeroMapper;
import com.heroes.core.service.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class HeroController extends BaseController implements HeroesApi {

    private final HeroService service;

    @Autowired
    public HeroController(HeroService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<HeroResource> getHero(Integer id) {
        return Optional
                .ofNullable(service.findById(id))
                .map(m -> ResponseEntity.ok().body(HeroMapper.INSTANCE.toResource(m)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<HeroResource>> getHeroes(String name, String character) {
        return Optional
                .ofNullable(service.findAll(name, character))
                .map(m -> ResponseEntity.ok().body(HeroMapper.INSTANCE.toResources(m)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<HeroResource> postHero(HeroResource body) {
        return Optional
                .ofNullable(service.create(HeroMapper.INSTANCE.toDomain(body)))
                .map(m -> ResponseEntity.created(URI.create(String.format("/heroes/%s", m.getId()))).body(HeroMapper.INSTANCE.toResource(m)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Override
    public ResponseEntity<HeroResource> patchHero(Integer id, HeroResource body) {
        return Optional
                .ofNullable(service.update(id, HeroMapper.INSTANCE.toDomain(body)))
                .map(m -> ResponseEntity.ok().body(HeroMapper.INSTANCE.toResource(m)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteHero(Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
