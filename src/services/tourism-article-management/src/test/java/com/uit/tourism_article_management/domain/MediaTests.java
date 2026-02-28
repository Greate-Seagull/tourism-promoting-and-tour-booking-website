package com.uit.tourism_article_management.domain;

import com.uit.tourism_article_management.domain.model.article.ArticleContentEdited;
import com.uit.tourism_article_management.domain.model.article.ArticleId;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import com.uit.tourism_article_management.domain.model.media.MediaService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MediaTests {
    @Test
    public void Media_service_detects_correct_changes(){
        // Arrange
        var oldContent = List.of(
                ArticleBlockBuilder.image("1").withMediaId("This will be replaced with existing").build(),
                ArticleBlockBuilder.image("2").withMediaId("This will be replaced with new").build(),
                ArticleBlockBuilder.image("3").withMediaId("This will be deleted").build(),
                ArticleBlockBuilder.image("4").withMediaId("This will be existing one").build()
        );
        var newContent = List.of(
                ArticleBlockBuilder.image("1").withMediaId("This will be existing one").build(),
                ArticleBlockBuilder.image("2").withMediaId("This will be added for replacing").build(),
                ArticleBlockBuilder.image("3").withMediaId("This will be added").build(),
                ArticleBlockBuilder.image("4").withMediaId("This will be existing one").build()
        );
        var event = new ArticleContentEdited(
                ArticleId.nextIdentity(),
                oldContent,
                newContent
        );
        var sut = new MediaService();

        // Act
        var result = sut.analyzeChanges(event);

        // Assert
        assertThat(result)
                .containsEntry(MediaId.existing("This will be replaced with existing"), -1)
                .containsEntry(MediaId.existing("This will be replaced with new"), -1)
                .containsEntry(MediaId.existing("This will be deleted"), -1)
                .containsEntry(MediaId.existing("This will be existing one"), 1)
                .containsEntry(MediaId.existing("This will be added for replacing"), 1)
                .containsEntry(MediaId.existing("This will be added"), 1);
    }
}
