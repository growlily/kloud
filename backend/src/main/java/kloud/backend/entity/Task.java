package kloud.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fromTime;   //开始时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;    //结束时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime delayTime;  //延期时间

    private String taskResource;    //课程资源路径

    private String taskResourceName = "无";

    @NotNull
    private Long courseId;  //所属课程id

}
