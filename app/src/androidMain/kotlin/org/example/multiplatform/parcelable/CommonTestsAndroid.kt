package org.example.multiplatform.parcelable

import kotlinx.parcelize.Parcel
import kotlinx.parcelize.Parceler

actual object CommonExternalClassParceler : Parceler<CommonExternalClass>
{
    override fun create(parcel: Parcel) = CommonExternalClass(parcel.readInt())

    override fun CommonExternalClass.write(parcel: Parcel, flags: Int) {
        parcel.writeInt(value)
    }
}