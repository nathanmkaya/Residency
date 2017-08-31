package com.nathanmkaya.residency.model

class Device {
  var type: String
  var make: String
  var serial: String
  var owner: String



  constructor(type: String, make: String, serial: String, owner: String) {
    this.type = type
    this.make = make
    this.serial = serial
    this.owner = owner
  }

  constructor(): this("","","","")
}
