package com.heroes.infrastructure.repository;

import com.heroes.infrastructure.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Integer>, QuerydslPredicateExecutor<Hero> {

}