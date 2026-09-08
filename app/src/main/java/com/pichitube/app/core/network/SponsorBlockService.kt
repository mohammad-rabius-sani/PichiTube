package com.pichitube.app.core.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SponsorBlockService @Inject constructor(private val client: OkHttpClient) {

    companion object {
        private const val API = "https://sponsor.ajay.app"
        private val SKIP_CATEGORIES = listOf("sponsor", "intro", "outro", "selfpromo", "interaction")
    }

    suspend fun getSegments(videoId: String): List<SponsorSegment> =
        kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
            try {
                val categories = SKIP_CATEGORIES.joinToString(",", "[\"", "\"]") { "\"$it\"" }
                    .let { "[\"${SKIP_CATEGORIES.joinToString("\",\"")}\"]" }
                val url = "$API/api/skipSegments?videoID=$videoId&categories=$categories"
                val response = client.newCall(okhttp3.Request.Builder().url(url).build()).execute()
                if (!response.isSuccessful) return@withContext emptyList()
                val body = response.body?.string() ?: return@withContext emptyList()
                // Simple JSON parse without Gson — extract segment arrays
                parseSponsorSegments(body)
            } catch (e: Exception) {
                emptyList()
            }
        }

    private fun parseSponsorSegments(json: String): List<SponsorSegment> {
        val segments = mutableListOf<SponsorSegment>()
        try {
            // Look for "segment":[[start,end]] patterns
            val segmentRegex = Regex(""""segment":\[([^\]]+)\]""")
            val categoryRegex = Regex(""""category":"([^"]+)"""")
            val matches = segmentRegex.findAll(json)
            val categories = categoryRegex.findAll(json).map { it.groupValues[1] }.toList()
            matches.forEachIndexed { idx, match ->
                val nums = match.groupValues[1].split(",")
                if (nums.size >= 2) {
                    val start = nums[0].trim().toDoubleOrNull() ?: return@forEachIndexed
                    val end = nums[1].trim().toDoubleOrNull() ?: return@forEachIndexed
                    segments += SponsorSegment(
                        startMs = (start * 1000).toLong(),
                        endMs = (end * 1000).toLong(),
                        category = categories.getOrElse(idx) { "sponsor" }
                    )
                }
            }
        } catch (e: Exception) { /* ignore parse errors */ }
        return segments
    }
}

data class SponsorSegment(
    val startMs: Long,
    val endMs: Long,
    val category: String,
)
