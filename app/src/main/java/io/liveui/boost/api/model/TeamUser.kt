package io.liveui.boost.api.model

/**
 * {
    "id": "630C97E6-AC56-4213-882B-3BEBAE50BF6D",
    "lastname": "Admin",
    "registered": 541114427,
    "su": true,
    "email": "admin@liveui.io",
    "firstname": "Super",
    "disabled": false
   }
 */
data class TeamUser(val id: String,
                    val firstname: String,
                    val lastname: String,
                    val registered: String,
                    val email: String,
                    val su: Boolean,
                    val disabled: Boolean) {

    fun getFullName(): String {
        return "$firstname $lastname"
    }
}