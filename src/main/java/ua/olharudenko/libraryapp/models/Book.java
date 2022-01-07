package ua.olharudenko.libraryapp.models;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ua.olharudenko.libraryapp.enums.Locale;

public class Book extends BaseEntity {

    private Long id;

    private String title;

    private LocalizedAuthor author;

    private String description;

    private Locale publish_locale;

    private Long publish_house_id;

    private int issueDate;

    private int items;

    public Book() {
    }

    public Book(String title, LocalizedAuthor author, String description, Locale publish_locale, Long publish_house_id, int issueDate, int items) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.publish_locale = publish_locale;
        this.publish_house_id = publish_house_id;
        this.issueDate = issueDate;
        this.items = items;
    }

    public Book(Long id, String title, LocalizedAuthor author, String description, Locale publish_locale, Long publish_house_id, int issueDate, int items) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.publish_locale = publish_locale;
        this.publish_house_id = publish_house_id;
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

    public Locale getPublish_locale() {
        return publish_locale;
    }

    public void setPublish_locale(Locale publish_locale) {
        this.publish_locale = publish_locale;
    }

    public Long getPublish_house_id() {
        return publish_house_id;
    }

    public void setPublish_house_id(Long publish_house_id) {
        this.publish_house_id = publish_house_id;
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
        return issueDate == book.issueDate && items == book.items && Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(description, book.description) && publish_locale == book.publish_locale && Objects.equals(publish_house_id, book.publish_house_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, description, publish_locale, publish_house_id, issueDate, items);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("author", author)
                .append("description", description)
                .append("publish_locale", publish_locale)
                .append("publish_house_id", publish_house_id)
                .append("issueDate", issueDate)
                .append("items", items)
                .toString();
    }
}
