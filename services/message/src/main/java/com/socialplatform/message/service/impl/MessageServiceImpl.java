package com.socialplatform.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.socialplatform.message.mapper.MessageMapper;
import com.socialplatform.message.model.Message;
import com.socialplatform.message.service.MessageService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Override
    public Message sendMessage(Long fromUserId, Long toUserId, String content) {
        Message message = new Message();
        message.setFromUserId(fromUserId);
        message.setToUserId(toUserId);
        message.setContent(content);
        message.setIsRead(false);
        save(message);
        return message;
    }

    @Override
    public Message getMessage(Long messageId) {
        return getById(messageId);
    }

    @Override
    public void deleteMessage(Long messageId) {
        removeById(messageId);
    }

    @Override
    public void markAsRead(Long messageId) {
        Message message = getById(messageId);
        if (message != null) {
            message.setIsRead(true);
            updateById(message);
        }
    }

    @Override
    public void markAsUnread(Long messageId) {
        Message message = getById(messageId);
        if (message != null) {
            message.setIsRead(false);
            updateById(message);
        }
    }

    @Override
    public void recallMessage(Long messageId) {
        Message message = getById(messageId);
        if (message != null) {
            message.setIsRecalled(true);
            updateById(message);
        }
    }

    @Override
    public void reportMessage(Long messageId, String reason) {
        Message message = getById(messageId);
        if (message != null) {
            message.setIsReported(true);
            message.setReportReason(reason);
            updateById(message);
        }
    }

    @Override
    public boolean isMessageRead(Long messageId) {
        Message message = getById(messageId);
        return message != null && message.getIsRead();
    }

    @Override
    public List<Message> getUserMessages(Long userId) {
        return list(new LambdaQueryWrapper<Message>()
            .eq(Message::getToUserId, userId)
            .orderByDesc(Message::getCreateTime));
    }

    @Override
    public List<Message> getUserUnreadMessages(Long userId) {
        return list(new LambdaQueryWrapper<Message>()
            .eq(Message::getToUserId, userId)
            .eq(Message::getIsRead, false)
            .orderByDesc(Message::getCreateTime));
    }

    @Override
    public long getUserUnreadMessageCount(Long userId) {
        return count(new LambdaQueryWrapper<Message>()
            .eq(Message::getToUserId, userId)
            .eq(Message::getIsRead, false));
    }

    @Override
    public void markMessagesAsRead(List<Long> messageIds) {
        if (messageIds != null && !messageIds.isEmpty()) {
            List<Message> messages = listByIds(messageIds);
            messages.forEach(message -> message.setIsRead(true));
            updateBatchById(messages);
        }
    }
}
