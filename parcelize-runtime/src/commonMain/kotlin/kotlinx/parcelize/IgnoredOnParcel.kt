package kotlinx.parcelize

/**
 * The property annotated with [IgnoredOnParcel] will not be stored into parcel.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class IgnoredOnParcel