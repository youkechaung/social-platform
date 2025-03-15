package com.socialplatform.message.controller;

import com.socialplatform.message.dto.MessageRequest;
import com.socialplatform.message.model.Message;
import com.socialplatform.message.service.MessageService;
import com.socialplatform.common.api.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send")
    public Result<?> sendMessage(@Valid @RequestBody MessageRequest request) {
        Message message = messageService.sendMessage(request.getFromUserId(), request.getToUserId(), request.getContent());
        return Result.success(message);
    }

    @GetMapping("/{messageId}")
    public Result<?> getMessage(@PathVariable Long messageId) {
        Message message = messageService.getMessage(messageId);
        return Result.success(message);
    }

    @DeleteMapping("/{messageId}")
    public Result<?> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return Result.success();
    }

    @PutMapping("/{messageId}/read")
    public Result<?> markAsRead(@PathVariable Long messageId) {
        messageService.markAsRead(messageId);
        return Result.success();
    }

    @PutMapping("/{messageId}/unread")
    public Result<?> markAsUnread(@PathVariable Long messageId) {
        messageService.markAsUnread(messageId);
        return Result.success();
    }

    @PutMapping("/{messageId}/recall")
    public Result<?> recallMessage(@PathVariable Long messageId) {
        messageService.recallMessage(messageId);
        return Result.success();
    }

    @PostMapping("/{messageId}/report")
    public Result<?> reportMessage(@PathVariable Long messageId, @RequestBody String reason) {
        messageService.reportMessage(messageId, reason);
        return Result.success();
    }

    @GetMapping("/{messageId}/isRead")
    public Result<?> isMessageRead(@PathVariable Long messageId) {
        boolean isRead = messageService.isMessageRead(messageId);
        return Result.success(isRead);
    }

    // 新增端点
    @GetMapping("/user/{userId}")
    public Result<?> getUserMessages(@PathVariable Long userId) {
        List<Message> messages = messageService.getUserMessages(userId);
        return Result.success(messages);
    }

    @GetMapping("/user/{userId}/unread")
    public Result<?> getUserUnreadMessages(@PathVariable Long userId) {
        List<Message> messages = messageService.getUserUnreadMessages(userId);
        return Result.success(messages);
    }

    @GetMapping("/user/{userId}/unread/count")
    public Result<?> getUserUnreadMessageCount(@PathVariable Long userId) {
        long count = messageService.getUserUnreadMessageCount(userId);
        return Result.success(count);
    }

    @PutMapping("/read/batch")
    public Result<?> markMessagesAsRead(@RequestBody List<Long> messageIds) {
        messageService.markMessagesAsRead(messageIds);
        return Result.success();
    }
}
