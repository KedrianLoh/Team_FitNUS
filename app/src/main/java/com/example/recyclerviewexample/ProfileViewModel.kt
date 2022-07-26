package com.example.recyclerviewexample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewexample.ProfileDatabase.ProfileDatabase
import com.example.recyclerviewexample.ProfileDatabase.ProfileDetail
import com.example.recyclerviewexample.ProfileDatabase.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application): AndroidViewModel(application) {
    val allProfileDetail: LiveData<List<ProfileDetail>>
    val repository: ProfileRepository

    init {
        val dao = ProfileDatabase.getInstance(application).profileDetailDAO()
        repository = ProfileRepository(dao)
        allProfileDetail = repository.profiles
    }

    fun insertProfile(profile: ProfileDetail) = viewModelScope.launch (Dispatchers.IO) {
        repository.insert(profile)
    }

    suspend fun getListProfile(): List<ProfileDetail> {
        return repository.getListProfiles()
    }

}