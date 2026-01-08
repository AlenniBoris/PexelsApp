package com.example.pexapp.presentation.uikit.model

data class ClickableElement(
    val text: String,
    val onClick: () -> Unit
) {
    override fun equals(other: Any?): Boolean {
        (other as? ClickableElement)?.let {
            return other.text == this.text
        }
        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}