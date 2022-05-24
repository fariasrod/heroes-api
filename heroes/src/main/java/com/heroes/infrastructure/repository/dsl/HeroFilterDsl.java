package com.heroes.infrastructure.repository.dsl;

import com.heroes.infrastructure.entity.QHero;
import com.querydsl.core.types.dsl.BooleanExpression;

public class HeroFilterDsl {

    private static final QHero HERO = QHero.hero;

    public static BooleanExpression filter(String name, String character) {
        BooleanExpression expression = null;
        expression = QueryDslBuilder.addAndContains(expression, HERO.name, name);
        expression = QueryDslBuilder.addAndContains(expression, HERO.character, character);
        return expression;
    }
}
