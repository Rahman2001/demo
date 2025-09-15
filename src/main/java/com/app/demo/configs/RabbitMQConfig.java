package com.app.demo.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue exampleQueue() {
        return new Queue("notes.queue", true);
    }

    @Bean
    public DirectExchange exampleExchange() {
        return new DirectExchange("notes.exchange");
    }

    @Bean
    public Binding exampleBinding(Queue exampleQueue, DirectExchange exampleExchange) {
        return BindingBuilder.bind(exampleQueue)
                .to(exampleExchange)
                .with("proksi.yazilim");
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
