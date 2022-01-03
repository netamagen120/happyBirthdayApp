package com.example.happybirthday

class ElephantFragment: AnniversaryFragment() {
    override fun getDefaultBabyIcon(): Int {
        return R.drawable.default_place_holder_yellow
    }

    override fun getDefaultCameraIcon(): Int {
        return R.drawable.camera_icon_yellow
    }

    override fun getBackgroundDrawable(): Int {
        return R.drawable.i_os_bg_elephant
    }

    override fun getBackgroundColor(): Int {
        return R.color.elephant_bg
    }

    override fun getCircleBgDrawable(): Int {
        return R.drawable.yellow_circle
    }
}