package com.ead.course.consumer;

import com.ead.course.dto.rabbitmq.UserEventDTO;
import com.ead.course.entity.User;
import com.ead.course.mapper.UserMapper;
import com.ead.course.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConsumer {

    private final UserService userService;
    private final UserMapper userMapper;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.userEventQueue}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.userEvent}", type = ExchangeTypes.FANOUT),
            ignoreDeclarationExceptions = "true")
    )
    public void listenUserEvent(@Payload final UserEventDTO userEventDTO) {
        final User user = userMapper.from(userEventDTO);
        userService.insertUser(user);
    }
}
