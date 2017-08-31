package com.nathanmkaya.residency.utils

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object DbReference {

  var root = FirebaseDatabase.getInstance()!!
  var students = root.getReference("students")!!
  var devices = root.getReference("devices")!!
  var clearance = root.getReference("clearance")!!
  var maintenance = root.getReference("maintenance")!!
  var news = root.getReference("news")!!
  var users = root.getReference("users")!!

  fun initialize() {

  }

  fun keepSynced(condition: Boolean) {
    students.keepSynced(condition)
    devices.keepSynced(condition)
    clearance.keepSynced(condition)
    maintenance.keepSynced(condition)
    news.keepSynced(condition)
    users.keepSynced(condition)
  }
}
