package com.hamiddev.nikeshop.data.repo.banner

import com.hamiddev.nikeshop.data.model.Banner
import com.hamiddev.nikeshop.data.model.Resource

interface BannerRepository {
    suspend fun banners() : Resource<List<Banner>>
}