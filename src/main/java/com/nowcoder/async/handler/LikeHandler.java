package com.nowcoder.async.handler;

import com.nowcoder.async.EventHandler;
import com.nowcoder.async.EventModel;
import com.nowcoder.async.EventType;
import com.nowcoder.model.EntityType;
import com.nowcoder.model.Message;
import com.nowcoder.model.User;
import com.nowcoder.service.MessageService;
import com.nowcoder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by hasse on 2020/4/1
 */
@Component
public class LikeHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;


    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(model.getActorId());
        message.setToId(model.getEntityOwnerId());
       // message.setToId(model.getActorId());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户" + user.getZhName()
                + model.getType().typeToString(model.getType().getValue())+ EntityType.getType(model.getEntityType())+model.getEntityId());
        message.setCreatedDate(new Date());
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
