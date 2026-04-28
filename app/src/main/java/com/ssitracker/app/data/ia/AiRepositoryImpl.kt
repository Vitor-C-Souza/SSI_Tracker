package com.ssitracker.app.data.ia

import com.google.ai.client.generativeai.GenerativeModel
import com.ssitracker.app.domain.model.SSI
import com.ssitracker.app.domain.repository.AiRepository

class AiRepositoryImpl(
    private val generativeModel: GenerativeModel,
) : AiRepository {
    override suspend fun getDailyTip(ssi: SSI): String? {
        val prompt = """
            Sou um usuário do LinkedIn e meu Social Selling Index (SSI) atual é ${ssi.total}/100.
            Meus pilares são:
            - Estabelecer sua marca profissional: ${ssi.professionalBrand}
            - Localizar as pessoas certas: ${ssi.findPeople}
            - Interagir oferecendo insights: ${ssi.engageInsights}
            - Criar relacionamentos: ${ssi.buildRelationships}
            
            Com base nesses números, me dê uma dica curta, prática e motivadora em português (máximo 2 frases) de como posso melhorar meu score hoje.
        """.trimIndent()

        return try {
            val response = generativeModel.generateContent(prompt)

            response.text ?: "Continue focado em interagir com sua rede para aumentar seu SSI!"
        } catch (e: Exception) {
            "No momento não foi possível gerar uma dica personalizada, mas continue sua rotina de networking!"
        }
    }
}