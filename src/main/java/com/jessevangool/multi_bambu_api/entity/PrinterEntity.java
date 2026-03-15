package com.jessevangool.multi_bambu_api.entity;

import java.util.UUID;

import com.jessevangool.multi_bambu_api.converter.EncryptedStringConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PrinterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Convert(converter = EncryptedStringConverter.class)
    private String hostname;

    @Convert(converter = EncryptedStringConverter.class)
    private String accessCode;

    @Convert(converter = EncryptedStringConverter.class)
    private String serial;
}
