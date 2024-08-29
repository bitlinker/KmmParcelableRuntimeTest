package org.example.multiplatform.parcelable

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import kotlinx.parcelize.WriteWith

@Parcelize
data class CommonSimple(
    val normalField: Int,
) : Parcelable {
    @IgnoredOnParcel
    val ignoredField1: Int = 0
}

//@Parcelize
//data class CommonSimpleNoIgnoredOnParcelAnnotation(
//    val normalField: Int,
//) : Parcelable {
//    val ignoredField1: Int = 0
//}

// Class-local parceler
@Parcelize
@TypeParceler<CommonExternalClass, CommonExternalClassParceler>()
data class CommonClassLocal(val external: CommonExternalClass) : Parcelable

// Property-local parceler
@Parcelize
data class CommmonPropertyLocal(@TypeParceler<CommonExternalClass, CommonExternalClassParceler>() val external: CommonExternalClass) :
    Parcelable

// Type-local parceler
@Parcelize
data class CommonTypeLocal(val external: @WriteWith<CommonExternalClassParceler>() CommonExternalClass) :
    Parcelable

// External class
data class CommonExternalClass(val value: Int)

expect object CommonExternalClassParceler : Parceler<CommonExternalClass>

private val CommonExternalItem = CommonExternalClass(100)
val CommonTestItems = listOf(
    CommonSimple(1),
    CommonClassLocal(CommonExternalItem),
    CommmonPropertyLocal(CommonExternalItem),
    CommonTypeLocal(CommonExternalItem),
)