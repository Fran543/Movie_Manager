    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.model;

import java.util.Optional;

/**
 *
 * @author fran
 */
public class User implements Comparable<User>{
    
    
    private int id;
    private String username;
    private String password;
    private Role role;
    private boolean active;

    public User() {
    }

    public User(int id, String username, String password, Role role, boolean active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = active;
    }

    public User(String username, String password, Role role, boolean active) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return username + " - " + role.name + " - " + (active? "Active" : "Not active");
    }

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.getUsername());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Integer.parseInt(username);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    public enum Role {
        ADMIN ("Administrator"),
        USER ("Local user");
        
        private final String name;       

        private Role(String name) {
            this.name = name;
        }

        public static Optional<Role> from(String name) {
            for (Role value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
        

        public String getName() {
            return name;
        }
        
    }
    
    
}

