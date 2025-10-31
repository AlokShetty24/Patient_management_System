package com.pm.analytics_service.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class Kafkaconsumer {
    private static final Logger log = LoggerFactory.getLogger(Kafkaconsumer.class);

    @KafkaListener(topics ="patient",groupId = "analytics_service")
    public  void consumeEvent(byte[] event)
    {
        try {
            PatientEvent patientEvent =PatientEvent.parseFrom(event);

            log.info("Received Patient Event :[Patient_id={},Patient_name={},Patient_email={}]",patientEvent.getPatientId(),patientEvent.getName(),patientEvent.getEmail());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error deserializing event {} ",e.getMessage());
        }
    }
}
