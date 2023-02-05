package br.com.eltontozatto.mercadoapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.eltontozatto.mercadoapp.data.MemoryRepository
import br.com.eltontozatto.mercadoapp.model.entidade.Produto

class MainViewModel: ViewModel() {
    private var memoryRepository: MemoryRepository = MemoryRepository(mutableListOf())
    private val _listaDeProdutos = MutableLiveData<List<Produto>>()
    val listaDeProdutos: LiveData<List<Produto>> = _listaDeProdutos

    fun inicializarDados() {
        _listaDeProdutos.value = memoryRepository.retornaLista()
    }

    fun salvarProdutos(produto: Produto) {
        Log.i("XPEInfo", "Produto recebido: ${produto}")
        memoryRepository.salvar(produto)
        atualizarListaDeProdutos()
    }

    fun limparListaDeProdutos() {
        memoryRepository.limparLista()
        atualizarListaDeProdutos()
    }

    private fun atualizarListaDeProdutos() {
        _listaDeProdutos.value = memoryRepository.retornaLista()
    }

    fun atualizarProdutoChecado(status: Boolean, position: Int) {
        memoryRepository.atualizarProdutoChecado(status, position)
    }
}