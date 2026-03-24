package com.jessevangool.multi_bambu_api.dto.request;

public record UpdatePrinterRequest(String name, String hostname, String accessCode, String serial) {}
