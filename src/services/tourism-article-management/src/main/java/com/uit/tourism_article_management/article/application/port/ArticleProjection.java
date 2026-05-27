package com.uit.tourism_article_management.article.application.port;

import com.uit.tourism_article_management.article.presentation.view.CompleteArticle;
import com.uit.tourism_article_management.article.presentation.view.CompleteReport;
import com.uit.tourism_article_management.article.presentation.view.SummarizedArticle;

import java.util.List;

public interface ArticleProjection {
    CompleteArticle findCompleteById(String articleId);

    List<CompleteReport> findReportsOfArticle(String articleId);

    List<SummarizedArticle> findSummariesOfEditor(String accountId);
}
