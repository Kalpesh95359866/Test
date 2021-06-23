package com.example.practicalapp.model

data class PopulerModel(var Title:String,var Description:String,var Time:String,var Days: String) {


    fun PopulerModel() {}
    fun PopulerModel(Title:String, Description:String,Time:String, Days: String) {
        this.Title = Title
        this.Description = Description
        this.Time = Time
        this.Days = Days

    }

    fun Title(Title: String) {
        this.Title = Title
    }

    fun getTitle(Title: String): String? {
        return Title
    }
    fun Description(Description: String) {
        this.Description = Description
    }

    fun getDescription(Description: String): String? {
        return Description
    }

    fun Time(Time: String) {
        this.Time = Time
    }

    fun getTime(Time: String): String? {
        return Time
    }


    fun Days(Days: String) {
        this.Days = Days
    }

    fun getDays(Days: String): String? {
        return Days
    }


}