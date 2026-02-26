package com.personhealth.service.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personhealth.config.ExternalApiConfig;
import com.personhealth.entity.MedicalVisit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 医院服务对接实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HospitalApiServiceImpl {

    private final ExternalApiConfig apiConfig;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 从摩熵医药API查询医保药品信息
     */
    public Map<String, Object> queryDrugFromMoneng(String drugName) {
        if (!apiConfig.getMoneng().getEnabled()) {
            log.warn("摩熵医药API未启用");
            return new HashMap<>();
        }

        try {
            String url = apiConfig.getMoneng().getDrugCatalogUrl();

            // 构建请求参数
            Map<String, Object> params = new HashMap<>();
            params.put("drugName", drugName);
            params.put("apiKey", apiConfig.getMoneng().getApiKey());

            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-API-KEY", apiConfig.getMoneng().getApiKey());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

            // 发送请求
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            // 解析响应
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode rootNode = objectMapper.readTree(response.getBody());
                JsonNode dataNode = rootNode.path("data");

                Map<String, Object> result = new HashMap<>();
                result.put("drugName", dataNode.path("drugName").asText());
                result.put("dosageForm", dataNode.path("dosageForm").asText());
                result.put("medicalInsuranceType", dataNode.path("medicalInsuranceType").asText());
                result.put("medicalInsuranceCategory", dataNode.path("medicalInsuranceCategory").asText());

                log.info("从摩熵医药API查询药品信息成功，药品：{}", drugName);
                return result;
            }

            return new HashMap<>();
        } catch (Exception e) {
            log.error("从摩熵医药API查询药品信息失败", e);
            return new HashMap<>();
        }
    }

    /**
     * 从腾讯医疗健康API查询医学术语
     */
    public Map<String, Object> queryMedicalTerm(String term) {
        if (!apiConfig.getTencentHealth().getEnabled()) {
            log.warn("腾讯医疗健康API未启用");
            return new HashMap<>();
        }

        try {
            String url = apiConfig.getTencentHealth().getMedicalTermUrl();

            // 构建请求参数
            Map<String, Object> params = new HashMap<>();
            params.put("term", term);

            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-App-Key", apiConfig.getTencentHealth().getAppKey());
            headers.set("X-App-Secret", apiConfig.getTencentHealth().getAppSecret());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

            // 发送请求
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            // 解析响应
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode rootNode = objectMapper.readTree(response.getBody());
                JsonNode dataNode = rootNode.path("data");

                Map<String, Object> result = new HashMap<>();
                result.put("term", dataNode.path("term").asText());
                result.put("definition", dataNode.path("definition").asText());
                result.put("category", dataNode.path("category").asText());
                result.put("englishName", dataNode.path("englishName").asText());

                log.info("从腾讯医疗健康API查询医学术语成功，术语：{}", term);
                return result;
            }

            return new HashMap<>();
        } catch (Exception e) {
            log.error("从腾讯医疗健康API查询医学术语失败", e);
            return new HashMap<>();
        }
    }

    /**
     * 从腾讯医疗健康API识别OCR病历
     */
    public Map<String, Object> ocrMedicalRecord(String imageUrl) {
        if (!apiConfig.getTencentHealth().getEnabled()) {
            log.warn("腾讯医疗健康API未启用");
            return new HashMap<>();
        }

        try {
            String url = apiConfig.getTencentHealth().getOcrMedicalRecordUrl();

            // 构建请求参数
            Map<String, Object> params = new HashMap<>();
            params.put("imageUrl", imageUrl);

            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-App-Key", apiConfig.getTencentHealth().getAppKey());
            headers.set("X-App-Secret", apiConfig.getTencentHealth().getAppSecret());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

            // 发送请求
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            // 解析响应
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode rootNode = objectMapper.readTree(response.getBody());
                JsonNode dataNode = rootNode.path("data");

                Map<String, Object> result = new HashMap<>();
                result.put("patientName", dataNode.path("patientName").asText());
                result.put("diagnosis", dataNode.path("diagnosis").asText());
                result.put("prescription", dataNode.path("prescription").asText());
                result.put("hospitalName", dataNode.path("hospitalName").asText());

                log.info("从腾讯医疗健康API识别OCR病历成功");
                return result;
            }

            return new HashMap<>();
        } catch (Exception e) {
            log.error("从腾讯医疗健康API识别OCR病历失败", e);
            return new HashMap<>();
        }
    }

    /**
     * 从医院CRM系统同步患者数据
     */
    public Map<String, Object> syncPatientFromCrm(String idCardNumber, String phone) {
        if (!apiConfig.getHospitalCrm().getEnabled()) {
            log.warn("医院CRM系统API未启用");
            return new HashMap<>();
        }

        try {
            String url = apiConfig.getHospitalCrm().getPatientSyncUrl();

            // 构建请求参数
            Map<String, Object> params = new HashMap<>();
            params.put("idCardNumber", idCardNumber);
            params.put("phone", phone);

            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-API-KEY", apiConfig.getHospitalCrm().getApiKey());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

            // 发送请求
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            // 解析响应
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode rootNode = objectMapper.readTree(response.getBody());
                JsonNode dataNode = rootNode.path("data");

                Map<String, Object> result = new HashMap<>();
                result.put("patientId", dataNode.path("patientId").asText());
                result.put("patientName", dataNode.path("patientName").asText());
                result.put("gender", dataNode.path("gender").asInt());
                result.put("birthday", dataNode.path("birthday").asText());
                result.put("phone", dataNode.path("phone").asText());

                log.info("从医院CRM系统同步患者数据成功");
                return result;
            }

            return new HashMap<>();
        } catch (Exception e) {
            log.error("从医院CRM系统同步患者数据失败", e);
            return new HashMap<>();
        }
    }

    /**
     * 从医院CRM系统查询检查报告
     */
    public Map<String, Object> queryReportFromCrm(String patientId, String reportType) {
        if (!apiConfig.getHospitalCrm().getEnabled()) {
            log.warn("医院CRM系统API未启用");
            return new HashMap<>();
        }

        try {
            String url = apiConfig.getHospitalCrm().getReportQueryUrl();

            // 构建请求参数
            Map<String, Object> params = new HashMap<>();
            params.put("patientId", patientId);
            params.put("reportType", reportType);

            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-API-KEY", apiConfig.getHospitalCrm().getApiKey());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

            // 发送请求
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            // 解析响应
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode rootNode = objectMapper.readTree(response.getBody());
                JsonNode dataNode = rootNode.path("data");

                Map<String, Object> result = new HashMap<>();
                result.put("reportId", dataNode.path("reportId").asText());
                result.put("reportName", dataNode.path("reportName").asText());
                result.put("reportDate", dataNode.path("reportDate").asText());
                result.put("reportResult", dataNode.path("reportResult").asText());
                result.put("doctorName", dataNode.path("doctorName").asText());

                log.info("从医院CRM系统查询检查报告成功");
                return result;
            }

            return new HashMap<>();
        } catch (Exception e) {
            log.error("从医院CRM系统查询检查报告失败", e);
            return new HashMap<>();
        }
    }

    /**
     * 从医院CRM系统查询实时排班
     */
    public Map<String, Object> queryScheduleFromCrm(String department, String date) {
        if (!apiConfig.getHospitalCrm().getEnabled()) {
            log.warn("医院CRM系统API未启用");
            return new HashMap<>();
        }

        try {
            String url = apiConfig.getHospitalCrm().getScheduleQueryUrl();

            // 构建请求参数
            Map<String, Object> params = new HashMap<>();
            params.put("department", department);
            params.put("date", date);

            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-API-KEY", apiConfig.getHospitalCrm().getApiKey());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

            // 发送请求
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            // 解析响应
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode rootNode = objectMapper.readTree(response.getBody());
                JsonNode dataNode = rootNode.path("data");

                Map<String, Object> result = new HashMap<>();
                result.put("department", dataNode.path("department").asText());
                result.put("date", dataNode.path("date").asText());
                result.put("availableDoctors", dataNode.path("availableDoctors").asInt());
                result.put("availableSlots", dataNode.path("availableSlots").asInt());

                log.info("从医院CRM系统查询实时排班成功");
                return result;
            }

            return new HashMap<>();
        } catch (Exception e) {
            log.error("从医院CRM系统查询实时排班失败", e);
            return new HashMap<>();
        }
    }

    /**
     * 构建就医记录对象
     */
    public MedicalVisit buildMedicalVisit(Map<String, Object> data) {
        return MedicalVisit.builder()
                .hospitalName((String) data.get("hospitalName"))
                .hospitalLevel(data.get("hospitalLevel") != null ? data.get("hospitalLevel").toString() : null)
                .department((String) data.get("department"))
                .doctorName((String) data.get("doctorName"))
                .diagnosis((String) data.get("diagnosis"))
                .prescription((String) data.get("prescription"))
                .medicalExpense(data.get("medicalExpense") != null ? new java.math.BigDecimal(data.get("medicalExpense").toString()) : null)
                .insuranceReimbursement(data.get("insuranceReimbursement") != null ? new java.math.BigDecimal(data.get("insuranceReimbursement").toString()) : null)
                .dataSource(1) // 医院来源
                .build();
    }
}
