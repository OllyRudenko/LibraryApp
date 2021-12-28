package ua.olharudenko.libraryapp.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class PublishingHouse extends BaseEntity {

    private Long id;

    private String email;

    private String phone;

    private Long fileReferenceId;

    public PublishingHouse() {
    }

    public PublishingHouse(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }

    public PublishingHouse(Long id, String email, String phone) {
        this.id = id;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getFileReferenceId() {
        return fileReferenceId;
    }

    public void setFileReferenceId(Long fileReferenceId) {
        this.fileReferenceId = fileReferenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublishingHouse that = (PublishingHouse) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(fileReferenceId, that.fileReferenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, phone, fileReferenceId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("email", email)
                .append("phone", phone)
                .append("fileReferenceId", fileReferenceId)
                .toString();
    }
}
