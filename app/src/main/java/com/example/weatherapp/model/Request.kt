package com.example.weatherapp.model

data class Request(
    val abbrv: Int,
    val add_request: Int,
    val format: String,
    val key: String,
    val language: String,
    val limit: Int,
    val min_confidence: Int,
    val no_annotations: Int,
    val no_dedupe: Int,
    val no_record: Int,
    val pretty: Int,
    val proximity: String,
    val query: String,
    val roadinfo: Int,
    val version: String
)