package com.heroes.infrastructure.repository.dsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;

public class QueryDslBuilder {

    public static BooleanExpression addAndContains(final BooleanExpression expression, final StringPath path,
                                                   final String stringValue) {
        BooleanExpression stringContains = null;
        if (stringValue != null) {
            stringContains = path.contains(stringValue);
        }

        return addAndCriteria(expression, stringContains);
    }

    public static BooleanExpression addAndCriteria(final BooleanExpression expression, final BooleanExpression added) {
        BooleanExpression expressionAux = expression;
        if (added != null) {
            if (expressionAux == null) {
                expressionAux = added;
            } else {
                expressionAux = expressionAux.and(added);
            }
        }
        return expressionAux;
    }
}
