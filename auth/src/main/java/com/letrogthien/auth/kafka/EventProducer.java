package com.letrogthien.auth.kafka;

import com.letrogthien.common.event.RegistrationEvent;
import com.letrogthien.common.event.StrangeDevice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class EventProducer {
    private final SendEvent s;

    public void registerUser(RegistrationEvent event) {
        s.sendEvent(KafkaTopic.REGISTER.getTopicName(), event);
    }

    public void forgotPassword(Object event) {
        s.sendEvent(KafkaTopic.FORGOT_PASSWORD.getTopicName(), event);
    }

    public void changePassword(Object event) {
        s.sendEvent(KafkaTopic.CHANGE_PASSWORD.getTopicName(), event);
    }

    public void sendOtp(Object event) {
        s.sendEvent(KafkaTopic.SEND_OTP.getTopicName(), event);
    }

    public void strangeDevice(StrangeDevice event) {
        s.sendEvent(KafkaTopic.STRANGE_DEVICE.getTopicName(), event);
    }




}
