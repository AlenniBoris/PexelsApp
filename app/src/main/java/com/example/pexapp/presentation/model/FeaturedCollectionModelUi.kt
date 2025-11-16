package com.example.pexapp.presentation.model

import com.example.pexapp.domain.model.CollectionsModelDomain

data class FeaturedCollectionModelUi(
    private val domainModel: CollectionsModelDomain? = null
) {
    val text: String = domainModel?.title ?: ""
    val id: String = domainModel?.id ?: ""

    override fun equals(other: Any?): Boolean {
        return (other as? FeaturedCollectionModelUi)?.let {
            (this.text == it.text) && (this.id == it.id)
        } ?: false
    }

    override fun hashCode(): Int {
        return text.hashCode()
    }
}

fun CollectionsModelDomain.toUiModel(): FeaturedCollectionModelUi =
    FeaturedCollectionModelUi(
        domainModel = this
    )