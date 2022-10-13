package com.yang.smsservice.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

public interface SmsService {
    boolean sendSmsPhone(String phone, Map<String, String> param);
}
