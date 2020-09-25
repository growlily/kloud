package kloud.backend.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "saas_tasks")
@Data
public class Task extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String taskName;

    private String taskInfo;    //任务说明

    private Instant fromTime;   //开始时间

    private Instant endTime;    //结束时间

    private Instant delayTime;  //延期时间

    private Long courseId;  //所属课程id

    private String taskResource;    //课程资源路径

}
