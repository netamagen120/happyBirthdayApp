package com.example.happybirthday

class FoxFragment: AnniversaryFragment() {
    override fun getDefaultBabyIcon(): Int {
        return R.drawable.default_place_holder_green
    }

    override fun getDefaultCameraIcon(): Int {
        return R.drawable.camera_icon_green
    }

    override fun getBackgroundDrawable(): Int {
        return R.drawable.i_os_bg_fox
    }

    override fun getBackgroundColor(): Int {
        return R.color.fox_bg
    }

    override fun getCircleBgDrawable(): Int {
        return R.drawable.green_circle
    }
}