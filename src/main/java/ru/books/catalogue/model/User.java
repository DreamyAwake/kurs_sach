package ru.books.catalogue.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.FetchType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@ToString
public class User implements UserDetails {

    @Id
    @SequenceGenerator(sequenceName = "user_id_seq", name = "user_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;


    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private boolean enabled;

    @JsonIgnore
    private boolean expired;

    @JsonIgnore
    private boolean locked;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UserRole> userRoles;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream().map(UserRole::getUserAuthority).collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @JsonIgnore
    @Override
    public  boolean isAccountNonLocked() {
        return !expired;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return !expired;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

