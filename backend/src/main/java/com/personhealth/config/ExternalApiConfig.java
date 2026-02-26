package com.personhealth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 外部API配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "external.api")
public class ExternalApiConfig {

    /**
     * 摩熵医药API配置
     */
    private MonengApi moneng = new MonengApi();

    /**
     * 腾讯医疗健康API配置
     */
    private TencentHealth tencentHealth = new TencentHealth();

    /**
     * 医院CRM系统API配置
     */
    private HospitalCrm hospitalCrm = new HospitalCrm();

    /**
     * 国家医保平台API配置
     */
    private NationalInsurance nationalInsurance = new NationalInsurance();

    /**
     * 地方医保API配置
     */
    private LocalInsurance localInsurance = new LocalInsurance();

    /**
     * 摩熵医药API配置
     */
    @Data
    public static class MonengApi {
        private Boolean enabled = false;
        private String baseUrl = "https://api.moneng.com"; // 示例地址，实际使用时请填写真实API地址
        private String apiKey;
        private String apiSecret;
        private Integer timeout = 30000;

        /**
         * 医保药品目录查询API
         */
        public String getDrugCatalogUrl() {
            return baseUrl + "/v1/drug/catalog";
        }

        /**
         * 药品分类代码查询API
         */
        public String getDrugCategoryUrl() {
            return baseUrl + "/v1/drug/category";
        }
    }

    /**
     * 腾讯医疗健康API配置
     */
    @Data
    public static class TencentHealth {
        private Boolean enabled = false;
        private String baseUrl = "https://api.tencent.com/medical"; // 示例地址，实际使用时请填写真实API地址
        private String appKey;
        private String appSecret;
        private Integer timeout = 30000;

        /**
         * 医学术语查询API
         */
        public String getMedicalTermUrl() {
            return baseUrl + "/v1/term/query";
        }

        /**
         * ICD编码查询API
         */
        public String getIcdCodeUrl() {
            return baseUrl + "/v1/icd/query";
        }

        /**
         * OCR病历识别API
         */
        public String getOcrMedicalRecordUrl() {
            return baseUrl + "/v1/ocr/medical-record";
        }

        /**
         * NLP医典查询API
         */
        public String getMedicalDictUrl() {
            return baseUrl + "/v1/nlp/dict";
        }
    }

    /**
     * 医院CRM系统API配置
     */
    @Data
    public static class HospitalCrm {
        private Boolean enabled = false;
        private String baseUrl = "https://api.hospital-crm.com"; // 示例地址，实际使用时请填写真实API地址
        private String apiKey;
        private Integer timeout = 30000;

        /**
         * 患者数据同步API
         */
        public String getPatientSyncUrl() {
            return baseUrl + "/v1/patient/sync";
        }

        /**
         * 预约挂号API
         */
        public String getAppointmentUrl() {
            return baseUrl + "/v1/appointment/register";
        }

        /**
         * 检查报告查询API
         */
        public String getReportQueryUrl() {
            return baseUrl + "/v1/report/query";
        }

        /**
         * 实时排班查询API
         */
        public String getScheduleQueryUrl() {
            return baseUrl + "/v1/schedule/query";
        }
    }

    /**
     * 国家医保平台API配置
     */
    @Data
    public static class NationalInsurance {
        private Boolean enabled = false;
        private String baseUrl = "https://api.national-medical-insurance.gov.cn"; // 示例地址，实际使用时请填写真实API地址
        private String appId;
        private String appSecret;
        private Integer timeout = 30000;

        /**
         * 医保电子凭证授权API
         */
        public String getEcodeAuthUrl() {
            return baseUrl + "/v1/ecode/auth";
        }

        /**
         * 刷脸支付API
         */
        public String getFacePaymentUrl() {
            return baseUrl + "/v1/payment/face";
        }

        /**
         * 费用结算API
         */
        public String getSettlementUrl() {
            return baseUrl + "/v1/settlement";
        }

        /**
         * 参保人信息查询API
         */
        public String getInsuredInfoUrl() {
            return baseUrl + "/v1/insured/info";
        }
    }

    /**
     * 地方医保API配置
     */
    @Data
    public static class LocalInsurance {
        private Boolean enabled = false;
        private String baseUrl = "https://api.local-medical-insurance.gov.cn"; // 示例地址，实际使用时请填写真实API地址
        private String apiKey;
        private String regionCode;
        private Integer timeout = 30000;

        /**
         * 地方医保目录查询API
         */
        public String getLocalCatalogUrl() {
            return baseUrl + "/v1/catalog/local";
        }

        /**
         * 报销政策获取API
         */
        public String getPolicyUrl() {
            return baseUrl + "/v1/policy/query";
        }

        /**
         * 报销比例查询API
         */
        public String getReimbursementRateUrl() {
            return baseUrl + "/v1/reimbursement/rate";
        }
    }
}
