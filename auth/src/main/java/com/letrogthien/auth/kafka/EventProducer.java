package com.letrogthien.auth.kafka;

import com.letrogthien.common.event.RegistrationEvent;
import com.letrogthien.common.event.StrangeDevice;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void registerUser(RegistrationEvent event) {
        this.sendEvent(KafkaTopic.REGISTER.getTopicName(), event);
    }

    public void forgotPassword(Object event) {
        this.sendEvent(KafkaTopic.FORGOT_PASSWORD.getTopicName(), event);
    }

    public void changePassword(Object event) {
        this.sendEvent(KafkaTopic.CHANGE_PASSWORD.getTopicName(), event);
    }

    public void sendOtp(Object event) {
        this.sendEvent(KafkaTopic.SEND_OTP.getTopicName(), event);
    }

    public void strangeDevice(StrangeDevice event) {
        this.sendEvent(KafkaTopic.STRANGE_DEVICE.getTopicName(), event);
    }

    private void sendEvent(String topic, Object event) {
        this.kafkaTemplate.send(topic, event);
    }


}
