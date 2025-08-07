package org.wfq.wufangquan.es.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "index_w_project")
public class w_project implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String project_id;

    @Field(type = FieldType.Integer)
    private int status;

    @Field(type = FieldType.Date)
    private LocalDateTime create_time;

    @Field(type = FieldType.Text)
    private String project_name;

    @Field(type = FieldType.Date)
    private LocalDateTime deadline;

    @Field(type = FieldType.Text)
    private String description;

}
