package com.example.pexelsapp

import android.app.Application
import com.example.pexelsapp.graph.Graph

class PexelsApp: Application(){
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}