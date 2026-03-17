package com.jessevangool.multi_bambu_api.controller;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jessevangool.multi_bambu_api.service.PrinterRuntimeService;

@RestController
@RequestMapping("/api/printer/{id}")
public class PrinterController {

    private final PrinterRuntimeService printerRuntimeService;

    public PrinterController(PrinterRuntimeService printerRuntimeService) {
        this.printerRuntimeService = printerRuntimeService;
    }

    @GetMapping("/bed-temperature")
    public CompletableFuture<Double> getBedTemperature(@PathVariable UUID id) {
        return printerRuntimeService.getBedTemperature(id);
    }

    @GetMapping("/led-light-state")
    public CompletableFuture<Boolean> setLedLightState(@PathVariable UUID id, @RequestParam boolean on) {
        return printerRuntimeService.setLedLightState(id, on);
    }
}
