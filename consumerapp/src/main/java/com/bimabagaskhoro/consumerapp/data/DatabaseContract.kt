package com.bimabagaskhoro.consumerapp.data

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTH = "com.bimabagaskhoro.submissionfundamentalakhir"
    const val SCHEME = "content"

    internal class FavoriteUserColumn : BaseColumns {
        companion object {
            private const val TABLE_NAME = "favorite"
            const val ID = "id"
            const val USERNAME = "login"
            const val AVATAR_URL = "avatar_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTH)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}