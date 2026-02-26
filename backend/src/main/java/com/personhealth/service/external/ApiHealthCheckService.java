package com.personhealth.service.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personhealth.config.ExternalApiConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 外部API连通性检查服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiHealthCheckService {

    private final ExternalApiConfig apiConfig;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 检查所有外部API的连通性
     */
    public Map<String, Object> checkAllApis() {
        Map<String, Object> result = new HashMap<>();

        // 检查摩熵医药API
        result.put("moneng", checkMonengApi());

        // 检查腾讯医疗健康API
        result.put("tencentHealth", checkTencentHealthApi());

        // 检查医院CRM系统API
        result.put("hospitalCrm", checkHospitalCrmApi());

        // 检查国家医保平台API
        result.put("nationalInsurance", checkNationalInsuranceApi());

        // 检查地方医保API
        result.put("localInsurance", checkLocalInsuranceApi());

        return result;
    }

    /**
     * 检查摩熵医药API连通性
     */
    public Map<String, Object> checkMonengApi() {
        Map<String, Object> result = new HashMap<>();

        if (!apiConfig.getMoneng().getEnabled()) {
            result.put("status", "disabled");
            result.put("message", "摩熵医药API未启用");
            return result;
        }

        try {
            String url = apiConfig.getMoneng().getBaseUrl();

            // 发送测试请求
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                result.put("status", "ok");
                result.put("message", "摩熵医药API连接成功");
                result.put("url", url);
            } else {
                result.put("status", "error");
                result.put("message", "摩熵医药API返回错误状态：" + response.getStatusCode());
                result.put("url", url);
            }
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "摩熵医药API连接失败：" + e.getMessage());
            result.put("url", apiConfig.getMoneng().getBaseUrl());
        }

        return result;
    }

    /**
     * 检查腾讯医疗健康API连通性
     */
    public Map<String, Object> checkTencentHealthApi() {
        Map<String, Object> result = new HashMap<>();

        if (!apiConfig.getTencentHealth().getEnabled()) {
            result.put("status", "disabled");
            result.put("message", "腾讯医疗健康API未启用");
            return result;
        }

        try {
            String url = apiConfig.getTencentHealth().getBaseUrl();

            // 发送测试请求
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                result.put("status", "ok");
                result.put("message", "腾讯医疗健康API连接成功");
                result.put("url", url);
            } else {
                result.put("status", "error");
                result.put("message", "腾讯医疗健康API返回错误状态：" + response.getStatusCode());
                result.put("url", url);
            }
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "腾讯医疗健康API连接失败：" + e.getMessage());
            result.put("url", apiConfig.getTencentHealth().getBaseUrl());
        }

        return result;
    }

    /**
     * 检查医院CRM系统API连通性
     */
    public Map<String, Object> checkHospitalCrmApi() {
        Map<String, Object> result = new HashMap<>();

        if (!apiConfig.getHospitalCrm().getEnabled()) {
            result.put("status", "disabled");
            result.put("message", "医院CRM系统API未启用");
            return result;
        }

        try {
            String url = apiConfig.getHospitalCrm().getBaseUrl();

            // 发送测试请求
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                result.put("status", "ok");
                result.put("message", "医院CRM系统API连接成功");
                result.put("url", url);
            } else {
                result.put("status", "error");
                result.put("message", "医院CRM系统API返回错误状态：" + response.getStatusCode());
                result.put("url", url);
            }
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "医院CRM系统API连接失败：" + e.getMessage());
            result.put("url", apiConfig.getHospitalCrm().getBaseUrl());
        }

        return result;
    }

    /**
     * 检查国家医保平台API连通性
     */
    public Map<String, Object> checkNationalInsuranceApi() {
        Map<String, Object> result = new HashMap<>();

        if (!apiConfig.getNationalInsurance().getEnabled()) {
            result.put("status", "disabled");
            result.put("message", "国家医保平台API未启用");
            return result;
        }

        try {
            String url = apiConfig.getNationalInsurance().getBaseUrl();

            // 发送测试请求
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                result.put("status", "ok");
                result.put("message", "国家医保平台API连接成功");
                result.put("url", url);
            } else {
                result.put("status", "error");
                result.put("message", "国家医保平台API返回错误状态：" + response.getStatusCode());
                result.put("url", url);
            }
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "国家医保平台API连接失败：" + e.getMessage());
            result.put("url", apiConfig.getNationalInsurance().getBaseUrl());
        }

        return result;
    }

    /**
     * 检查地方医保API连通性
     */
    public Map<String, Object> checkLocalInsuranceApi() {
        Map<String, Object> result = new HashMap<>();

        if (!apiConfig.getLocalInsurance().getEnabled()) {
            result.put("status", "disabled");
            result.put("message", "地方医保API未启用");
            return result;
        }

        try {
            String url = apiConfig.getLocalInsurance().getBaseUrl();

            // 发送测试请求
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                result.put("status", "ok");
                result.put("message", "地方医保API连接成功");
                result.put("url", url);
            } else {
                result.put("status", "error");
                result.put("message", "地方医保API返回错误状态：" + response.getStatusCode());
                result.put("url", url);
            }
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "地方医保API连接失败：" + e.getMessage());
            result.put("url", apiConfig.getLocalInsurance().getBaseUrl());
        }

        return result;
    }

    /**
     * 测试摩熵医药API的药品查询接口
     */
    public Map<String, Object> testMonengDrugQuery() {
        Map<String, Object> result = new HashMap<>();

        if (!apiConfig.getMoneng().getEnabled()) {
            result.put("status", "disabled");
            result.put("message", "摩熵医药API未启用");
            return result;
        }

        try {
            String url = apiConfig.getMoneng().getDrugCatalogUrl();

            // 构建测试请求
            Map<String, Object> params = new HashMap<>();
            params.put("drugName", "阿司匹林");
            params.put("apiKey", apiConfig.getMoneng().getApiKey());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-API-KEY", apiConfig.getMoneng().getApiKey());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

            // 发送请求
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                result.put("status", "ok");
                result.put("message", "摩熵医药API接口测试成功");
                result.put("url", url);
                result.put("response", response.getBody());
            } else {
                result.put("status", "error");
                result.put("message", "摩熵医药API接口返回错误状态：" + response.getStatusCode());
                result.put("url", url);
            }
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "摩熵医药API接口测试失败：" + e.getMessage());
            result.put("url", apiConfig.getMoneng().getDrugCatalogUrl());
        }

        return result;
    }

    /**
     * 测试腾讯医疗健康API的OCR接口
     */
    public Map<String, Object> testTencentOcr() {
        Map<String, Object> result = new HashMap<>();

        if (!apiConfig.getTencentHealth().getEnabled()) {
            result.put("status", "disabled");
            result.put("message", "腾讯医疗健康API未启用");
            return result;
        }

        try {
            String url = apiConfig.getTencentHealth().getOcrMedicalRecordUrl();

            // 构建测试请求（使用示例图片URL）
            Map<String, Object> params = new HashMap<>();
            params.put("imageUrl", "https://example.com/medical-record.jpg");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-App-Key", apiConfig.getTencentHealth().getAppKey());
            headers.set("X-App-Secret", apiConfig.getTencentHealth().getAppSecret());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

            // 发送请求
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                // BAD_REQUEST可能是因为示例图片无效，但API本身是可访问的
                result.put("status", "ok");
                result.put("message", "腾讯医疗健康API接口可访问");
                result.put("url", url);
                result.put("httpStatus", response.getStatusCode().value());
            } else {
                result.put("status", "error");
                result.put("message", "腾讯医疗健康API接口返回错误状态：" + response.getStatusCode());
                result.put("url", url);
            }
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "腾讯医疗健康API接口测试失败：" + e.getMessage());
            result.put("url", apiConfig.getTencentHealth().getOcrMedicalRecordUrl());
        }

        return result;
    }

    /**
     * 测试医院CRM系统API的患者同步接口
     */
    public Map<String, Object> testHospitalCrmPatientSync() {
        Map<String, Object> result = new HashMap<>();

        if (!apiConfig.getHospitalCrm().getEnabled()) {
            result.put("status", "disabled");
            result.put("message", "医院CRM系统API未启用");
            return result;
        }

        try {
            String url = apiConfig.getHospitalCrm().getPatientSyncUrl();

            // 构建测试请求
            Map<String, Object> params = new HashMap<>();
            params.put("idCardNumber", "110101199001011234");
            params.put("phone", "13800138000");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-API-KEY", apiConfig.getHospitalCrm().getApiKey());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

            // 发送请求
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                // BAD_REQUEST可能是因为示例数据无效，但API本身是可访问的
                result.put("status", "ok");
                result.put("message", "医院CRM系统API接口可访问");
                result.put("url", url);
                result.put("httpStatus", response.getStatusCode().value());
            } else {
                result.put("status", "error");
                result.put("message", "医院CRM系统API接口返回错误状态：" + response.getStatusCode());
                result.put("url", url);
            }
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "医院CRM系统API接口测试失败：" + e.getMessage());
            result.put("url", apiConfig.getHospitalCrm().getPatientSyncUrl());
        }

        return result;
    }

    /**
     * 测试国家医保平台API的电子凭证接口
     */
    public Map<String, Object> testNationalEcode() {
        Map<String, Object> result = new HashMap<>();

        if (!apiConfig.getNationalInsurance().getEnabled()) {
            result.put("status", "disabled");
            result.put("message", "国家医保平台API未启用");
            return result;
        }

        try {
            String url = apiConfig.getNationalInsurance().getEcodeAuthUrl();

            // 构建测试请求
            Map<String, Object> params = new HashMap<>();
            params.put("appId", apiConfig.getNationalInsurance().getAppId());
            params.put("idCardNumber", "110101199001011234");
            params.put("phone", "13800138000");
            params.put("timestamp", System.currentTimeMillis());

            // 简化测试，只验证连接
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                // UNAUTHORIZED可能是因为测试数据无效，但API本身是可访问的
                result.put("status", "ok");
                result.put("message", "国家医保平台API接口可访问");
                result.put("url", url);
                result.put("httpStatus", response.getStatusCode().value());
            } else {
                result.put("status", "error");
                result.put("message", "国家医保平台API接口返回错误状态：" + response.getStatusCode());
                result.put("url", url);
            }
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "国家医保平台API接口测试失败：" + e.getMessage());
            result.put("url", apiConfig.getNationalInsurance().getEcodeAuthUrl());
        }

        return result;
    }

    /**
     * 测试地方医保API的目录查询接口
     */
    public Map<String, Object> testLocalCatalog() {
        Map<String, Object> result = new HashMap<>();

        if (!apiConfig.getLocalInsurance().getEnabled()) {
            result.put("status", "disabled");
            result.put("message", "地方医保API未启用");
            return result;
        }

        try {
            String url = apiConfig.getLocalInsurance().getLocalCatalogUrl();

            // 构建测试请求
            Map<String, Object> params = new HashMap<>();
            params.put("regionCode", apiConfig.getLocalInsurance().getRegionCode());
            params.put("keyword", "药品");
            params.put("apiKey", apiConfig.getLocalInsurance().getApiKey());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-API-KEY", apiConfig.getLocalInsurance().getApiKey());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

            // 发送请求
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                // BAD_REQUEST可能是因为测试数据无效，但API本身是可访问的
                result.put("status", "ok");
                result.put("message", "地方医保API接口可访问");
                result.put("url", url);
                result.put("httpStatus", response.getStatusCode().value());
            } else {
                result.put("status", "error");
                result.put("message", "地方医保API接口返回错误状态：" + response.getStatusCode());
                result.put("url", url);
            }
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "地方医保API接口测试失败：" + e.getMessage());
            result.put("url", apiConfig.getLocalInsurance().getLocalCatalogUrl());
        }

        return result;
    }

    /**
     * 生成连通性检查报告
     */
    public String generateHealthReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== 外部API连通性检查报告 ===\n\n");

        // 检查所有API
        Map<String, Object> healthResults = checkAllApis();

        // 摩熵医药API
        report.append("1. 摩熵医药API\n");
        report.append("   状态：").append(healthResults.get("moneng")).append("\n\n");

        // 腾讯医疗健康API
        report.append("2. 腾讯医疗健康API\n");
        report.append("   状态：").append(healthResults.get("tencentHealth")).append("\n\n");

        // 医院CRM系统API
        report.append("3. 医院CRM系统API\n");
        report.append("   状态：").append(healthResults.get("hospitalCrm")).append("\n\n");

        // 国家医保平台API
        report.append("4. 国家医保平台API\n");
        report.append("   状态：").append(healthResults.get("nationalInsurance")).append("\n\n");

        // 地方医保API
        report.append("5. 地方医保API\n");
        report.append("   状态：").append(healthResults.get("localInsurance")).append("\n\n");

        // 接口测试
        report.append("=== 接口功能测试 ===\n\n");

        if (apiConfig.getMoneng().getEnabled()) {
            Map<String, Object> drugTest = testMonengDrugQuery();
            report.append("摩熵医药API - 药品查询接口\n");
            report.append("   状态：").append(drugTest.get("status")).append("\n");
            report.append("   消息：").append(drugTest.get("message")).append("\n\n");
        }

        if (apiConfig.getTencentHealth().getEnabled()) {
            Map<String, Object> ocrTest = testTencentOcr();
            report.append("腾讯医疗健康API - OCR识别接口\n");
            report.append("   状态：").append(ocrTest.get("status")).append("\n");
            report.append("   消息：").append(ocrTest.get("message")).append("\n\n");
        }

        if (apiConfig.getHospitalCrm().getEnabled()) {
            Map<String, Object> patientTest = testHospitalCrmPatientSync();
            report.append("医院CRM系统API - 患者同步接口\n");
            report.append("   状态：").append(patientTest.get("status")).append("\n");
            report.append("   消息：").append(patientTest.get("message")).append("\n\n");
        }

        if (apiConfig.getNationalInsurance().getEnabled()) {
            Map<String, Object> ecodeTest = testNationalEcode();
            report.append("国家医保平台API - 电子凭证接口\n");
            report.append("   状态：").append(ecodeTest.get("status")).append("\n");
            report.append("   消息：").append(ecodeTest.get("message")).append("\n\n");
        }

        if (apiConfig.getLocalInsurance().getEnabled()) {
            Map<String, Object> catalogTest = testLocalCatalog();
            report.append("地方医保API - 目录查询接口\n");
            report.append("   状态：").append(catalogTest.get("status")).append("\n");
            report.append("   消息：").append(catalogTest.get("message")).append("\n\n");
        }

        report.append("=== 检查完成 ===\n");
        report.append("如需启用API，请在 application.yml 中配置相应的 enabled: true\n");

        return report.toString();
    }
}
