package com.jessevangool.multi_bambu_api.controller;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jessevangool.multi_bambu_api.service.PrinterRuntimeService;

@RestController
@RequestMapping("/api/printer-camera/{id}")
public class PrinterCameraController {

    private final PrinterRuntimeService printerRuntimeService;

    public PrinterCameraController(PrinterRuntimeService printerRuntimeService) {
        this.printerRuntimeService = printerRuntimeService;
    }

    @GetMapping("/camera-frame")
    public CompletableFuture<ResponseEntity<byte[]>> getCameraFrame(@PathVariable UUID id) {
        return printerRuntimeService.getCameraFrame(id)
                .thenApply(frameData -> {
                    if (frameData == null) {
                        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
                    }

                    return ResponseEntity.ok()
                            .contentType(MediaType.IMAGE_JPEG)
                            .body(frameData);
                });
    }

    @GetMapping("/camera-frame-resized")
    public CompletableFuture<ResponseEntity<byte[]>> getResizedCameraFrame(@PathVariable UUID id) {
        return printerRuntimeService.getResizedCameraFrame(id)
                .thenApply(frameData -> {
                    if (frameData == null) {
                        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
                    }

                    return ResponseEntity.ok()
                            .contentType(MediaType.IMAGE_JPEG)
                            .body(frameData);
                });
    }
}

