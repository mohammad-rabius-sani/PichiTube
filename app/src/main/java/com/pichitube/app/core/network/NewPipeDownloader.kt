package com.pichitube.app.core.network

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.schabi.newpipe.extractor.downloader.Downloader
import org.schabi.newpipe.extractor.downloader.Request as NpRequest
import org.schabi.newpipe.extractor.downloader.Response as NpResponse
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewPipeDownloader @Inject constructor(
    private val client: OkHttpClient
) : Downloader() {

    companion object {
        private const val USER_AGENT =
            "Mozilla/5.0 (Android 14; Mobile; rv:109.0) Gecko/109.0 Firefox/120.0"
    }

    override fun execute(request: NpRequest): NpResponse {
        val httpMethod = request.httpMethod()
        val url = request.url()
        val headers = request.headers()
        val body = request.dataToSend()

        val requestBuilder = Request.Builder()
            .url(url)
            .header("User-Agent", USER_AGENT)
            .header("Accept-Language", "en-US,en;q=0.9")

        headers.forEach { (key, values) ->
            if (values.isNotEmpty()) {
                // Use header() to overwrite defaults and avoid duplicate headers
                requestBuilder.header(key, values.joinToString("; "))
            }
        }

        when (httpMethod) {
            "POST" -> {
                val requestBody = if (body != null) {
                    body.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                } else {
                    ByteArray(0).toRequestBody(null)
                }
                requestBuilder.post(requestBody)
            }
            "DELETE" -> requestBuilder.delete()
            else -> requestBuilder.get()
        }

        val response: Response = client.newCall(requestBuilder.build()).execute()

        if (response.code == 429) {
            throw ReCaptchaException("Rate limited (429)", url)
        }

        val responseHeaders = mutableMapOf<String, List<String>>()
        response.headers.names().forEach { name ->
            responseHeaders[name] = response.headers(name)
        }

        val responseBody = try { response.body?.string() } catch (e: Exception) {
            Timber.w(e, "Failed to read response body")
            null
        }

        return NpResponse(
            response.code,
            response.message,
            responseHeaders,
            responseBody,
            response.request.url.toString()
        )
    }
}
