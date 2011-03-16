package models;

import java.util.*;
import javax.persistence.*;

import models.deadbolt.Role;
import models.deadbolt.RoleHolder;
import play.db.jpa.*;
import play.data.validation.*;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Usuario extends Model implements RoleHolder {

    @Required
    public String login;

    @Required
    //@Password
    public String password;

    @Required
    @ManyToOne
    public ApplicationRole rol;

    public static Usuario connect(String login, String password) {
        return find("byLoginAndPassword", login, password).first();
    }

    @Override
    public String toString() {
        return "Usuario(" + login + ")";
    }

    public List<? extends Role> getRoles() {
        return Arrays.asList(rol);
    }
}
