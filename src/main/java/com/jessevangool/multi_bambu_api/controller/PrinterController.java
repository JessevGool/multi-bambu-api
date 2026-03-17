package com.jessevangool.multi_bambu_api.controller;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jessevangool.multi_bambu_api.dto.response.ResponseMessage;
import com.jessevangool.multi_bambu_api.service.PrinterRuntimeService;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/printer/{id}")
public class PrinterController {

    private final PrinterRuntimeService printerRuntimeService;

    public PrinterController(PrinterRuntimeService printerRuntimeService) {
        this.printerRuntimeService = printerRuntimeService;
    }

    @GetMapping("/bed-temperature")
    public CompletableFuture<ResponseMessage<Double>> getBedTemperature(@PathVariable UUID id) {
        return printerRuntimeService.getBedTemperature(id);
    }

    @GetMapping("/bed-target-temperature")
    public CompletableFuture<ResponseMessage<Double>> getBedTargetTemperature(@PathVariable UUID id) {
        return printerRuntimeService.getBedTargetTemperature(id);
    }

    @GetMapping("/nozzle-temperature")
    public CompletableFuture<ResponseMessage<Double>> getNozzleTemperature(@PathVariable UUID id) {
        return printerRuntimeService.getNozzleTemperature(id);
    }
    
    @GetMapping("/nozzle-target-temperature")
    public CompletableFuture<ResponseMessage<Double>> getNozzleTargetTemperature(@PathVariable UUID id) {
        return printerRuntimeService.getNozzleTargetTemperature(id);
    }

    @GetMapping("/print-progress")
    public CompletableFuture<ResponseMessage<Integer>> getPrintProgress(@PathVariable UUID id) {
        return printerRuntimeService.getPrintPercentage(id);
    }

    @GetMapping("/get-fan-speed")
    public CompletableFuture<ResponseMessage<Integer>> getFanSpeed(@PathVariable UUID id,@Min(1) @Max(3) @RequestParam int fanIndex) {
        return printerRuntimeService.getFanSpeed(id, fanIndex);
    }

    @GetMapping("/led-light-state")
    public CompletableFuture<Boolean> setLedLightState(@PathVariable UUID id, @RequestParam boolean on) {
        return printerRuntimeService.setLedLightState(id, on);
    }
}
