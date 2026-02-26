# API 对接文档

## 📋 目录

- [概述](#概述)
- [API地址说明](#api地址说明)
- [医院相关API对接](#医院相关api对接)
- [医保相关API对接](#医保相关api对接)
- [API连通性检查](#api连通性检查)
- [配置说明](#配置说明)
- [注意事项](#注意事项)

---

## 概述

本系统支持与以下权威平台进行对接，所有API地址和密钥均可配置，无需修改代码。

### 支持的平台

| 平台 | 用途 | 配置项 |
|------|------|--------|
| 摩熵医药API | 医保药品目录查询、药品分类代码查询 | moneng |
| 腾讯医疗健康API | 医学术语、ICD编码、OCR病历识别、NLP医典 | tencentHealth |
| 医院CRM系统API | 患者数据同步、预约挂号、检查报告查询 | hospitalCrm |
| 国家医保平台API | 医保电子凭证、刷脸支付、费用结算 | nationalInsurance |
| 地方医保API | 地方医保目录、报销政策 | localInsurance |

---

## API地址说明

### ⚠️ 重要提示

**当前配置中的所有API地址均为示例地址，不是真实可访问的地址！**

### 示例地址列表

| API名称 | 当前配置地址 | 说明 |
|---------|-------------|------|
| 摩熵医药API | `https://api.moneng.com` | **示例地址**，请填写真实API地址 |
| 腾讯医疗健康API | `https://api.tencent.com/medical` | **示例地址**，请填写真实API地址 |
| 医院CRM系统API | `https://api.hospital-crm.com` | **示例地址**，请填写真实API地址 |
| 国家医保平台API | `https://api.national-medical-insurance.gov.cn` | **示例地址**，请填写真实API地址 |
| 地方医保API | `https://api.local-medical-insurance.gov.cn` | **示例地址**，请填写真实API地址 |

### 如何获取真实API地址

1. **摩熵医药API**
   - 访问：https://www.moneng.com
   - 注册开发者账号
   - 获取真实API地址

2. **腾讯医疗健康API**
   - 访问：https://cloud.tencent.com/product/medical
   - 申请API服务
   - 获取真实API地址

3. **医院CRM系统API**
   - 联系医院CRM供应商（如康策）
   - 获取OpenAPI文档
   - 获取真实API地址

4. **国家医保平台API**
   - 访问：https://www.nhsa.gov.cn
   - 提交API接入申请
   - 获取真实API地址

5. **地方医保API**
   - 联系当地医保局
   - 了解具体申请流程
   - 获取真实API地址

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

#### 对接流程

1. **注册开发者账号**
   ```
   访问：https://www.moneng.com
   注册：开发者账号
   认证：企业认证
   ```

2. **获取API信息**
   ```
   登录：开发者后台
   创建：新应用
   获取：API Key、API Secret、真实API地址
   ```

3. **配置系统**
   ```yaml
   external:
     api:
       moneng:
         enabled: true
         base-url: https://真实API地址.com  # 填写真实地址
         api-key: your_moneng_api_key
         api-secret: your_moneng_api_secret
   ```

#### 接口列表

| 接口名称 | 方法 | 路径 | 说明 |
|---------|------|------|------|
| 医保药品目录查询 | POST | /v1/drug/catalog | 查询医保药品信息 |
| 药品分类代码查询 | POST | /v1/drug/category | 查询药品分类代码 |

#### 调用示例

```bash
curl -X POST "https://真实API地址.com/v1/drug/catalog" \
  -H "Content-Type: application/json" \
  -H "X-API-KEY: your_moneng_api_key" \
  -d '{
    "drugName": "阿司匹林"
  }'
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

#### 对接流程

1. **申请服务**
   ```
   访问：https://cloud.tencent.com
   申请：医疗健康API服务
   获取：访问密钥（AppKey、AppSecret）、真实API地址
   ```

2. **配置系统**
   ```yaml
   external:
     api:
       tencent-health:
         enabled: true
         base-url: https://真实API地址.com/medical  # 填写真实地址
         app-key: your_tencent_app_key
         app-secret: your_tencent_app_secret
   ```

#### 接口列表

| 接口名称 | 方法 | 路径 | 说明 |
|---------|------|------|------|
| 医学术语查询 | POST | /v1/term/query | 查询医学术语 |
| ICD编码查询 | POST | /v1/icd/query | 查询ICD编码 |
| OCR病历识别 | POST | /v1/ocr/medical-record | 识别OCR病历 |
| NLP医典查询 | POST | /v1/nlp/dict | 查询NLP医典 |

#### 调用示例

```bash
curl -X POST "https://真实API地址.com/medical/v1/ocr/medical-record" \
  -H "Content-Type: application/json" \
  -H "X-App-Key: your_tencent_app_key" \
  -H "X-App-Secret: your_tencent_app_secret" \
  -d '{
    "imageUrl": "https://example.com/medical-record.jpg"
  }'
```

---

### 三、医院CRM系统API

#### 对接场景
患者数据同步、预约挂号、检查报告查询等

#### 对接流程

1. **联系供应商**
   ```
   联系：医院CRM供应商（如康策）
   获取：OpenAPI接口文档、真实API地址
   签署：合作协议
   ```

2. **获取Token**
   ```
   调用：登录接口
   获取：访问Token
   使用：Token访问后续接口
   ```

3. **配置系统**
   ```yaml
   external:
     api:
       hospital-crm:
         enabled: true
         base-url: https://真实API地址.com  # 填写真实地址
         api-key: your_hospital_crm_api_key
   ```

#### 接口列表

| 接口名称 | 方法 | 路径 | 说明 |
|---------|------|------|------|
| 患者数据同步 | POST | /v1/patient/sync | 同步患者数据 |
| 预约挂号 | POST | /v1/appointment/register | 预约挂号 |
| 检查报告查询 | POST | /v1/report/query | 查询检查报告 |
| 实时排班查询 | POST | /v1/schedule/query | 查询实时排班 |

#### 调用示例

```bash
curl -X POST "https://真实API地址.com/v1/patient/sync" \
  -H "Content-Type: application/json" \
  -H "X-API-KEY: your_hospital_crm_api_key" \
  -d '{
    "idCardNumber": "110101199001011234",
    "phone": "13800138000"
  }'
```

---

## 医保相关API对接

### 一、国家医保平台API

#### 对接场景
医保电子凭证授权、刷脸支付、费用结算等

#### 对接流程

1. **前置准备**
   ```
   拥有：互联网医院主体公众号/小程序
   完成：线上问诊和交易系统开发
   ```

2. **申请流程**
   ```
   访问：国家医保服务平台官网
   提交：API接入申请
   填写：企业资质、技术方案等材料
   等待：审核通过
   获取：生产环境权限、真实API地址
   ```

3. **配置系统**
   ```yaml
   external:
     api:
       national-insurance:
         enabled: true
         base-url: https://真实API地址.gov.cn  # 填写真实地址
         app-id: your_national_insurance_app_id
         app-secret: your_national_insurance_app_secret
   ```

#### 接口列表

| 接口名称 | 方法 | 路径 | 说明 |
|---------|------|------|------|
| 医保电子凭证授权 | POST | /v1/ecode/auth | 获取医保电子凭证 |
| 刷脸支付 | POST | /v1/payment/face | 刷脸支付 |
| 费用结算 | POST | /v1/settlement | 费用结算 |
| 参保人信息查询 | POST | /v1/insured/info | 查询参保人信息 |

#### 调用示例

```bash
curl -X POST "https://真实API地址.gov.cn/v1/ecode/auth" \
  -H "Content-Type: application/json" \
  -H "X-App-Id: your_national_insurance_app_id" \
  -H "X-App-Secret: [生成签名]" \
  -d '{
    "appId": "your_national_insurance_app_id",
    "idCardNumber": "110101199001011234",
    "phone": "13800138000",
    "timestamp": 1640780800000
  }'
```

#### 签名生成

国家医保平台API需要MD5签名：

```java
public String generateSignature(Map<String, Object> params, String appSecret) {
    // 按参数名排序
    TreeMap<String, Object> sortedParams = new TreeMap<>(params);

    // 拼接参数
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, Object> entry : sortedParams.entrySet()) {
        if (sb.length() > 0) {
            sb.append("&");
        }
        sb.append(entry.getKey()).append("=").append(entry.getValue());
    }

    // 添加密钥
    sb.append("&key=").append(appSecret);

    // MD5加密
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
```

---

### 二、地方医保API

#### 对接场景
地方医保目录查询、报销政策获取等

#### 对接流程

1. **联系当地医保局**
   ```
   联系：所在地医保局或相关机构
   了解：具体申请流程
   提交：企业资质、技术文档等材料
   等待：审核通过
   获取：API访问权限、真实API地址
   ```

2. **配置系统**
   ```yaml
   external:
     api:
       local-insurance:
         enabled: true
         base-url: https://真实API地址.gov.cn  # 填写真实地址
         api-key: your_local_insurance_api_key
         region-code: 110000  # 地区代码
   ```

#### 接口列表

| 接口名称 | 方法 | 路径 | 说明 |
|---------|------|------|------|
| 地方医保目录查询 | POST | /v1/catalog/local | 查询地方医保目录 |
| 报销政策获取 | POST | /v1/policy/query | 查询报销政策 |
| 报销比例查询 | POST | /v1/reimbursement/rate | 查询报销比例 |

#### 调用示例

```bash
curl -X POST "https://真实API地址.gov.cn/v1/catalog/local" \
  -H "Content-Type: application/json" \
  -H "X-API-KEY: your_local_insurance_api_key" \
  -d '{
    "regionCode": "110000",
    "keyword": "药品"
  }'
```

---

### 三、第三方医保数据API

#### 对接场景
医保药品分类代码查询、市场需求分析等

#### 对接流程

1. **注册平台账号**
   ```
   访问：摩熵数科开放平台
   注册：开发者账号
   申请：医保药品查询API权限
   获取：真实API地址
   ```

2. **配置系统**
   ```yaml
   external:
     api:
       moneng:
         enabled: true
         base-url: https://真实API地址.com  # 填写真实地址
         api-key: your_moneng_api_key
   ```

#### 接口列表

| 接口名称 | 方法 | 路径 | 说明 |
|---------|------|------|------|
| 医保药品分类代码查询 | POST | /v1/drug/category | 查询医保药品分类 |

#### 调用示例

```bash
curl -X POST "https://真实API地址.com/v1/drug/category" \
  -H "Content-Type: application/json" \
  -H "X-API-KEY: your_moneng_api_key" \
  -d '{
    "categoryName": "抗生素"
  }'
```

---

## API连通性检查

系统提供了完整的API连通性检查功能：

### 检查所有API

```bash
GET /api/external-api/health-check
```

返回所有已启用API的连通性状态：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "moneng": {
      "status": "error",
      "message": "摩熵医药API连接失败：Connection refused",
      "url": "https://api.moneng.com"
    },
    "tencentHealth": {
      "status": "error",
      "message": "腾讯医疗健康API连接失败：Unknown host",
      "url": "https://api.tencent.com/medical"
    },
    "hospitalCrm": {
      "status": "error",
      "message": "医院CRM系统API连接失败：Connection timed out",
      "url": "https://api.hospital-crm.com"
    },
    "nationalInsurance": {
      "status": "error",
      "message": "国家医保平台API连接失败：No route to host",
      "url": "https://api.national-medical-insurance.gov.cn"
    },
    "localInsurance": {
      "status": "error",
      "message": "地方医保API连接失败：Connection refused",
      "url": "https://api.local-medical-insurance.gov.cn"
    }
  }
}
```

### 检查指定API

```bash
GET /api/external-api/health-check/{apiName}
```

支持的API名称：
- `moneng` - 摩熵医药API
- `tencentHealth` - 腾讯医疗健康API
- `hospitalCrm` - 医院CRM系统API
- `nationalInsurance` - 国家医保平台API
- `localInsurance` - 地方医保API

### 生成检查报告

```bash
GET /api/external-api/health-report
```

生成完整的API连通性检查报告，包括：
- 所有API的连通性状态
- 功能接口测试结果
- 详细的错误信息
- 配置建议

### 验证API地址

```bash
POST /api/external-api/verify-url

Content-Type: application/json

{
  "url": "https://real-api-address.com"
}
```

验证给定的URL是否可访问。

---

## 配置说明

### 启用API

在 `application.yml` 中设置对应的 `enabled` 为 `true`：

```yaml
external:
  api:
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

### 配置真实API地址

在 `application.yml` 中将 `base-url` 修改为真实API地址：

```yaml
external:
  api:
    moneng:
      enabled: true
      base-url: https://真实摩熵API地址.com  # 修改为真实地址
      api-key: your_moneng_api_key
      api-secret: your_moneng_api_secret
```

### 配置API密钥

将申请到的API密钥配置到 `application.yml` 中：

```yaml
external:
  api:
    moneng:
      enabled: true
      api-key: your_moneng_api_key  # 填写真实的API Key
      api-secret: your_moneng_api_secret  # 填写真实的API Secret
```

### API地址配置说明

| 配置项 | 说明 | 示例 |
|--------|------|------|
| `base-url` | API基础地址 | `https://api.moneng.com`（示例地址） |
| `api-key` | API访问密钥 | `your_moneng_api_key` |
| `app-key` | 腾讯/国家医保的App Key | `your_tencent_app_key` |
| `app-secret` | 腾讯/国家医保的App Secret | `your_tencent_app_secret` |
| `app-id` | 国家医保的App ID | `your_national_insurance_app_id` |
| `region-code` | 地方医保的地区代码 | `110000`（北京） |

---

## 注意事项

### ⚠️ 安全合规
- 遵守《数据安全法》《个人信息保护法》
- 禁止传输敏感信息（身份证号、手机号等）
- 使用HTTPS加密传输
- 定期更新API密钥

### ⚠️ 性能优化
- 采用异步调用降低延迟
- 使用缓存机制减少重复请求
- 控制并发请求数量
- 设置合理的超时时间

### ⚠️ 监控维护
- 实时监控API调用成功率
- 监控API响应延迟
- 及时处理API异常
- 关注API版本更新

### ⚠️ 测试建议
- 先在测试环境验证
- 使用Mock数据进行开发
- 生产环境逐步上线
- 做好回滚准备

### ⚠️ 地址说明
- 所有 `base-url` 默认为示例地址
- 首次使用必须配置真实API地址
- 未配置真实地址时，API调用会失败
- 建议使用系统提供的连通性检查功能验证

---

## 技术支持

如有对接问题，请联系：

- 摩熵医药：https://www.moneng.com
- 腾讯云医疗健康：https://cloud.tencent.com
- 国家医保服务平台：https://www.nhsa.gov.cn
- 各地医保局：联系当地医保机构

---

**对接完成后，请使用 API 连通性检查功能验证配置是否正确。**
