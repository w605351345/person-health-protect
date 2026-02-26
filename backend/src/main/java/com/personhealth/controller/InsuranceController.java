package com.personhealth.controller;

import com.personhealth.entity.InsuranceProduct;
import com.personhealth.entity.UserPolicy;
import com.personhealth.service.InsuranceService;
import com.personhealth.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 保险服务控制器
 */
@Tag(name = "保险服务", description = "保险产品查询、智能推荐、保单管理等接口")
@RestController
@RequestMapping("/insurance")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    /**
     * 获取保险产品列表
     */
    @Operation(summary = "获取保险产品", description = "查询符合用户年龄段的保险产品")
    @GetMapping("/products")
    public Result<List<InsuranceProduct>> getInsuranceProducts() {
        List<InsuranceProduct> products = insuranceService.getInsuranceProducts();
        return Result.success(products);
    }

    /**
     * 智能推荐保险
     */
    @Operation(summary = "智能推荐", description = "根据用户健康记录和病史智能推荐保险产品")
    @GetMapping("/recommend")
    public Result<List<InsuranceProduct>> recommendInsurance() {
        List<InsuranceProduct> products = insuranceService.recommendInsurance();
        return Result.success(products);
    }

    /**
     * 过滤无法投保的保险
     */
    @Operation(summary = "过滤保险", description = "基于医疗记录和病史过滤无法投保的保险产品")
    @GetMapping("/filter")
    public Result<List<InsuranceProduct>> filterInsurance() {
        List<InsuranceProduct> products = insuranceService.filterInsurance();
        return Result.success(products);
    }

    /**
     * 获取保险详情
     */
    @Operation(summary = "保险详情", description = "查看保险产品的详细信息")
    @GetMapping("/detail/{id}")
    public Result<InsuranceProduct> getInsuranceDetail(@PathVariable Long id) {
        InsuranceProduct product = insuranceService.getInsuranceDetail(id);
        return Result.success(product);
    }

    /**
     * 获取我的保单
     */
    @Operation(summary = "我的保单", description = "查询用户当前在投的保险")
    @GetMapping("/my-policies")
    public Result<List<UserPolicy>> getMyPolicies() {
        List<UserPolicy> policies = insuranceService.getMyPolicies();
        return Result.success(policies);
    }

    /**
     * 跳转到保险购买页面
     */
    @Operation(summary = "跳转购买", description = "跳转到保险公司的购买页面")
    @GetMapping("/purchase/{id}")
    public Result<String> purchaseInsurance(@PathVariable Long id) {
        String purchaseUrl = insuranceService.getPurchaseUrl(id);
        return Result.success(purchaseUrl);
    }
}
