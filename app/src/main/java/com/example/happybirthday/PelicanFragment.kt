package com.example.happybirthday

class PelicanFragment: AnniversaryFragment() {
    override fun getDefaultBabyIcon(): Int {
        return R.drawable.default_place_holder_blue
    }

    override fun getDefaultCameraIcon(): Int {
        return R.drawable.camera_icon_blue
    }

    override fun getBackgroundDrawable(): Int {
        return R.drawable.i_os_bg_pelican_2
    }

    override fun getBackgroundColor(): Int {
        return R.color.pelican_bg
    }
}