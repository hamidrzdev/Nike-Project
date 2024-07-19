package com.hamiddev.nikeshop.data.repo.banner

import com.hamiddev.nikeshop.common.safeApiCall
import com.hamiddev.nikeshop.data.model.Banner
import com.hamiddev.nikeshop.data.model.Resource
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(private val bannerDataSource: BannerDataSource) :
    BannerRepository {
    override suspend fun banners(): Resource<List<Banner>> = bannerDataSource.banners()
}


