package io.github.feliperce.cryptonews.feature.nav

import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import com.eygraber.uri.UriCodec
import io.github.feliperce.cryptonews.feature.news.mapper.Article
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val ArticleNavType = object : NavType<Article>(
    isNullableAllowed = false,
) {
    override fun get(bundle: Bundle, key: String): Article? {
        return Json.decodeFromString(bundle.getString(key) ?: return null)
    }

    override fun parseValue(value: String): Article {
        return Json.decodeFromString(UriCodec.decode(value))
    }

    override fun put(bundle: Bundle, key: String, value: Article) {
        bundle.putString(key, Json.encodeToString(value))
    }

    override fun serializeAsValue(value: Article): String {
        return UriCodec.encode(Json.encodeToString(value))
    }
}