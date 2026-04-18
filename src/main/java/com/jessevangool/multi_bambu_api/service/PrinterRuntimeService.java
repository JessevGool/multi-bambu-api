package com.jessevangool.multi_bambu_api.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.jessevangool.bambu.PrinterClient;
import com.jessevangool.multi_bambu_api.dto.response.ResponseMessage;
import com.jessevangool.multi_bambu_api.repository.PrinterRepository;
import com.jessevangool.multi_bambu_api.utility.ImageResizer;

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

    // #region None Development Methods

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
    public CompletableFuture<ResponseMessage<Integer>> getPrintPercentage(UUID id) {
        try {
            var client = getPrinterClient(id);
            int printPercentage = client.getLastPrintPercentage();
            return CompletableFuture.completedFuture(new ResponseMessage<>(true, printPercentage));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async
    public CompletableFuture<ResponseMessage<Integer>> getRemainingTime(UUID id) {
        try {
            var client = getPrinterClient(id);
            int remainingTime = client.getRemainingPrintTime();
            return CompletableFuture.completedFuture(new ResponseMessage<>(true, remainingTime));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async
    public CompletableFuture<ResponseMessage<Double>> getBedTemperature(UUID id) {
        try {
            var client = getPrinterClient(id);
            double bedTemperature = client.getBedTemperature();
            return CompletableFuture.completedFuture(new ResponseMessage<>(true, bedTemperature));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async
    public CompletableFuture<ResponseMessage<Double>> getBedTargetTemperature(UUID id) {
        try {
            var client = getPrinterClient(id);
            double bedTargetTemperature = client.getBedTargetTemperature();
            return CompletableFuture.completedFuture(new ResponseMessage<>(true, bedTargetTemperature));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async
    public CompletableFuture<ResponseMessage<Double>> getNozzleTemperature(UUID id) {
        try {
            var client = getPrinterClient(id);
            double nozzleTemperature = client.getNozzleTemperature();
            return CompletableFuture.completedFuture(new ResponseMessage<>(true, nozzleTemperature));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async
    public CompletableFuture<ResponseMessage<Double>> getNozzleTargetTemperature(UUID id) {
        try {
            var client = getPrinterClient(id);
            double nozzleTargetTemperature = client.getNozzleTargetTemperature();
            return CompletableFuture.completedFuture(new ResponseMessage<>(true, nozzleTargetTemperature));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async
    public CompletableFuture<ResponseMessage<Integer>> getFanSpeed(UUID id, int fanIndex) {
        try {
            var client = getPrinterClient(id);
            int fanSpeed = client.getFanSpeed(fanIndex);
            return CompletableFuture.completedFuture(new ResponseMessage<>(true, fanSpeed));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    // #endregion

    // #region Development Methods

    @Async
    public CompletableFuture<ResponseMessage<String>> autoHome(UUID id) {
        try {
            var client = getPrinterClient(id);
            boolean success = client.autoHome();
            return CompletableFuture.completedFuture(new ResponseMessage<>(success, success ? "Auto home command sent" : "Failed to send auto home command"));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async
    public CompletableFuture<ResponseMessage<String>> setFanSpeed(UUID id, int fanIndex, int speed) {
        try {
            var client = getPrinterClient(id);
            boolean success = client.setFanSpeed(speed, fanIndex);
            return CompletableFuture.completedFuture(new ResponseMessage<>(success, success ? "Set fan speed command sent" : "Failed to send set fan speed command"));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }
    // #endregion

    // #region Camera Methods

    @Async
    public CompletableFuture<byte[]> getCameraFrame(UUID id) {
        try {
            var client = getPrinterClient(id);
            return CompletableFuture.completedFuture(client.getCameraFrame());
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async
    public CompletableFuture<byte[]> getResizedCameraFrame(UUID id) {
        try {
            var client = getPrinterClient(id);
            byte[] originalFrame = client.getCameraFrame();
            if (originalFrame == null) {
                return CompletableFuture.completedFuture(null);
            }
            byte[] resizedFrame = ImageResizer.resizeJpeg(originalFrame);
            return CompletableFuture.completedFuture(resizedFrame);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    // #endregion

}
