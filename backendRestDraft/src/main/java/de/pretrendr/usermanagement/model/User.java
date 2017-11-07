package de.pretrendr.usermanagement.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Tristan Schneider
 */
@Table(name = "User")
@Entity
@Data
@NoArgsConstructor
@ToString(exclude = "password, roles")
@EqualsAndHashCode(exclude = "password, roles")
public class User implements Serializable, UserDetails {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param username
	 * @param password
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param address
	 * @param phone
	 */
	public User(String username, String password, String firstname, String lastname, String email, String address,
			String phone) {
		this.id = UUID.randomUUID();
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.address = address;
		this.phone = phone;
	}

	@Id
	private UUID id;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	private String address;
	private String phone;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole().toUpperCase()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}