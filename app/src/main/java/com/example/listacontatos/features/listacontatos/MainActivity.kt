package com.example.listacontatos.features.listacontatos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listacontatos.R
import com.example.listacontatos.application.ContatoApplication
import com.example.listacontatos.base.BaseActivity
import com.example.listacontatos.features.contato.ContatoActivity
import com.example.listacontatos.features.listacontatos.adapter.ContatoAdapter
import com.example.listacontatos.features.listacontatos.model.ContatosVO
import kotlinx.android.synthetic.main.activity_contato.toolBar
import com.example.listacontatos.singleton.ContatoSingleton
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : BaseActivity() {

    private var adapter:ContatoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolBar(toolBar, "Lista de contatos", false)
        setupListView()
        setupOnClicks()
    }

    private fun setupOnClicks(){
        /*fab ??? */
        fab.setOnClickListener { onClickAdd() }
        ivBuscar.setOnClickListener{ onClickBuscar() }
    }

    private fun setupListView(){
        recycleView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume(){
        super.onResume()
        onClickBuscar()
    }

    private fun onClickAdd(){
        val intent = Intent(this, ContatoActivity::class.java)
        startActivity(intent)
    }

    private fun onClickItemRecyclerView(index: Int){
        val intent = Intent(this, ContatoActivity::class.java)
        intent.putExtra("index", index)
        startActivity(intent)
    }

    private fun onClickBuscar(){
        val busca = etBuscar.text.toString()
        progress.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(1500)
            var listaFiltrada: List<ContatosVO> = mutableListOf()
            try{
                listaFiltrada = ContatoApplication.instance.helperDB?.buscaContatos(busca) ?: mutableListOf()
            }catch (ex: Exception){
                ex.printStackTrace()
            }
            runOnUiThread {
                adapter = ContatoAdapter(this,listaFiltrada) { onClickItemRecyclerView(it) }
                recycleView.adapter = adapter
                progress.visibility = View.GONE
                Toast.makeText(this, "Buscando por $busca", Toast.LENGTH_SHORT).show()
            }
        }).start()
    }
}