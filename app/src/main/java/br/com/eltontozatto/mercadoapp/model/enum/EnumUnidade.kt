package br.com.eltontozatto.mercadoapp.model.enum

enum class EnumUnidade {
    KG,
    LITROS,
    UNIDADE,
    UNDEFINED;

    companion object {
        fun valueOf(value: Int): EnumUnidade {
            return when (value) {
                0 -> KG
                1 -> LITROS
                2 -> UNIDADE
                else -> UNDEFINED
            }
        }
    }
}