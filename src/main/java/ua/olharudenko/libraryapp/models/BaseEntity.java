package ua.olharudenko.libraryapp.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BaseEntity implements Serializable {

    protected LocalDateTime createdDate;

    protected LocalDateTime updatedDate;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
