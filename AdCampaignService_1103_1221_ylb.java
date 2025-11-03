// 代码生成时间: 2025-11-03 12:21:40
package com.example.adcampaign;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
# 增强安全性
 * Service class for managing ad campaigns.
 */
@ApplicationScoped
public class AdCampaignService {

    // Injecting the ad campaign repository
    @Inject
    private AdCampaignRepository adCampaignRepository;

    // A list to store ad campaigns, in-memory for simplicity
    private List<AdCampaign> campaigns = new CopyOnWriteArrayList<>();

    /**
     * Adds a new ad campaign to the system.
     *
# 优化算法效率
     * @param adCampaign The ad campaign to add.
     * @return The added ad campaign with an ID.
     */
    public AdCampaign addAdCampaign(@Valid AdCampaign adCampaign) {
        // Check for existing campaign with the same name
        for (AdCampaign campaign : campaigns) {
            if (campaign.getName().equals(adCampaign.getName())) {
                throw new IllegalArgumentException("Ad campaign with name "" + adCampaign.getName() +"" already exists.");
            }
# NOTE: 重要实现细节
        }

        // Add the campaign to the repository
        adCampaignRepository.save(adCampaign);
        campaigns.add(adCampaign);
# TODO: 优化性能

        return adCampaign;
    }

    /**
     * Retrieves an ad campaign by its ID.
     *
     * @param id The ID of the ad campaign to retrieve.
     * @return The ad campaign with the given ID or null if not found.
     */
    public AdCampaign getAdCampaignById(Long id) {
        return adCampaignRepository.findById(id).orElse(null);
# 改进用户体验
    }

    /**
     * Updates an existing ad campaign.
     *
     * @param id The ID of the ad campaign to update.
# 增强安全性
     * @param adCampaign The updated ad campaign details.
     * @return The updated ad campaign.
# 增强安全性
     */
    public AdCampaign updateAdCampaign(Long id, @Valid AdCampaign adCampaign) {
# 扩展功能模块
        AdCampaign existingCampaign = getAdCampaignById(id);
        if (existingCampaign == null) {
            throw new IllegalArgumentException("Ad campaign with ID "" + id +"" does not exist.");
# 扩展功能模块
        }

        existingCampaign.setName(adCampaign.getName());
        existingCampaign.setBudget(adCampaign.getBudget());
        existingCampaign.setTargetAudience(adCampaign.getTargetAudience());

        adCampaignRepository.update(existingCampaign);
        return existingCampaign;
    }

    /**
# 改进用户体验
     * Deletes an ad campaign by its ID.
     *
     * @param id The ID of the ad campaign to delete.
     */
    public void deleteAdCampaign(Long id) {
        AdCampaign campaign = getAdCampaignById(id);
        if (campaign != null) {
# 添加错误处理
            adCampaignRepository.delete(campaign);
# 改进用户体验
            campaigns.remove(campaign);
        } else {
            throw new IllegalArgumentException("Ad campaign with ID "" + id +"" does not exist.");
        }
    }
}
