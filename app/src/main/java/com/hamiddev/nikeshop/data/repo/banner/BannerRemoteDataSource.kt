package com.hamiddev.nikeshop.data.repo.banner

import com.hamiddev.nikeshop.common.safeApiCall
import com.hamiddev.nikeshop.data.model.Banner
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.service.ApiService
import javax.inject.Inject

class BannerRemoteDataSource @Inject constructor(private val apiService: ApiService) : BannerDataSource {
    override suspend fun banners(): Resource<List<Banner>> = safeApiCall {
        apiService.banners()
    }
}