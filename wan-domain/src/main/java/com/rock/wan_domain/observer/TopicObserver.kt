package com.rock.wan_domain.observer


import com.rock.lib_base.arch.SubjectInteractor
import com.rock.wan_data.entity.Article
import com.rock.wan_repositroy.ArticleRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class TopicObserver @Inject constructor(
    private val articleRepository: ArticleRepository
): SubjectInteractor<Unit, List<Article>>() {

    override fun createObservable(params: Unit): Flow<List<Article>> = articleRepository.observeTopic()
}