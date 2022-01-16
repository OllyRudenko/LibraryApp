package ua.olharudenko.libraryapp.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ua.olharudenko.libraryapp.enums.Locale;

import java.util.Objects;

public class LocalizedAuthor extends BaseEntity {

    private Long authorId;

    private String fullName;

    private Locale local;

    private String biografy; //todo biography

    private FileReference photo;

    public LocalizedAuthor() {
    }

    public LocalizedAuthor(String fullName, Locale local, String biografy, FileReference photo) {
        this.fullName = fullName;
        this.local = local;
        this.biografy = biografy;
        this.photo = photo;
    }

    public LocalizedAuthor(Long authorId, String fullName, Locale local, String biografy, FileReference photo) {
        this.authorId = authorId;
        this.fullName = fullName;
        this.local = local;
        this.biografy = biografy;
        this.photo = photo;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Locale getLocal() {
        return local;
    }

    public void setLocal(Locale local) {
        this.local = local;
    }

    public String getBiografy() {
        return biografy;
    }

    public void setBiografy(String biografy) {
        this.biografy = biografy;
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
        LocalizedAuthor that = (LocalizedAuthor) o;
        return Objects.equals(authorId, that.authorId) && Objects.equals(fullName, that.fullName) && local == that.local && Objects.equals(biografy, that.biografy) && Objects.equals(photo, that.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, fullName, local, biografy, photo);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("author_id", authorId)
                .append("fullName", fullName)
                .append("local", local)
                .append("biografy", biografy)
                .toString();
    }
}
