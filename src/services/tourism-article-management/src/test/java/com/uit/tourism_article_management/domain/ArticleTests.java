package com.uit.tourism_article_management.domain;

import com.uit.tourism_article_management.domain.exception.DomainException;
import com.uit.tourism_article_management.domain.model.article.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.type;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArticleTests {
    @Test
    public void Article_with_valid_information_is_created() {
        // Arrange
        final String title = "Create article";
        final String introduction = "Create article";

        // Act
        final Article article = Article.create(title, introduction);

        // Assert
        assertEquals(title, article.getTitle());
        assertEquals(introduction, article.getIntroduction());
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

        // Act
        final Executable act = () -> Article.create(title, introduction);

        // Assert
        DomainException error = assertThrows(DomainException.class, act);
        assertEquals(expectedMessage, error.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "'introduction is missing', ",
            "'introduction must not be blank', ''",
            "'introduction must not be blank', ' '",
            "'introduction must be between 1 and 350, actual 400', '320aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'"
    })
    public void Detects_an_invalid_article_introduction(final String expectedMessage, final String introduction) {
        // Arrange
        final String title = "Create article";

        // Act
        final Executable act = () -> Article.create(title, introduction);

        // Assert
        DomainException error = assertThrows(DomainException.class, act);
        assertEquals(expectedMessage, error.getMessage());
    }

    @Test
    public void Edit_an_article_with_valid_content(){
        // Arrange
        var currentToDelete1 = ArticleBlockBuilder.heading("1").buildFixture();
        var currentToUpdate = ArticleBlockBuilder.video("2").withMediaId("This will be deleted 1").buildFixture();
        var currentToDelete2 = ArticleBlockBuilder.video("3").withMediaId("This will be deleted 2").buildFixture();
        var incomingToUpdate = ArticleBlockBuilder.video("2")
                .withMediaId("This will be added")
                .withContent("edit content")
                .withOrder(1)
                .build();
        var incomingToAdd = ArticleBlockBuilder.paragraph("4")
                .withContent("edit content")
                .withOrder(2)
                .build();

        var incomingContent = List.of(incomingToUpdate, incomingToAdd);

        var sut = ArticleBuilder.builder()
                .withContentBlocks(currentToDelete1, currentToUpdate, currentToDelete2)
                .build();

        // Act
        sut.editContent(incomingContent);

        // Assert
        assertThat(sut.getContent()).containsExactlyInAnyOrderElementsOf(incomingContent);
    }

    @ParameterizedTest
    @CsvSource({
            "'ArticleContent is missing order 2', 1, 1", // Duplicate order
            "'ArticleContent is missing order 1', 2, 2",
            "'ArticleContent is missing order 1', 3, 3",
            "'ArticleContent is missing order 1', 2, 3", // Miss preceding order
            "'ArticleContent is missing order 2', 1, 3",
            "'ArticleContent is missing order 2', 4, 1",
            "'ArticleContent is missing order 2', 0, 1", // Doesn't allow not positive order
            "'ArticleContent is missing order 1', -4, 2"
    })
    public void Detects_an_invalid_sequence_of_article_content(
            final String expectedMessage,
            int firstOrder,
            int secondOrder
    ){
        // Arrange
        var incoming = List.of(
                ArticleBlockBuilder.paragraph("1").withContent("edit content").withOrder(firstOrder).build(),
                ArticleBlockBuilder.paragraph("2").withContent("edit content").withOrder(secondOrder).build()
        );
        var sut = ArticleBuilder.builder().build();

        // Act
        final Executable act = () -> sut.editContent(incoming);

        // Arrange
        DomainException error = assertThrows(DomainException.class, act);
        assertEquals(expectedMessage, error.getMessage());
    }
}
