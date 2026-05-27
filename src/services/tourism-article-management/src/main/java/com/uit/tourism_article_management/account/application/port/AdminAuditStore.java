package com.uit.tourism_article_management.account.application.port;

import com.uit.tourism_article_management.account.domain.AdminAction;

public interface AdminAuditStore {
    void save(AdminAction audit);
}
