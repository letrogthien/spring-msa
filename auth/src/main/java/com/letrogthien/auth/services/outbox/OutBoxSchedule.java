package com.letrogthien.auth.services.outbox;

import com.letrogthien.auth.common.Status;
import com.letrogthien.auth.entities.RegisterOutBox;
import com.letrogthien.auth.kafka.EventProducer;
import com.letrogthien.auth.repositories.RegisterOutBoxRepository;
import com.letrogthien.common.event.RegistrationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutBoxSchedule {
    private final RegisterOutBoxRepository registerOutBoxRepository;
    private final EventProducer eventProducer;


    @Scheduled(fixedRate = 1000)
    public void processRegisterEventOutBox() {
        List<RegisterOutBox> pendingMessages = registerOutBoxRepository.findAllByStatusOrderByCreatedAtAsc(Status.PENDING);
        handleRegisterRecords(pendingMessages);
    }

    @Scheduled(fixedRate = 1000)
    public void processRetryRegisterEventOutBox() {
        List<RegisterOutBox> pendingMessages = registerOutBoxRepository.findAllByStatusOrderByCreatedAtAsc(Status.RETRY);
        handleRegisterRecords(pendingMessages);
    }

    private void handleRegisterRecords(List<RegisterOutBox> pendingMessages) {
        for (var message : pendingMessages) {
            try {
                RegistrationEvent event = RegistrationEvent.newBuilder()
                        .setEmail(message.getEmail())
                        .setUserId(message.getUserId())
                        .setUrlActivation(message.getUrlActivation())
                        .build();
                eventProducer.registerUser(event);
                message.setStatus(Status.SUCCESS);
            } catch (Exception e) {
                message.setStatus(Status.RETRY);
            }
            registerOutBoxRepository.save(message);
        }
    }

}
