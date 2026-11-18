package com.gsdc.campushub.dto;

import com.gsdc.campushub.model.Role;

import javax.validation.constraints.NotNull;

public class RoleUpdateRequest {
    @NotNull(message = "Role is required")
    private Role role;

    private Boolean active;

    public RoleUpdateRequest() {}

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
