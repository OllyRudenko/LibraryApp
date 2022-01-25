package ua.olharudenko.libraryapp.models;

import java.util.Comparator;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ua.olharudenko.libraryapp.enums.Locale;

import javax.validation.constraints.*;

public class Book extends BaseEntity implements Comparable {

    private Long id;

    @NotBlank
    @Size(min = 1, max = 200)
    private String title;

    @NotNull
    private LocalizedAuthor author;

    @NotBlank
    @Size(min = 1, max = 2000)
    private String description;

    @NotNull
    private Locale publishLocale;

    @NotNull
    private Long publishHouseId;

    @NotNull
    private int issueDate;

    @NotNull
    private int items;

    public Book() {
    }

    public Book(String title, LocalizedAuthor author, String description, Locale publishLocale, Long publishHouseId, int issueDate, int items) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.publishLocale = publishLocale;
        this.publishHouseId = publishHouseId;
        this.issueDate = issueDate;
        this.items = items;
    }

    public Book(Long id, String title, LocalizedAuthor author, String description, Locale publishLocale, Long publishHouseId, int issueDate, int items) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.publishLocale = publishLocale;
        this.publishHouseId = publishHouseId;
        this.issueDate = issueDate;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalizedAuthor getLocalizedAuthor() {
        return author;
    }

    public void setLocalizedAuthor(LocalizedAuthor author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Locale getPublishLocale() {
        return publishLocale;
    }

    public void setPublishLocale(Locale publishLocale) {
        this.publishLocale = publishLocale;
    }

    public Long getPublishHouseId() {
        return publishHouseId;
    }

    public void setPublishHouseId(Long publishHouseId) {
        this.publishHouseId = publishHouseId;
    }

    public int getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(int issueDate) {
        this.issueDate = issueDate;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return issueDate == book.issueDate && items == book.items && Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(description, book.description) && publishLocale == book.publishLocale && Objects.equals(publishHouseId, book.publishHouseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, description, publishLocale, publishHouseId, issueDate, items);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("author", author)
                .append("description", description)
                .append("publish_locale", publishLocale)
                .append("publish_house_id", publishHouseId)
                .append("issueDate", issueDate)
                .append("items", items)
                .toString();
    }

    @Override
    public int compareTo(Object o) {
        return this.getTitle().compareTo(((Book)o).getTitle());
    }
}
