package com.jessevangool.multi_bambu_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jessevangool.multi_bambu_api.service.PrinterRuntimeService;

@RestController
@RequestMapping("/api/printer/developer/{id}")
public class PrinterDeveloperController {

    private final PrinterRuntimeService printerRuntimeService;

    public PrinterDeveloperController(PrinterRuntimeService printerRuntimeService) {
        this.printerRuntimeService = printerRuntimeService;
    }

    
}