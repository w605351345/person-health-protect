package com.personhealth.controller;

import com.personhealth.service.external.ApiHealthCheckService;
import com.personhealth.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 外部API管理控制器
 */
@Tag(name = "外部API管理", description = "外部API连通性检查和配置管理")
@RestController
@RequestMapping("/external-api")
@RequiredArgsConstructor
public class ExternalApiController {

    private final ApiHealthCheckService apiHealthCheckService;

    /**
     * 检查所有外部API的连通性
     */
    @Operation(summary = "检查所有外部API", description = "检查所有已配置的外部API是否可访问")
    @GetMapping("/health-check")
    public Result<Map<String, Object>> checkAllApis() {
        Map<String, Object> result = apiHealthCheckService.checkAllApis();
        return Result.success(result);
    }

    /**
     * 检查指定API的连通性
     */
    @Operation(summary = "检查指定API", description = "检查指定的外部API是否可访问")
    @GetMapping("/health-check/{apiName}")
    public Result<Map<String, Object>> checkApi(@PathVariable String apiName) {
        Map<String, Object> result = new java.util.HashMap<>();

        switch (apiName) {
            case "moneng":
                result = apiHealthCheckService.checkMonengApi();
                break;
            case "tencentHealth":
                result = apiHealthCheckService.checkTencentHealthApi();
                break;
            case "hospitalCrm":
                result = apiHealthCheckService.checkHospitalCrmApi();
                break;
            case "nationalInsurance":
                result = apiHealthCheckService.checkNationalInsuranceApi();
                break;
            case "localInsurance":
                result = apiHealthCheckService.checkLocalInsuranceApi();
                break;
            default:
                result.put("error", "未知的API名称：" + apiName);
                result.put("status", "error");
        }

        return Result.success(result);
    }

    /**
     * 测试指定API的功能接口
     */
    @Operation(summary = "测试API功能", description = "测试指定外部API的功能接口是否可正常工作")
    @GetMapping("/test/{apiName}")
    public Result<Map<String, Object>> testApi(@PathVariable String apiName) {
        Map<String, Object> result = new java.util.HashMap<>();

        switch (apiName) {
            case "moneng":
                result = apiHealthCheckService.testMonengDrugQuery();
                break;
            case "tencentHealth":
                result = apiHealthCheckService.testTencentOcr();
                break;
            case "hospitalCrm":
                result = apiHealthCheckService.testHospitalCrmPatientSync();
                break;
            case "nationalInsurance":
                result = apiHealthCheckService.testNationalEcode();
                break;
            case "localInsurance":
                result = apiHealthCheckService.testLocalCatalog();
                break;
            default:
                result.put("error", "未知的API名称：" + apiName);
                result.put("status", "error");
        }

        return Result.success(result);
    }

    /**
     * 生成连通性检查报告
     */
    @Operation(summary = "生成检查报告", description = "生成所有外部API的连通性检查报告")
    @GetMapping("/health-report")
    public Result<String> generateHealthReport() {
        String report = apiHealthCheckService.generateHealthReport();
        return Result.success(report);
    }

    /**
     * 获取API配置信息（脱敏）
     */
    @Operation(summary = "获取API配置", description = "获取外部API的配置信息（敏感信息已脱敏）")
    @GetMapping("/config")
    public Result<Map<String, Object>> getApiConfig() {
        Map<String, Object> config = new java.util.HashMap<>();

        // 摩熵医药API配置
        Map<String, Object> moneng = new java.util.HashMap<>();
        moneng.put("enabled", false); // 实际配置中读取
        moneng.put("baseUrl", "https://api.moneng.com");
        moneng.put("apiKey", ""); // 脱敏
        moneng.put("timeout", 30000);
        config.put("moneng", moneng);

        // 腾讯医疗健康API配置
        Map<String, Object> tencent = new java.util.HashMap<>();
        tencent.put("enabled", false); // 实际配置中读取
        tencent.put("baseUrl", "https://api.tencent.com/medical");
        tencent.put("appKey", ""); // 脱敏
        tencent.put("timeout", 30000);
        config.put("tencentHealth", tencent);

        // 医院CRM系统API配置
        Map<String, Object> hospital = new java.util.HashMap<>();
        hospital.put("enabled", false); // 实际配置中读取
        hospital.put("baseUrl", "https://api.hospital-crm.com");
        hospital.put("apiKey", ""); // 脱敏
        hospital.put("timeout", 30000);
        config.put("hospitalCrm", hospital);

        // 国家医保平台API配置
        Map<String, Object> national = new java.util.HashMap<>();
        national.put("enabled", false); // 实际配置中读取
        national.put("baseUrl", "https://api.national-medical-insurance.gov.cn");
        national.put("appId", ""); // 脱敏
        national.put("timeout", 30000);
        config.put("nationalInsurance", national);

        // 地方医保API配置
        Map<String, Object> local = new java.util.HashMap<>();
        local.put("enabled", false); // 实际配置中读取
        local.put("baseUrl", "https://api.local-medical-insurance.gov.cn");
        local.put("apiKey", ""); // 脱敏
        local.put("regionCode", "");
        local.put("timeout", 30000);
        config.put("localInsurance", local);

        return Result.success(config);
    }

    /**
     * 获取API地址列表
     */
    @Operation(summary = "获取API地址", description = "获取所有外部API的地址列表")
    @GetMapping("/endpoints")
    public Result<Map<String, String>> getApiEndpoints() {
        Map<String, String> endpoints = new java.util.HashMap<>();

        endpoints.put("monengBaseUrl", "https://api.moneng.com");
        endpoints.put("tencentHealthBaseUrl", "https://api.tencent.com/medical");
        endpoints.put("hospitalCrmBaseUrl", "https://api.hospital-crm.com");
        endpoints.put("nationalInsuranceBaseUrl", "https://api.national-medical-insurance.gov.cn");
        endpoints.put("localInsuranceBaseUrl", "https://api.local-medical-insurance.gov.cn");

        endpoints.put("note", "⚠️ 以上为示例地址，实际使用时请在 application.yml 中配置真实的API地址");

        return Result.success(endpoints);
    }

    /**
     * 验证API地址是否可访问
     */
    @Operation(summary = "验证API地址", description = "验证给定的API地址是否可访问")
    @PostMapping("/verify-url")
    public Result<Map<String, Object>> verifyUrl(@RequestBody Map<String, String> request) {
        String url = request.get("url");
        Map<String, Object> result = new java.util.HashMap<>();

        if (url == null || url.isEmpty()) {
            result.put("status", "error");
            result.put("message", "URL不能为空");
            return Result.error("URL不能为空");
        }

        try {
            // 尝试访问URL
            org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
            org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>("");
            org.springframework.http.ResponseEntity<String> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                result.put("status", "ok");
                result.put("message", "URL可访问");
                result.put("httpStatus", response.getStatusCode().value());
                result.put("url", url);
            } else {
                result.put("status", "warning");
                result.put("message", "URL返回了非成功状态：" + response.getStatusCode());
                result.put("httpStatus", response.getStatusCode().value());
                result.put("url", url);
            }
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "URL无法访问：" + e.getMessage());
            result.put("url", url);
        }

        return Result.success(result);
    }
}
