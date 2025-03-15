package com.socialplatform.message.controller;

import com.socialplatform.common.api.Result;
import com.socialplatform.message.dto.MessageRequest;
import com.socialplatform.message.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public Result<?> createMessage(@Valid @RequestBody MessageRequest request) {
        return Result.success(messageService.createMessage(request));
    }

    @GetMapping
    public Result<?> getMessages(Pageable pageable) {
        return Result.success(messageService.getMessages(pageable));
    }

    @GetMapping("/user/{userId}")
    public Result<?> getUserMessages(@PathVariable Long userId, Pageable pageable) {
        return Result.success(messageService.getUserMessages(userId, pageable));
    }

    @PostMapping("/{messageId}/like")
    public Result<?> likeMessage(@PathVariable Long messageId) {
        messageService.likeMessage(messageId);
        return Result.success();
    }

    @DeleteMapping("/{messageId}/like")
    public Result<?> unlikeMessage(@PathVariable Long messageId) {
        messageService.unlikeMessage(messageId);
        return Result.success();
    }

    @PostMapping("/{messageId}/comment")
    public Result<?> addComment(@PathVariable Long messageId, @RequestBody String content) {
        return Result.success(messageService.addComment(messageId, content));
    }

    @GetMapping("/{messageId}/comments")
    public Result<?> getComments(@PathVariable Long messageId, Pageable pageable) {
        return Result.success(messageService.getComments(messageId, pageable));
    }

    @DeleteMapping("/{messageId}")
    public Result<?> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return Result.success();
    }
}
