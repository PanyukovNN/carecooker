package com.zylex.carecooker.repository;

import com.zylex.carecooker.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
