package com.example.listacontatos.application

import android.app.Application
import com.example.listacontatos.helpers.HelperDB


/*
* Application class em Android é a classe base, que contem todos os componentes
* como activities e services
* É a primeira classe a ser inicializada
* */
class ContatoApplication : Application() {
    var helperDB: HelperDB? = null
        private set

    companion object {
        lateinit var instance: ContatoApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        helperDB = HelperDB(this)
    }
}