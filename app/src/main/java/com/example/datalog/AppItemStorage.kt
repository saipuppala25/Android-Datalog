package com.example.datalog


import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.ByteArrayOutputStream

@Entity(tableName = "app_table")
data class AppItemStorage(@PrimaryKey @ColumnInfo(name ="id") var id: Int,
                     @ColumnInfo(name ="app_name") var appName: String,
                     @ColumnInfo(name ="app_package_name") var packageName: String,
                     @ColumnInfo(name="liked") var liked: Boolean = false

)
{
    fun Drawable.toByteArray(): ByteArray {
        val bitmap = (this as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}