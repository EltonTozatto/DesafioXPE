package br.com.eltontozatto.mercadoapp.ui.incluirProduto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import br.com.eltontozatto.mercadoapp.R
import br.com.eltontozatto.mercadoapp.databinding.ActivityIncluirProdutoBinding
import br.com.eltontozatto.mercadoapp.model.entidade.Produto
import br.com.eltontozatto.mercadoapp.model.enum.EnumUnidade
import br.com.eltontozatto.mercadoapp.ui.main.MainActivity
import br.com.eltontozatto.mercadoapp.ui.main.MainViewModel

class IncluirProdutoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIncluirProdutoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncluirProdutoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        configurarListeners()
    }

    private fun configurarListeners() {
        configurarButtonListener()
    }

    private fun configurarButtonListener() {
        binding.btnSalvar.setOnClickListener {
            salvarDados()
        }
    }

    private fun salvarDados() {
        binding.apply {
            val produto = etNomeProduto.text.toString()
            val quantidade = etQuantidadeProduto.text.toString().toDouble()
            val radioGroupId = rgUnidadeMedida.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(radioGroupId)
            val unidade = rgUnidadeMedida.indexOfChild(radioButton)

            tilNomeProduto.error = if (produto.isEmpty())
                getText(R.string.erro_sem_produto)
            else
                null

            tilQuantidadeProduto.error = if (quantidade.equals(0))
                getText(R.string.erro_sem_quantidade)
            else
                null

            if (produto.isNotEmpty() && quantidade > 0 && !rgUnidadeMedida.isSelected) {
                val item = Produto(
                    nome = produto,
                    quantidade = quantidade,
                    unidade = EnumUnidade.valueOf(unidade)
                )

                Intent().apply {
                    putExtra(MainActivity.RETORNO, item)
                    setResult(RESULT_OK, this)
                }
                finish()
            } else {
                Log.e("XPEerro", getString(R.string.erro_cadastro_produto))
            }
        }
    }
}