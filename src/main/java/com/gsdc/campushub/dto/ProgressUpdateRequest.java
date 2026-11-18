package com.gsdc.campushub.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProgressUpdateRequest {
    @NotNull(message = "Progress is required")
    @Min(value = 0, message = "Progress cannot be less than 0")
    @Max(value = 100, message = "Progress cannot exceed 100")
    private Integer progress;

    public ProgressUpdateRequest() {}

    public ProgressUpdateRequest(Integer progress) {
        this.progress = progress;
    }

    public Integer getProgress() { return progress; }
    public void setProgress(Integer progress) { this.progress = progress; }
}
