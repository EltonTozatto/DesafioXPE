package br.com.eltontozatto.mercadoapp.ui.main

import android.content.Intent
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import br.com.eltontozatto.mercadoapp.R
import br.com.eltontozatto.mercadoapp.databinding.ActivityMainBinding
import br.com.eltontozatto.mercadoapp.model.entidade.Produto
import br.com.eltontozatto.mercadoapp.ui.incluirProduto.IncluirProdutoActivity

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val retornoProduto = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
            activityResult.data?.let {
                if (it.hasExtra(RETORNO)) {
                    if (VERSION.SDK_INT >= 33) {
                        Log.e("XPEInfo", "Produto: ${it.getParcelableExtra(RETORNO, Produto::class.java)}")
                        viewModel.salvarProdutos(it.getParcelableExtra(RETORNO, Produto::class.java)!!)
                    } else {
                        viewModel.salvarProdutos(it.getParcelableExtra(RETORNO)!!)
                    }
                } else {
                    Log.e("XPEerro", "Não encontrada a chave de retorno")
                }
            }
        } else {
            Log.e("XPEerro", "Erro ao inserir produto")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        configuraListeners()
        configuraObservers()
        iniciarDados()
    }

    private fun configuraObservers() {
        configurarRecyclerView()
    }

    private fun configurarRecyclerView() {
        viewModel.listaDeProdutos.observe(this) {lista ->
            atualizarLista(lista)
        }
    }

    private fun atualizarLista(lista: List<Produto>?) {
        if (lista.isNullOrEmpty()) {
            binding.apply {
                rvProdutos.visibility = View.GONE
                tvSemProduto.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                rvProdutos.visibility = View.VISIBLE
                tvSemProduto.visibility = View.GONE
                rvProdutos.adapter = ProdutoAdapter(listaDeProdutos = lista, itemCheckCallback =  fun(status: Boolean, position: Int) {
                    atualizarProdutoChecado(status, position)
                })
            }
        }
    }

    private fun atualizarProdutoChecado(status: Boolean, position: Int) {
        viewModel.atualizarProdutoChecado(status, position)
    }

    private fun iniciarDados() {
        viewModel.inicializarDados()
    }

    private fun configuraListeners() {
        configuraFabListener()
    }

    private fun configuraFabListener() {
        binding.fabAdicionarProduto.setOnClickListener {
            Intent(this, IncluirProdutoActivity::class.java).let {
                retornoProduto.launch(it)
            }
        }

        binding.fabAdicionarProduto.setOnLongClickListener {
            viewModel.limparListaDeProdutos()
            Toast.makeText(this, getString(R.string.toast_lista_limpa), Toast.LENGTH_SHORT).show()
            it.isLongClickable
        }
    }

    companion object {
        const val RETORNO = "retorno_produto"
    }
}