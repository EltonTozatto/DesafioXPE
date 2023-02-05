package br.com.eltontozatto.mercadoapp.data

import br.com.eltontozatto.mercadoapp.model.entidade.Produto

class MemoryRepository(novaLista: MutableList<Produto>) {
    private val listDB: MutableList<Produto> = novaLista

    fun salvar(produto: Produto) {
        listDB.add(produto)
    }

    fun limparLista() {
        listDB.clear()
    }

    fun retornaLista() = listDB.toList()

    fun atualizarProdutoChecado(status: Boolean, position: Int) {
        listDB[position].selecionado = status
    }
}