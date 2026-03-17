package com.jessevangool.multi_bambu_api.dto.response;

import java.util.UUID;

public record UpdatePrinterResponse(UUID id, boolean success, String message) {

}
