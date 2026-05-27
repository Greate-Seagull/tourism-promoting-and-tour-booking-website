package com.uit.tourism_article_management.utils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.uit.tourism_article_management.account.presentation.view.RoleRequestQuery;
import com.uit.tourism_article_management.article.presentation.view.AdminArticleQuery;
import com.uit.tourism_article_management.article.presentation.view.ArticleQuery;
import com.uit.tourism_article_management.order.presentation.view.OrderQuery;
import com.uit.tourism_article_management.tour.presentation.view.OperatorTourQuery;
import com.uit.tourism_article_management.tour.presentation.view.ProductQuery;

public class QueryDslPredicateBuilder {
    private QueryDslPredicateBuilder() {
    }

    private static Predicate like(StringPath queriedField, String queriedValue) {
        if (queriedValue == null || queriedValue.isBlank())
            return null;
        return queriedField.likeIgnoreCase("%" + queriedValue + "%");
    }

    private static <T extends Number & Comparable<?>> Predicate min(NumberExpression<T> queriedField, T queriedValue) {
        if (queriedValue == null)
            return null;
        return queriedField.goe(queriedValue);
    }

    private static <T extends Number & Comparable<?>> Predicate max(NumberExpression<T> queriedField, T queriedValue) {
        if (queriedValue == null)
            return null;
        return queriedField.loe(queriedValue);
    }

    private static <T extends Comparable<?>> Predicate min(ComparableExpression<T> queriedField, T queriedValue) {
        if (queriedValue == null)
            return null;
        return queriedField.goe(queriedValue);
    }

    private static <T extends Comparable<?>> Predicate max(ComparableExpression<T> queriedField, T queriedValue) {
        if (queriedValue == null)
            return null;
        return queriedField.loe(queriedValue);
    }

    private static <T> Predicate exact(SimpleExpression<T> queriedField, T queriedValue) {
        if (queriedValue == null)
            return null;
        return queriedField.eq(queriedValue);
    }

    public static Predicate from(ProductQuery query) {
        return new BooleanBuilder();
    }

    public static Predicate from(ArticleQuery query) {
        return new BooleanBuilder();
    }

    public static Predicate from(AdminArticleQuery query) {
        return new BooleanBuilder();
    }

    public static Predicate from(OperatorTourQuery query) {
        return new BooleanBuilder();
    }

    public static Predicate from(RoleRequestQuery query) {
        return new BooleanBuilder();
    }

    public static Predicate from(OrderQuery query, String accountId) {
        return new BooleanBuilder();
    }
}
