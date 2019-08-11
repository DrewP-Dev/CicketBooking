package com.example.mybookingapp.model

class Movie {

    var mtitle: String? = null
    var mphotoUrl: String? = null
    var mtag: String? = null
    var mdirector: String? = null
    var mcast: String? = null
    var mdescription: String? = null

    constructor():this("","", "", "", "", "") {}

    constructor(mtitle: String?, mphotoUrl: String?, mtag: String?, mdirector: String?, mcast: String?, mdescription: String?) {
        this.mtitle = mtitle
        this.mphotoUrl = mphotoUrl
        this.mtag = mtag
        this.mdirector = mdirector
        this.mcast = mcast
        this.mdescription = mdescription
    }
}