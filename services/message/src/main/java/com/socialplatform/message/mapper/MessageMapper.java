package com.socialplatform.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.socialplatform.message.model.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}
