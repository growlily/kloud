package kloud.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import kloud.backend.config.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  A user.
 */

@Entity
@Table(name = "saas_user")
@Getter
@Setter
@ToString
public class User extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

//    @JsonIgnore
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "real_Name", length = 50)
    private String realName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    private Integer userType = 0;   //1为教师，2为管理员

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj  instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) obj).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


}
