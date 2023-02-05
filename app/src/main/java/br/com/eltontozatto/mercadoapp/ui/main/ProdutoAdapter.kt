package br.com.eltontozatto.mercadoapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.eltontozatto.mercadoapp.databinding.ListItemProdutoBinding
import br.com.eltontozatto.mercadoapp.model.entidade.Produto

class ProdutoAdapter(
    private val listaDeProdutos: List<Produto>,
    private val itemCheckCallback: ((Boolean, Int) -> Unit)?
): Adapter<ProdutoAdapter.ViewHolder>() {
    private lateinit var binding: ListItemProdutoBinding

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: Produto) {
            binding.apply {
                tvNomeProduto.text = item.nome
                tvQuantidade.text = "${item.quantidade} ${item.unidade}"
                chkSelecionado.isChecked = item.selecionado
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ListItemProdutoBinding.inflate(
            /* inflater = */ LayoutInflater.from(parent.context),
            /* parent = */ parent,
            /* attachToParent = */ false
        )

        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int = listaDeProdutos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item = listaDeProdutos[position])

        binding.chkSelecionado.setOnCheckedChangeListener { _, isChecked ->
            itemCheckCallback?.invoke(isChecked, position)
        }
    }
}