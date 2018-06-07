package ua.gvv.koshshot.data.entities

data class Point (var x: Float?, var y: Float?) {
    fun isValid(): Boolean {
        return x != null && y != null
    }

    val intX: Int get() = x?.toInt() ?: 0
    val intY: Int get() = y?.toInt() ?: 0
}