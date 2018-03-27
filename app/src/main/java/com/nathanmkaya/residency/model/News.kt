package com.nathanmkaya.residency.model

class News {
    var datePosted = System.currentTimeMillis()
    var from: String
    var title: String
    var body: String

    constructor(from: String, title: String, body: String) {
        this.from = from
        this.title = title
        this.body = body
    }
}
