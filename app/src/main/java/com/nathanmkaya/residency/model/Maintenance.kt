package com.nathanmkaya.residency.model

class Maintenance {
    var hostel: String
    var wing: String
    var issue: String
    var datePosted = System.currentTimeMillis()
    var dateFixed: Long = 0
    var fixed = false

    constructor(hostel: String, wing: String, issue: String) {
        this.hostel = hostel
        this.wing = wing
        this.issue = issue
        this.datePosted = datePosted
    }
}
