package com.example.user.domain.useCases

import com.example.user.domain.repositories.UserRepository
import javax.inject.Inject

class AddOrRemoveTvFromWatchListUseCase @Inject constructor(private val repo: UserRepository) {
    suspend fun invoke(seriesId: Int, isAddRequest: Boolean): Result<Unit> {
      return  try {
            Result.success( repo.addSeriesToWatchList(seriesId, isAddRequest))
        }catch (e:Throwable){
            Result.failure(e)
        }

    }
}