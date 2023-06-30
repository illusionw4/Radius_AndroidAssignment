package com.asthana.radius.Models

class ApiModels {
    // ApiModels.kt

    data class Facility(
        val facility_id: String,
        val name: String,
        val options: List<Option>
    )

    data class Option(
        val name: String,
        val icon: String,
        val id: String
    )

    data class Exclusion(
        val facility_id: String,
        val options_id: String
    )

    data class ApiResponse(
        val facilities: List<Facility>,
        val exclusions: List<List<Exclusion>>
    )
}