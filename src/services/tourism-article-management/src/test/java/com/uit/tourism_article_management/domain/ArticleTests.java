package com.uit.tourism_article_management.domain;

import com.uit.tourism_article_management.domain.exception.DomainException;
import com.uit.tourism_article_management.domain.model.article.Article;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArticleTests {
    @Test
    public void Article_with_valid_information_is_created() {
        // Arrange
        final String title = "Create article";
        final String introduction = "Create article";
        final String coverImageId = "Create article";

        // Act
        final Article article = Article.create(title, introduction, coverImageId);

        // Assert
        assertEquals(title, article.getTitle());
        assertEquals(introduction, article.getIntroduction());
        assertEquals(coverImageId, article.getCoverImageId());
    }

    @ParameterizedTest
    @CsvSource({
            "'title is missing', ",
            "'title must not be blank', ''",
            "'title must not be blank', ' '",
            "'title must be between 1 and 60, actual 64', '64aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'"
    })
    public void Detects_an_invalid_article_title(final String expectedMessage, final String title) {
        // Arrange
        final String introduction = "Create article";
        final String coverImageId = "Create article";

        // Act
        final Executable act = () -> Article.create(title, introduction, coverImageId);

        // Assert
        DomainException error = assertThrows(DomainException.class, act);
        assertEquals(expectedMessage, error.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "'introduction is missing', ",
            "'introduction must not be blank', ''",
            "'introduction must not be blank', ' '",
            "'introduction must be between 1 and 300, actual 320', '320aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'"
    })
    public void Detects_an_invalid_article_introduction(final String expectedMessage, final String introduction) {
        // Arrange
        final String title = "Create article";
        final String coverImageId = "Create article";

        // Act
        final Executable act = () -> Article.create(title, introduction, coverImageId);

        // Assert
        DomainException error = assertThrows(DomainException.class, act);
        assertEquals(expectedMessage, error.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "'coverImageId is missing', ",
            "'coverImageId must not be blank', ''",
            "'coverImageId must not be blank', ' '"
    })
    public void Detects_an_invalid_article_cover_image_id(final String expectedMessage, final String coverImageId) {
        // Arrange
        final String title = "Create article";
        final String introduction = "Create article";

        // Act
        final Executable act = () -> Article.create(title, introduction, coverImageId);

        // Assert
        final DomainException error = assertThrows(DomainException.class, act);
        assertEquals(expectedMessage, error.getMessage());
    }
}
