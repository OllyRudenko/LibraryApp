package ua.olharudenko.libraryapp.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class Author extends BaseEntity {

    private Long id;

    private String fullName;

    private FileReference photo;

    public Author() {
    }

    public Author(String fullName, FileReference photo) {
        this.fullName = fullName;
        this.photo = photo;
    }

    public Author(Long id, String fullName, FileReference photo) {
        this.id = id;
        this.fullName = fullName;
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public FileReference getPhoto() {
        return photo;
    }

    public void setPhoto(FileReference photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) && Objects.equals(fullName, author.fullName) && Objects.equals(photo, author.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, photo);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("fullName", fullName)
                .toString();
    }
}
