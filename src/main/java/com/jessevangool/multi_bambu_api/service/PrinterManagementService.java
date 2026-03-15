package com.jessevangool.multi_bambu_api.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.jessevangool.multi_bambu_api.dto.response.PrinterResponse;
import com.jessevangool.multi_bambu_api.entity.PrinterEntity;
import com.jessevangool.multi_bambu_api.repository.PrinterRepository;

@Service
public class PrinterManagementService {

    private final PrinterRepository printerRepository;

    public PrinterManagementService(PrinterRepository printerRepository) {
        this.printerRepository = printerRepository;
    }

    @Async
    public CompletableFuture<PrinterResponse> addPrinter(String hostname, String accessCode, String serial) {
        if (printerRepository.findByHostname(hostname) != null) {
            throw new IllegalArgumentException("Printer with hostname already exists");
        }

        PrinterEntity printer = new PrinterEntity();
        printer.setHostname(hostname);
        printer.setAccessCode(accessCode);
        printer.setSerial(serial);

        PrinterEntity saved = printerRepository.save(printer);
        return CompletableFuture.completedFuture(toResponse(saved));
    }

    @Async
    public CompletableFuture<Boolean> removePrinter(UUID id) {
        PrinterEntity printer = printerRepository.findById(id).orElse(null);
        if (printer == null) {
            return CompletableFuture.completedFuture(false);
        }
        printerRepository.delete(printer);
        return CompletableFuture.completedFuture(true);
    }

    @Async
    public CompletableFuture<java.util.List<PrinterResponse>> getAllPrinters() {
        java.util.List<PrinterResponse> printers = printerRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
        return CompletableFuture.completedFuture(printers);
    }

    private PrinterResponse toResponse(PrinterEntity entity) {
        return new PrinterResponse(entity.getId(), entity.getHostname(), entity.getSerial());
    }
}
