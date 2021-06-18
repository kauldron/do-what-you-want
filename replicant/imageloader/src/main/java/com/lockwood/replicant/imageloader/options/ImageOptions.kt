package com.lockwood.replicant.imageloader.options

class ImageOptions {

    @JvmField
    var data: Any? = null

    @JvmField
    var size: Size = DpSize.UNDEFINED

    @JvmField
    var cropType: CropType = CropType.DEFAULT
}