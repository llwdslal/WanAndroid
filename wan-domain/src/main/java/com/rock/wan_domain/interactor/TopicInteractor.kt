package com.rock.wan_domain.interactor


import com.rock.lib_base.arch.Interactor
import com.rock.wan_repositroy.ArticleRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

private const val TAG = "UpdateTopicInteractor"
@ViewModelScoped
class TopicInteractor @Inject constructor(
    private val articleRepository: ArticleRepository
): Interactor<Unit>() {

    override suspend fun doWork(params: Unit) = articleRepository.updateTopic()
}