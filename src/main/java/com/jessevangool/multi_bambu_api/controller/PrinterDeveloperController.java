package com.jessevangool.multi_bambu_api.controller;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jessevangool.multi_bambu_api.domain.limits.PrinterLimits;
import com.jessevangool.multi_bambu_api.dto.response.ResponseMessage;
import com.jessevangool.multi_bambu_api.service.PrinterRuntimeService;

import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/printer/developer/{id}")
public class PrinterDeveloperController {

    private final PrinterRuntimeService printerRuntimeService;

    final int minFanIndex = PrinterLimits.FanIndex.MIN;
    final int maxFanIndex = PrinterLimits.FanIndex.MAX;

    final int minFanSpeed = PrinterLimits.FanSpeed.MIN;
    final int maxFanSpeed = PrinterLimits.FanSpeed.MAX;

    public PrinterDeveloperController(PrinterRuntimeService printerRuntimeService) {
        this.printerRuntimeService = printerRuntimeService;
    }

    @PostMapping("/set-fan-speed")
    public CompletableFuture<ResponseMessage<String>> setFanSpeed(
            @PathVariable UUID id,
            @Min(minFanIndex) @Max(maxFanIndex) @RequestParam int fanIndex,
            @Min(minFanSpeed) @Max(maxFanSpeed) @RequestParam int speed) {
        return printerRuntimeService.setFanSpeed(id, fanIndex, speed);
    }

    @PostMapping("/auto-home")
    public CompletableFuture<ResponseMessage<String>> autoHome(@PathVariable UUID id) {
        return printerRuntimeService.autoHome(id);
    }

}