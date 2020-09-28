package kloud.backend.service.dto;

import lombok.Data;
import lombok.ToString;
import lombok.Value;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;

/**
 * 小白表示很疑惑，JackSon究竟如何序列化对象的
 */


//@Value
public interface UserCourseDTO {

//    Long id;
//
//    String login;
//
//    String RealName;
//
//    String email;

    Long getId();

    String getLogin();

    String getRealName();

    String getEmail();

}
