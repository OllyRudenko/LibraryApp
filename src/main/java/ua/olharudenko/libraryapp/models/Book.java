package ua.olharudenko.libraryapp.models;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Book extends BaseEntity {

    private Long id;

    private String title;

    private String author;

    private String issuingOrganization;

    private int issueDate;

    private int items;

    public Book() {
    }

    public Book(String title, String author, String issuingOrganization, int issueDate, int items) {
        this.title = title;
        this.author = author;
        this.issuingOrganization = issuingOrganization;
        this.issueDate = issueDate;
        this.items = items;
    }

    public Book(Long id, String title, String author, String issuingOrganization, int issueDate, int items) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.issuingOrganization = issuingOrganization;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIssuingOrganization() {
        return issuingOrganization;
    }

    public void setIssuingOrganization(String issuingOrganization) {
        this.issuingOrganization = issuingOrganization;
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
        return issueDate == book.issueDate && items == book.items && Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(issuingOrganization, book.issuingOrganization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, issuingOrganization, issueDate, items);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("Book{")
                .append(" createdDate", createdDate)
                .append(" updatedDate", updatedDate)
                .append(" id", id)
                .append(" title", title)
                .append(" author", author)
                .append(" issuingOrganization", issuingOrganization)
                .append(" issueDate", issueDate)
                .append(" ordered", items)
                .append('}')
                .toString();
    }
}
