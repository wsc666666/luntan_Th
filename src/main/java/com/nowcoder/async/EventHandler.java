package com.nowcoder.async;

import java.util.List;

/**
 * Created by hasse on 2020/4/1
 */
public interface EventHandler {
    void doHandle(EventModel model);
    List<EventType> getSupportEventTypes();
}
