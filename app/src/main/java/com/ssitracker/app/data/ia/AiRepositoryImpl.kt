package com.ssitracker.app.data.ia

import com.google.ai.client.generativeai.GenerativeModel
import com.ssitracker.app.domain.model.SSI
import com.ssitracker.app.domain.repository.AiRepository

class AiRepositoryImpl(
    private val generativeModel: GenerativeModel,
) : AiRepository {
    override suspend fun getDailyTip(ssiHistory: List<SSI>): String? {
        if (ssiHistory.isEmpty()) return null

        // Limita aos últimos 7 registros para focar em tendências recentes e economizar tokens
        val recentHistory = ssiHistory.takeLast(7)

        val historyDescription = recentHistory.joinToString("\n") { ssi ->
            "- Date: ${ssi.id}, Total: ${ssi.total}/100 (Brand: ${ssi.professionalBrand}, People: ${ssi.findPeople}, Insights: ${ssi.engageInsights}, Relationships: ${ssi.buildRelationships})"
        }

        val prompt = """
            I am a LinkedIn user tracking my Social Selling Index (SSI). 
            Here is my SSI history for the last 7 entries:
            $historyDescription
            
            Task:
            1. Analyze the trends over these days.
            2. Identify which of the 4 pillars is underperforming or shows a downward trend.
            3. Provide a short, practical, and motivating tip in English (max 2 sentences) on what specific action I should take today to improve that score.
            
            Response must be in English.
        """.trimIndent()

        return try {
            val response = generativeModel.generateContent(prompt)
            response.text ?: "Focus on engaging with your network today to boost your SSI!"
        } catch (e: Exception) {
            "Keep building your professional brand and connecting with people to improve your score!"
        }
    }
}