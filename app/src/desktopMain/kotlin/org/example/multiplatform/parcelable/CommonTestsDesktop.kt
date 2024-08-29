package org.example.multiplatform.parcelable

import kotlinx.parcelize.Parceler

actual object CommonExternalClassParceler : Parceler<CommonExternalClass> {
    // No impl on desktop
}