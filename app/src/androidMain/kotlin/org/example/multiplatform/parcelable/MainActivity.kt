@file:Suppress("DEPRECATION")

package org.example.multiplatform.parcelable

import android.app.Activity
import android.os.Bundle
import android.os.Parcel
import android.util.Log
import kotlinx.parcelize.Parcelable
import kotlinx.parcelize.parcelableCreator

class MainActivity : Activity() {
    private val allTestItems = AndroidTestItems + CommonTestItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recreateAndMarshallParcelTest(AndroidSimple(123))
        recreateAndMarshallParcelTest(CommonSimple(234))

        if (savedInstanceState != null) {
            allTestItems.forEach { item ->
                val bundledItem = savedInstanceState.readTestParcelable(item.javaClass)
                Log.d("TEST", bundledItem.toString())
                if (item != bundledItem) throw IllegalStateException("Items differs: $bundledItem $item")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        allTestItems.forEach { item ->
            outState.writeTestParcelable(item, item.javaClass)
        }
        super.onSaveInstanceState(outState)
    }

    private inline fun <reified T : Parcelable> recreateAndMarshallParcelTest(value: T) {
        val parcel = Parcel.obtain()
        value.writeToParcel(parcel, 0)
        val data = parcel.marshall()
        parcel.recycle()

        val newParcel = Parcel.obtain()
        newParcel.unmarshall(data, 0, data.size)
        newParcel.setDataPosition(0)
        val creator = parcelableCreator<T>()
        val newValue = creator.createFromParcel(newParcel)
        newParcel.recycle()

        if (value != newValue) {
            throw IllegalStateException("Parcel creator is broken for $value")
        }
    }

    private fun Bundle.readTestParcelable(cls: Class<*>): Parcelable {
        return getParcelable(cls.name)!!
    }

    private fun Bundle.writeTestParcelable(value: Parcelable, cls: Class<*>) {
        putParcelable(cls.name, value)
    }
}

