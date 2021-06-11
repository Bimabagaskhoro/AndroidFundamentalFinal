package com.bimabagaskhoro.consumerapp.data

import android.database.Cursor

object MappingHelper {
    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<EntityUser> {
        val list = ArrayList<EntityUser>()

        cursor?.apply {
            while (moveToNext()) {
                val username =
                    cursor.getString(getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.USERNAME))
                val id =
                    cursor.getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.ID))
                val avatar_url =
                    cursor.getString(getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.AVATAR_URL))
                list.add(
                    EntityUser(
                        username,
                        id,
                        avatar_url
                    )
                )
            }
        }
        return list
    }
}