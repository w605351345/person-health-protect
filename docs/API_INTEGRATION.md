# 外部API对接文档

## 📋 目录

- [概述](#概述)
- [医院相关API对接](#医院相关api对接)
- [医保相关API对接](#医保相关api对接)
- [配置说明](#配置说明)
- [使用示例](#使用示例)
- [注意事项](#注意事项)

---

## 概述

本系统支持与以下权威平台进行对接：

### 医院相关API
- **摩熵医药API**：医药研发、临床诊疗、医疗器械数据查询等
- **腾讯医疗健康API**：智慧医院服务、病历质量控制、药品信息查询等
- **医院CRM系统API**：患者数据同步、预约挂号、检查报告查询等

### 医保相关API
- **国家医保平台API**：医保电子凭证授权、刷脸支付、费用结算等
- **地方医保API**：地方医保目录查询、报销政策获取等
- **第三方医保数据API**：医保药品分类代码查询、市场需求分析等

---

## 医院相关API对接

### 一、摩熵医药API

#### 对接场景
医药研发、临床诊疗、医疗器械数据查询等

#### 数据覆盖
- 涵盖50亿结构化数据
- 支持200余种接口
- 医保药品目录查询
- 药品分类代码查询

#### 对接方式

1. **注册开发者账号**
   - 访问摩熵医药开放平台官网
   - 注册开发者账号
   - 完成企业认证

2. **获取API Key**
   - 进入应用管理页面
   - 创建新应用
   - 获取API Key和API Secret

3. **调用接口示例**

```java
// 查询医保药品目录
@RestController
@RequestMapping("/api/external/moneng")
public class MonengApiController {

    @Value("${external.moneng.api-key}")
    private String apiKey;

    @GetMapping("/drug-catalog")
    public Map<String, Object> queryDrugCatalog(@RequestParam String drugName) {
        RestTemplate restTemplate = new RestTemplate();

        // 构建请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("drugName", drugName);
        params.put("apiKey", apiKey);

        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

        // 发送请求
        ResponseEntity<String> response = restTemplate.postForEntity(
            "https://api.moneng.com/v1/drug/catalog",
            request,
            String.class
        );

        // 解析响应
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.getBody());
        JsonNode dataNode = rootNode.path("data");

        Map<String, Object> result = new HashMap<>();
        result.put("drugName", dataNode.path("drugName").asText());
        result.put("dosageForm", dataNode.path("dosageForm").asText());
        result.put("medicalInsuranceType", dataNode.path("medicalInsuranceType").asText());
        result.put("medicalInsuranceCategory", dataNode.path("medicalInsuranceCategory").asText());

        return result;
    }
}
```

#### 配置项

```yaml
external:
  moneng:
    enabled: true
    base-url: https://api.moneng.com
    api-key: your_moneng_api_key
    api-secret: your_moneng_api_secret
    timeout: 30000
```

---

### 二、腾讯医疗健康API

#### 对接场景
智慧医院服务、病历质量控制、药品信息查询等

#### 核心接口
- 医学术语
- ICD编码
- OCR和NLP
- 医典

#### 对接方式

1. **申请服务**
   - 登录腾讯云官网
   - 申请医疗健康API服务
   - 获取访问密钥（AppKey和AppSecret）
   - 配置权限范围

2. **调用接口示例**

```java
// OCR病历识别
@RestController
@RequestMapping("/api/external/tencent")
public class TencentHealthApiController {

    @Value("${external.tencent-health.app-key}")
    private String appKey;

    @Value("${external.tencent-health.app-secret}")
    private String appSecret;

    @PostMapping("/ocr/medical-record")
    public Map<String, Object> ocrMedicalRecord(@RequestParam String imageUrl) {
        RestTemplate restTemplate = new RestTemplate();

        // 构建请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("imageUrl", imageUrl);

        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-App-Key", appKey);
        headers.set("X-App-Secret", appSecret);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

        // 发送请求
        ResponseEntity<String> response = restTemplate.postForEntity(
            "https://api.tencent.com/medical/v1/ocr/medical-record",
            request,
            String.class
        );

        // 解析响应
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.getBody());
        JsonNode dataNode = rootNode.path("data");

        Map<String, Object> result = new HashMap<>();
        result.put("patientName", dataNode.path("patientName").asText());
        result.put("diagnosis", dataNode.path("diagnosis").asText());
        result.put("prescription", dataNode.path("prescription").asText());
        result.put("hospitalName", dataNode.path("hospitalName").asText());

        return result;
    }
}
```

#### 配置项

```yaml
external:
  tencent-health:
    enabled: true
    base-url: https://api.tencent.com/medical
    app-key: your_tencent_app_key
    app-secret: your_tencent_app_secret
    timeout: 30000
```

---

### 三、医院CRM系统API

#### 对接场景
患者数据同步、预约挂号、检查报告查询等

#### 典型案例
康策医院CRM支持与HIS、LIS、PACS等系统无缝对接

#### 对接方式

1. **联系供应商**
   - 联系医院CRM供应商（如康策）
   - 获取OpenAPI接口文档
   - 签署合作协议

2. **获取Token**
   - 调用登录接口获取Token
   - Token用于后续数据访问

3. **调用接口示例**

```java
// 查询检查报告
@RestController
@RequestMapping("/api/external/hospital-crm")
public class HospitalCrmApiController {

    @Value("${external.hospital-crm.api-key}")
    private String apiKey;

    @PostMapping("/report/query")
    public Map<String, Object> queryReport(
            @RequestParam String patientId,
            @RequestParam String reportType) {

        RestTemplate restTemplate = new RestTemplate();

        // 构建请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("patientId", patientId);
        params.put("reportType", reportType);

        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

        // 发送请求
        ResponseEntity<String> response = restTemplate.postForEntity(
            "https://api.hospital-crm.com/v1/report/query",
            request,
            String.class
        );

        // 解析响应
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.getBody());
        JsonNode dataNode = rootNode.path("data");

        Map<String, Object> result = new HashMap<>();
        result.put("reportId", dataNode.path("reportId").asText());
        result.put("reportName", dataNode.path("reportName").asText());
        result.put("reportDate", dataNode.path("reportDate").asText());
        result.put("reportResult", dataNode.path("reportResult").asText());

        return result;
    }
}
```

#### 配置项

```yaml
external:
  hospital-crm:
    enabled: true
    base-url: https://api.hospital-crm.com
    api-key: your_hospital_crm_api_key
    timeout: 30000
```

---

## 医保相关API对接

### 一、国家医保平台API

#### 对接场景
医保电子凭证授权、刷脸支付、费用结算等

#### 前置准备
- 拥有互联网医院主体公众号/小程序
- 完成线上问诊和交易系统开发

#### 申请流程

1. **提交申请**
   - 进入国家医保服务平台官网
   - 提交API接入申请
   - 填写申请表格，提供企业资质、技术方案等材料

2. **审核通过**
   - 完成技术对接和测试
   - 获取生产环境权限

#### 技术实现

1. **使用核心动态链接库**
   - 使用NationECCode.dll等核心库
   - 通过C#等语言调用封装好的函数
   - 如NationEcTrans

2. **组装业务报文**
   - 处理加密和签名
   - 实现刷脸支付、参保人信息查询等功能

#### 调用示例

```java
// 医保电子凭证授权
@RestController
@RequestMapping("/api/external/national-insurance")
public class NationalInsuranceApiController {

    @Value("${external.national-insurance.app-id}")
    private String appId;

    @Value("${external.national-insurance.app-secret}")
    private String appSecret;

    @PostMapping("/ecode/auth")
    public Map<String, Object> getEcodeAuth(
            @RequestParam String idCardNumber,
            @RequestParam String phone) {

        RestTemplate restTemplate = new RestTemplate();

        // 构建请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("appId", appId);
        params.put("idCardNumber", idCardNumber);
        params.put("phone", phone);
        params.put("timestamp", System.currentTimeMillis());

        // 生成签名
        String signature = generateSignature(params, appSecret);

        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-App-Id", appId);
        headers.set("X-App-Secret", signature);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

        // 发送请求
        ResponseEntity<String> response = restTemplate.postForEntity(
            "https://api.national-medical-insurance.gov.cn/v1/ecode/auth",
            request,
            String.class
        );

        // 解析响应
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.getBody());
        JsonNode dataNode = rootNode.path("data");

        Map<String, Object> result = new HashMap<>();
        result.put("ecodeToken", dataNode.path("ecodeToken").asText());
        result.put("ecodeUrl", dataNode.path("ecodeUrl").asText());
        result.put("expireTime", dataNode.path("expireTime").asLong());

        return result;
    }

    /**
     * 生成签名
     */
    private String generateSignature(Map<String, Object> params, String appSecret) {
        TreeMap<String, Object> sortedParams = new TreeMap<>(params);
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Object> entry : sortedParams.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }

        sb.append("&key=").append(appSecret);

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(sb.toString().getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : bytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString().toUpperCase();
        } catch (Exception e) {
            return "";
        }
    }
}
```

#### 配置项

```yaml
external:
  national-insurance:
    enabled: true
    base-url: https://api.national-medical-insurance.gov.cn
    app-id: your_national_insurance_app_id
    app-secret: your_national_insurance_app_secret
    timeout: 30000
```

---

### 二、地方医保API

#### 对接场景
地方医保目录查询、报销政策获取等

#### 对接方式

1. **联系当地医保局**
   - 直接联系所在地医保局或相关机构
   - 了解具体申请流程

2. **提交材料**
   - 企业资质
   - 技术文档
   - 完成审核后获取API访问权限

3. **调用接口**
   - 根据地方医保平台提供的接口文档
   - 调用相应接口实现功能

#### 调用示例

```java
// 查询报销政策
@RestController
@RequestMapping("/api/external/local-insurance")
public class LocalInsuranceApiController {

    @Value("${external.local-insurance.api-key}")
    private String apiKey;

    @Value("${external.local-insurance.region-code}")
    private String regionCode;

    @PostMapping("/policy/query")
    public Map<String, Object> queryReimbursementPolicy(
            @RequestParam String diseaseType) {

        RestTemplate restTemplate = new RestTemplate();

        // 构建请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("regionCode", regionCode);
        params.put("diseaseType", diseaseType);
        params.put("apiKey", apiKey);

        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

        // 发送请求
        ResponseEntity<String> response = restTemplate.postForEntity(
            "https://api.local-medical-insurance.gov.cn/v1/policy/query",
            request,
            String.class
        );

        // 解析响应
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.getBody());
        JsonNode dataNode = rootNode.path("data");

        Map<String, Object> result = new HashMap<>();
        result.put("policyName", dataNode.path("policyName").asText());
        result.put("reimbursementRatio", dataNode.path("reimbursementRatio").asDouble());
        result.put("limitAmount", dataNode.path("limitAmount").asDouble());
        result.put("description", dataNode.path("description").asText());

        return result;
    }
}
```

#### 配置项

```yaml
external:
  local-insurance:
    enabled: true
    base-url: https://api.local-medical-insurance.gov.cn
    api-key: your_local_insurance_api_key
    region-code: 110000  # 地区代码，如：110000-北京
    timeout: 30000
```

---

### 三、第三方医保数据API

#### 对接场景
医保药品分类代码查询、市场需求分析等

#### 典型案例
摩熵数科开放平台提供医保药品目录查询API，收录国家及地方医保目录数据

#### 对接方式

1. **注册平台账号**
   - 注册摩熵数科开放平台账号
   - 申请医保药品查询API权限

2. **调用接口**
   - 根据文档调用接口
   - 通过药品通用名称获取医保类别、执行状态等详细信息

#### 调用示例

```java
// 查询医保药品分类代码
@RestController
@RequestMapping("/api/external/moneng/insurance")
public class MonengInsuranceApiController {

    @Value("${external.moneng.api-key}")
    private String apiKey;

    @GetMapping("/drug-category")
    public Map<String, Object> queryDrugCategory(@RequestParam String categoryName) {
        RestTemplate restTemplate = new RestTemplate();

        // 构建请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("categoryName", categoryName);
        params.put("apiKey", apiKey);

        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

        // 发送请求
        ResponseEntity<String> response = restTemplate.postForEntity(
            "https://api.moneng.com/v1/drug/category",
            request,
            String.class
        );

        // 解析响应
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.getBody());
        JsonNode dataNode = rootNode.path("data");

        Map<String, Object> result = new HashMap<>();
        result.put("categoryCode", dataNode.path("categoryCode").asText());
        result.put("categoryName", dataNode.path("categoryName").asText());
        result.put("insuranceCategory", dataNode.path("insuranceCategory").asText());
        result.put("executionStatus", dataNode.path("executionStatus").asText());

        return result;
    }
}
```

---

## 配置说明

### 启用API

在 `application.yml` 中设置对应的 `enabled` 为 `true`：

```yaml
external:
  moneng:
    enabled: true  # 启用摩熵医药API
  tencent-health:
    enabled: true  # 启用腾讯医疗健康API
  hospital-crm:
    enabled: true  # 启用医院CRM系统API
  national-insurance:
    enabled: true  # 启用国家医保平台API
  local-insurance:
    enabled: true  # 启用地方医保API
```

### 配置API密钥

将申请到的API密钥配置到 `application.yml` 中：

```yaml
external:
  moneng:
    api-key: your_moneng_api_key
    api-secret: your_moneng_api_secret
  tencent-health:
    app-key: your_tencent_app_key
    app-secret: your_tencent_app_secret
  hospital-crm:
    api-key: your_hospital_crm_api_key
  national-insurance:
    app-id: your_national_insurance_app_id
    app-secret: your_national_insurance_app_secret
  local-insurance:
    api-key: your_local_insurance_api_key
    region-code: 110000
```

---

## 使用示例

### 在医疗记录服务中使用

系统已集成外部API对接服务，可在 `MedicalServiceImpl` 中使用：

```java
@Service
public class MedicalServiceImpl implements MedicalService {

    private final InsuranceApiServiceImpl insuranceApiService;
    private final HospitalApiServiceImpl hospitalApiService;

    @Override
    public List<MedicalVisit> getVisitsFromInsurance() {
        // 自动从医保服务获取就医记录
        return insuranceApiService.fetchMedicalVisitsFromInsurance(idCardNumber);
    }

    @Override
    public List<MedicalVisit> getVisitsFromHospital() {
        // 自动从医院CRM系统获取检查报告
        var reportData = hospitalApiService.queryReportFromCrm(patientId, "all");
        // 构建就医记录
        return visits;
    }
}
```

---

## 注意事项

### 安全合规
- ⚠️ 遵守《数据安全法》《个人信息保护法》
- ⚠️ 禁止传输敏感信息
- ⚠️ 使用HTTPS加密传输

### 性能优化
- ⚠️ 采用异步调用降低延迟
- ⚠️ 使用缓存机制减少重复请求
- ⚠️ 控制并发请求数量

### 监控维护
- ⚠️ 实时监控API调用成功率
- ⚠️ 监控响应延迟等指标
- ⚠️ 及时处理异常

### 版本管理
- ⚠️ 关注接口版本更新
- ⚠️ 确保与下游系统兼容
- ⚠️ 定期更新接口文档

### 开发建议
- ⚠️ 先在测试环境验证
- ⚠️ 使用Mock数据进行开发
- ⚠️ 生产环境逐步上线
- ⚠️ 做好回滚准备

---

## 技术支持

如有对接问题，请联系：

- 摩熵医药开放平台：https://www.moneng.com
- 腾讯云医疗健康：https://cloud.tencent.com/product/medical
- 国家医保服务平台：https://www.nhsa.gov.cn
- 各地医保局：联系当地医保机构

---

**对接完成后，请更新 [部署文档](DEPLOYMENT.md) 和 [测试文档](TESTING.md) 中的相关内容。**
