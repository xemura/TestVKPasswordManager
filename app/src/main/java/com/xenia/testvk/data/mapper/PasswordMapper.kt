package com.xenia.testvk.data.mapper

import com.xenia.testvk.data.entities.ItemEntity
import com.xenia.testvk.domain.ItemModel


class PasswordMapper() {
    fun mapListEntityToListModel(items: List<ItemEntity>): List<ItemModel> {
        val list = mutableListOf<ItemModel>()

        items.forEach {
            list.add(ItemModel(
                it.id,
                it.imageUrl,
                it.websiteUrl,
                it.login,
                it.password
            ))
        }

        return list
    }

    fun mapListModelToListEntity(items: List<ItemModel>): List<ItemEntity> {
        val list = mutableListOf<ItemEntity>()

        items.forEach {
            list.add(ItemEntity(
                it.id,
                it.imageUrl,
                it.websiteUrl,
                it.login,
                it.password
            ))
        }

        return list
    }

    fun mapModelToEntity(items: ItemModel): ItemEntity {
        return ItemEntity(
            items.id,
            items.imageUrl,
            items.websiteUrl,
            items.login,
            items.password
        )
    }

    fun mapEntityToModel(items: ItemEntity): ItemModel {
        return ItemModel(
            items.id,
            items.imageUrl,
            items.websiteUrl,
            items.login,
            items.password
        )
    }
}
