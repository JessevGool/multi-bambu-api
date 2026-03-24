package com.jessevangool.multi_bambu_api.controller;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jessevangool.multi_bambu_api.dto.request.AddPrinterRequest;
import com.jessevangool.multi_bambu_api.dto.request.UpdatePrinterRequest;
import com.jessevangool.multi_bambu_api.dto.response.PrinterResponse;
import com.jessevangool.multi_bambu_api.dto.response.UpdatePrinterResponse;
import com.jessevangool.multi_bambu_api.service.PrinterManagementService;


@RestController
@RequestMapping("/api/printers")
public class PrinterManagementController {

    private final PrinterManagementService printerManagementService;

    public PrinterManagementController(PrinterManagementService printerManagementService) {
        this.printerManagementService = printerManagementService;
    }

    @PostMapping("/add")
    public CompletableFuture<PrinterResponse> addPrinter(@RequestBody AddPrinterRequest request) {
        return printerManagementService.addPrinter(
                request.hostname(),
                request.accessCode(),
                request.serial());
    }

    @PostMapping("/delete")
    public CompletableFuture<Boolean> removePrinter(@RequestBody UUID id) {
        return printerManagementService.removePrinter(id);
    }

    @GetMapping()
    public CompletableFuture<List<PrinterResponse>> getAllPrinters() {
        return printerManagementService.getAllPrinters();
    }

    @PutMapping("/update/{id}")
    public CompletableFuture<UpdatePrinterResponse> updatePrinter(@PathVariable UUID id, @RequestBody UpdatePrinterRequest request) {
        return printerManagementService.updatePrinter(id, request.name(),request.hostname(), request.accessCode(), request.serial());
    }
    
}
