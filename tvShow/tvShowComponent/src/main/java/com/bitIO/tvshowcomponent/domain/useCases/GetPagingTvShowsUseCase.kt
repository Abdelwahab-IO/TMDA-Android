package com.bitIO.tvshowcomponent.domain.useCases

import androidx.paging.PagingData
import androidx.paging.map
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.mapper.toDomain
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPagingTvShowsUseCase @Inject constructor(private val repository: TvShowRepository) {
     operator fun invoke(type: Int): Flow<PagingData<TvShow>> {
        return repository.getPagingTvShows(type).map { it.map { it.toDomain() } }
    }

}