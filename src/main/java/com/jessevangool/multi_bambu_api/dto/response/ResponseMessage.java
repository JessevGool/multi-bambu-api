package com.jessevangool.multi_bambu_api.dto.response;

public record ResponseMessage<T>(boolean success, T message) {

}
