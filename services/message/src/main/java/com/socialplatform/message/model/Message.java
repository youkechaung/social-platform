package com.socialplatform.message.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("messages")
public class Message {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private String content;
    
    private String mediaUrls; // JSON array of media URLs
    
    private Integer type; // 1: text, 2: image, 3: video
    
    private Integer likeCount;
    
    private Integer commentCount;
    
    private Integer shareCount;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    private Integer status; // 0: normal, 1: hidden
    
    @TableLogic
    private Integer deleted;
}
