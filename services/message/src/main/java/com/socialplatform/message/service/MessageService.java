package com.socialplatform.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.socialplatform.message.model.Message;
import java.util.List;

public interface MessageService extends IService<Message> {
    Message sendMessage(Long fromUserId, Long toUserId, String content);
    Message getMessage(Long messageId);
    void deleteMessage(Long messageId);
    void markAsRead(Long messageId);
    void markAsUnread(Long messageId);
    void recallMessage(Long messageId);
    void reportMessage(Long messageId, String reason);
    boolean isMessageRead(Long messageId);
    
    // 新增方法
    List<Message> getUserMessages(Long userId);
    List<Message> getUserUnreadMessages(Long userId);
    long getUserUnreadMessageCount(Long userId);
    void markMessagesAsRead(List<Long> messageIds);
}
