package com.example.recyclerviewexample.ProfileDatabase

class ProfileRepository (private val dao: ProfileDetailDAO) {

    val profiles = dao.getAllProfiles()

    suspend fun insert(profile: ProfileDetail) {
        dao.insertProfile(profile)
    }

    suspend fun getListProfiles(): List<ProfileDetail> {
        return dao.getListProfile()
    }

}