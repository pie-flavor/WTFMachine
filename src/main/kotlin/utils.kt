package wtfviewer

import javafx.collections.ObservableList
import javafx.scene.control.MultipleSelectionModel
import okhttp3.HttpUrl
import okhttp3.Response
import tornadofx.*

class Unselectable<T> : MultipleSelectionModel<T>() {
    override fun clearSelection(index: Int) {

    }

    override fun clearSelection() {
    }

    override fun selectLast() {
    }

    override fun isSelected(index: Int): Boolean = false

    override fun getSelectedIndices(): ObservableList<Int> = observableListOf()

    override fun selectAll() {
    }

    override fun getSelectedItems(): ObservableList<T> = observableListOf()

    override fun select(index: Int) {
    }

    override fun select(obj: T) {
    }

    override fun isEmpty(): Boolean = true

    override fun selectNext() {
    }

    override fun selectPrevious() {
    }

    override fun selectIndices(index: Int, vararg indices: Int) {
    }

    override fun selectFirst() {
    }

    override fun clearAndSelect(index: Int) {
    }
}

operator fun HttpUrl.plus(link: String): HttpUrl = resolve(link)!!

fun Response.assumeSuccess(): Response {
    if (!isSuccessful) {
        throw Exception("HTTP error ${code()} ${message()}")
    }
    return this
}

fun Response.assumeSuccess(message: String): Response {
    if (!isSuccessful) {
        throw Exception("HTTP error ${code()} ${message()}\n$message")
    }
    return this
}
