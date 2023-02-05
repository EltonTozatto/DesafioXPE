package br.com.eltontozatto.mercadoapp.model.entidade

import android.os.Parcelable
import br.com.eltontozatto.mercadoapp.model.enum.EnumUnidade
import kotlinx.parcelize.Parcelize

@Parcelize
data class Produto(
    var nome: String,
    var quantidade: Double,
    var selecionado: Boolean = false,
    var unidade: EnumUnidade
) :Parcelable
