package ua.olharudenko.libraryapp.models;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Book extends BaseEntity {

    private Long id;

    private String title;

    private String author;

    private String issuingOrganization;

    private int issueDate;

    private boolean ordered;

    public Book() {
    }

    public Book(String title, String author, String issuingOrganization, int issueDate, boolean ordered) {
        this.title = title;
        this.author = author;
        this.issuingOrganization = issuingOrganization;
        this.issueDate = issueDate;
        this.ordered = ordered;
    }

    public Book(Long id, String title, String author, String issuingOrganization, int issueDate, boolean ordered) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.issuingOrganization = issuingOrganization;
        this.issueDate = issueDate;
        this.ordered = ordered;
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

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return issueDate == book.issueDate && ordered == book.ordered && Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(issuingOrganization, book.issuingOrganization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, issuingOrganization, issueDate, ordered);
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
                .append(" ordered", ordered)
                .append('}')
                .toString();
    }
}
