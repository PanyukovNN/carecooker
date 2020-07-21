package com.zylex.carecooker.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String subTitle;

    private String image;

    @ManyToMany
    private List<Section> sections;

    @OneToMany
    private List<Paragraph> paragraphs;

    public Article() {
    }

    public Article(String title,
                   String subTitle,
                   String image,
//                   List<ArticleSection> articleSections,
                   List<Paragraph> paragraphs) {
        this.title = title;
        this.subTitle = subTitle;
        this.image = image;
//        this.articleSections = articleSections;
        this.paragraphs = paragraphs;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", image='" + image + '\'' +
                ", sections=" + sections +
                ", paragraphs=" + paragraphs +
                '}';
    }
}
