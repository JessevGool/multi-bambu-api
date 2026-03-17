package com.jessevangool.multi_bambu_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jessevangool.multi_bambu_api.entity.PrinterEntity;

@Repository
public interface PrinterRepository extends JpaRepository<PrinterEntity, UUID> {
    PrinterEntity findByHostname(String hostname);
    PrinterEntity findBySerial(String serial);
    boolean existsBySerialOrHostname(String serial, String hostname);

}
