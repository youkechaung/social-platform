package com.socialplatform.message.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class MessageRequest {
    @NotBlank(message = "消息内容不能为空")
    @Size(max = 1000, message = "消息内容不能超过1000字")
    private String content;
    
    private List<String> mediaUrls;
    
    private Integer type = 1; // 默认为文本类型
}
