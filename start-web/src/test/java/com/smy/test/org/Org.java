package com.smy.test.org;

import com.smy.util.ChangeIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by smy on 2018/7/23.
 */
@Getter
@Setter
@ToString
@Entity
@Table
@ApiModel("用户")
public class Org {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(columnDefinition = "bigint COMMENT 'ID'")
    @ChangeIgnore
    private Long id;
    @ApiModelProperty(dataType = "string")
    @Column(columnDefinition = "timestamp COMMENT '创建时间'")
    @ChangeIgnore
    private Timestamp createTime;
    @ApiModelProperty(dataType = "string")
    @Column(columnDefinition = "timestamp COMMENT '更新时间'")
    @ChangeIgnore
    private Timestamp updateTime;
    @Version
    @Column(columnDefinition = "bigint COMMENT '版本号'")
    @ChangeIgnore
    private Long version;
    @Column(columnDefinition = "varchar(30) COMMENT '名称'")
    private String name;

    @PrePersist
    public void prePersist() {
        createTime = new Timestamp(System.currentTimeMillis());
        updateTime = createTime;
    }

    @PreUpdate
    public void preUpdate() {
        updateTime = new Timestamp(System.currentTimeMillis());
    }
}
