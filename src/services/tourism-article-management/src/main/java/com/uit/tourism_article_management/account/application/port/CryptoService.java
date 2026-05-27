package com.uit.tourism_article_management.account.application.port;

public interface CryptoService {
    String hash(String password);
    boolean compare(String password);
}
