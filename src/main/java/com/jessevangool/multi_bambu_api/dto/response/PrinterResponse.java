package com.jessevangool.multi_bambu_api.dto.response;

import java.util.UUID;

public record PrinterResponse(UUID id, String name, String hostname, String serial) {
}
