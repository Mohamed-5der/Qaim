package com.qaim.qaim.Models.RealstateShowUserResponse;

import com.google.gson.annotations.SerializedName;

public class NoteItem {

    @SerializedName("id")
    private Integer id;

    @SerializedName("notes")
    private String notes;

    @SerializedName("by")
    private String by;

    @SerializedName("created_at")
    private String createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}