package com.bimabagaskhoro.submissionfundamentalakhir.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.bimabagaskhoro.submissionfundamentalakhir.data.local.AbstractDatabaseFavorite
import com.bimabagaskhoro.submissionfundamentalakhir.data.local.InterfaceFavoriteDao

class MyContentProvider : ContentProvider() {
    companion object {
        const val AUTH = "com.bimabagaskhoro.submissionfundamentalakhir"
        const val TABLE_NAME = "favorite"
        const val ID_USER = 1
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTH, TABLE_NAME, ID_USER)
        }
    }

    private lateinit var interfaceFavoriteDao: InterfaceFavoriteDao

    override fun onCreate(): Boolean {
        val let = context?.let {
            AbstractDatabaseFavorite.getDatabase(it)?.interfaceFavoriteDao()
        }
        interfaceFavoriteDao = if (let != null) let else throw KotlinNullPointerException()
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when (uriMatcher.match(uri)) {
            ID_USER -> {
                cursor = interfaceFavoriteDao.findAll()
                if (context != null) {
                    cursor.setNotificationUri(context?.contentResolver, uri)
                }
            }
            else -> {
                cursor = null
            }
        }
        return cursor
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0
}