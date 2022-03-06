package com.normachellquake.myvoice

import android.media.AudioRecord
import android.media.Image
import java.io.Serializable

data class Item(
    val name: String
//    val image: Image?,
//    val recording: AudioRecord?
) : Serializable