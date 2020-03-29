package com.example.restfulwebservices.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;


public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet(
//            ApplicationUserPermission.USER_READ,
            ApplicationUserPermission.TASK_READ
    )),

    ADMIN(Sets.newHashSet(
            ApplicationUserPermission.USER_READ,
            ApplicationUserPermission.USER_WRITE,
            ApplicationUserPermission.TASK_READ,
            ApplicationUserPermission.TASK_WRITE
    ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> perms = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        perms.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return perms;
    }
}
