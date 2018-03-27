package com.nathanmkaya.residency.model

import org.parceler.Parcel
import org.parceler.Parcel.Serialization.BEAN
import org.parceler.ParcelConstructor


@Parcel(BEAN)
class Student {
    var name: String
    var regNo: String
    var hostel: String
    var wing: String
    var room: String
    var cleared = false
    var img: String? = null
    var comment: String? = null

    constructor() : this("", "", "", "", "")

    @ParcelConstructor
    constructor(name: String, regNo: String, hostel: String, wing: String, room: String) {
        this.name = name
        this.regNo = regNo
        this.hostel = hostel
        this.wing = wing
        this.room = room
    }
}
