package com.example.mybookingapp.model

class Cinema {

    var cname: String? = null
    var cphoto: String? = null
    var clocation: String? = null
    var caddress: String? = null
    var ccontact: String? = null

    constructor():this("","", "", "", "") {}

    constructor(cname: String?, cphoto: String?, clocation: String?, caddress: String?, ccontact: String?) {
        this.cname = cname
        this.cphoto = cphoto
        this.clocation = clocation
        this.caddress = caddress
        this.ccontact = ccontact
    }
}