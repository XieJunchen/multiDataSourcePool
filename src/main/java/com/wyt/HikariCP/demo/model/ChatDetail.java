package com.wyt.HikariCP.demo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("user")
public class ChatDetail implements Serializable {
    private Long id;

    private String name;

    private Integer age;

    private String email;
}