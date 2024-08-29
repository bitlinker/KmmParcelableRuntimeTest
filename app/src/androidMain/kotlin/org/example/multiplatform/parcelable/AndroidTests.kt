package org.example.multiplatform.parcelable

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcel
import kotlinx.parcelize.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import kotlinx.parcelize.WriteWith

@Parcelize
data class AndroidSimple(
    val normalField: Int,
) : android.os.Parcelable {
    @IgnoredOnParcel
    val ignoredField1: Int = 0
}

//@Parcelize
//data class AndroidSimpleNoIgnoredOnParcelAnnotation(
//    val normalField: Int,
//) : android.os.Parcelable {
//    val ignoredField1: Int = 0
//}

@Parcelize
data class AndroidInlineCustomParceler(val firstName: String) : Parcelable {
    private companion object : Parceler<AndroidInlineCustomParceler> {
        override fun AndroidInlineCustomParceler.write(parcel: Parcel, flags: Int) {
            parcel.writeString(firstName)
        }

        override fun create(parcel: Parcel): AndroidInlineCustomParceler {
            return AndroidInlineCustomParceler(parcel.readString() ?: "")
        }
    }
}

// Class-local parceler
@Parcelize
@TypeParceler<AndroidExternalClass, AndroidExternalClassParceler>()
data class AndroidClassLocal(val external: AndroidExternalClass) : Parcelable

// Property-local parceler
@Parcelize
data class AndroidPropertyLocal(@TypeParceler<AndroidExternalClass, AndroidExternalClassParceler>() val external: AndroidExternalClass) :
    Parcelable

// Type-local parceler
@Parcelize
data class AndroidTypeLocal(val external: @WriteWith<AndroidExternalClassParceler>() AndroidExternalClass) :
    Parcelable

// External class
data class AndroidExternalClass(val value: Int)

object AndroidExternalClassParceler : Parceler<AndroidExternalClass> {
    override fun create(parcel: Parcel) = AndroidExternalClass(parcel.readInt())

    override fun AndroidExternalClass.write(parcel: Parcel, flags: Int) {
        parcel.writeInt(value)
    }
}

private val AndroidExternalItem = AndroidExternalClass(2)
val AndroidTestItems = listOf(
    AndroidSimple(1),
    AndroidInlineCustomParceler("test name"),
    AndroidClassLocal(AndroidExternalItem),
    AndroidPropertyLocal(AndroidExternalItem),
    AndroidTypeLocal(AndroidExternalItem),
)