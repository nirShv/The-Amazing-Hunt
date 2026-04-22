package com.theamazinghunt.data.repository

import com.theamazinghunt.domain.model.ClueTask
import com.theamazinghunt.domain.repository.AiClueGeneratorRepository
import com.theamazinghunt.domain.repository.ClueGenerationRequest

class MockAiClueGeneratorRepository : AiClueGeneratorRepository {
    override suspend fun generateClues(request: ClueGenerationRequest): Result<List<ClueTask>> {
        return Result.success(emptyList())
    }
}
