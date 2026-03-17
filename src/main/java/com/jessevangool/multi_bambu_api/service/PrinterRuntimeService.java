package com.jessevangool.multi_bambu_api.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.jessevangool.bambu.PrinterClient;
import com.jessevangool.multi_bambu_api.repository.PrinterRepository;

@Service
public class PrinterRuntimeService {

    private final PrinterRepository printerRepository;
    private final ConcurrentHashMap<UUID, PrinterClient> printerClients;

    public PrinterRuntimeService(PrinterRepository printerRepository) {
        this.printerRepository = printerRepository;
        this.printerClients = new ConcurrentHashMap<>();
    }

    private PrinterClient getPrinterClient(UUID id) throws Exception {
        var existingClient = printerClients.get(id);
        if (existingClient != null) {
            return existingClient;
        }

        synchronized (printerClients) {
            existingClient = printerClients.get(id);
            if (existingClient != null) {
                return existingClient;
            }

            var printerEntity = printerRepository.findById(id).orElse(null);
            if (printerEntity == null) {
                throw new IllegalArgumentException("Printer not found");
            }

            var client = new PrinterClient(printerEntity.getHostname(), printerEntity.getAccessCode(),
                    printerEntity.getSerial());
            client.startClients();
            printerClients.put(id, client);
            return client;
        }
    }

    @Async
    public CompletableFuture<Double> getBedTemperature(UUID id) {
        try {
            var client = getPrinterClient(id);
            return CompletableFuture.completedFuture(client.getBedTemperature());
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async
    public CompletableFuture<Boolean> setLedLightState(UUID id, boolean on) {
        try {
            var client = getPrinterClient(id);

            if (on) {
                client.turnOnLight();
            } else {
                client.turnOffLight();
            }
         
            return CompletableFuture.completedFuture(true);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async
    public CompletableFuture<byte[]> getCameraFrame(UUID id) {
        try {
            var client = getPrinterClient(id);
            return CompletableFuture.completedFuture(client.getCameraFrame());
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    
}
