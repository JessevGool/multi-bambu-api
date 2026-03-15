package com.jessevangool.multi_bambu_api.dto.request;


public record AddPrinterRequest(String hostname, String accessCode, String serial) {}