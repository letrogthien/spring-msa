package com.letrogthien.auth.kafka;

import com.letrogthien.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventListener {
    private final UserRepository userRepository;

    @KafkaListener(
            topics = "approve-kyc",
            groupId = "auth-service-group-1",
            concurrency = "3"
    )
    public void kycApprovedListener(GenericRecord event) {
        userRepository.findById((UUID) event.get("userId"))
                .ifPresent(user -> {
                    user.setKyc(true);
                    userRepository.save(user);
                });
    }
}
