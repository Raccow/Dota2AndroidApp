package github.com.rhacco.dotascoop.sources.repos

import github.com.rhacco.dotascoop.App
import github.com.rhacco.dotascoop.api.LeaderboardsResponse
import github.com.rhacco.dotascoop.sources.databases.entities.HeroEntity
import github.com.rhacco.dotascoop.sources.databases.entities.ItemEntity
import io.reactivex.Single

object CustomAPILocalDataSource {
    fun getHeroes(): Single<List<HeroEntity>> =
            Single.create(
                    { subscriber ->
                        App.sDatabase.getHeroes()
                                .subscribe(
                                        { result ->
                                            if (result.isNotEmpty() &&
                                                    App.sSharedPreferences.getHeroesValid())
                                                subscriber.onSuccess(result)
                                            else
                                                CustomAPIRemoteDataSource.getHeroes()
                                                        .subscribe(
                                                                { remoteResult ->
                                                                    App.sDatabase.storeHeroes(remoteResult)
                                                                    App.sSharedPreferences.setHeroesValid()
                                                                    subscriber.onSuccess(remoteResult)
                                                                },
                                                                { error -> subscriber.onError(error) }
                                                        )
                                        },
                                        { error -> subscriber.onError(error) }
                                )
                    }
            )

    fun getItems(): Single<List<ItemEntity>> =
            Single.create(
                    { subscriber ->
                        App.sDatabase.getItems()
                                .subscribe(
                                        { result ->
                                            if (result.isNotEmpty() &&
                                                    App.sSharedPreferences.getItemsValid())
                                                subscriber.onSuccess(result)
                                            else
                                                CustomAPIRemoteDataSource.getItems()
                                                        .subscribe(
                                                                { remoteResult ->
                                                                    App.sDatabase.storeItems(remoteResult)
                                                                    App.sSharedPreferences.setItemsValid()
                                                                    subscriber.onSuccess(remoteResult)
                                                                },
                                                                { error -> subscriber.onError(error) }
                                                        )
                                        },
                                        { error -> subscriber.onError(error) }
                                )
                    }
            )

    fun getLeaderboard(region: String): Single<List<LeaderboardsResponse.Entry>> =
            Single.create(
                    { subscriber ->
                        App.sDatabase.getLeaderboard(region)
                                .subscribe(
                                        { result ->
                                            if (result.isNotEmpty() &&
                                                    App.sSharedPreferences.getLeaderboardValid(region))
                                                subscriber.onSuccess(result)
                                            else
                                                CustomAPIRemoteDataSource.getLeaderboard(region)
                                                        .subscribe(
                                                                { remoteResult ->
                                                                    App.sDatabase.storeLeaderboard(
                                                                            region, remoteResult)
                                                                    App.sSharedPreferences
                                                                            .setLeaderboardValid(region)
                                                                    subscriber.onSuccess(remoteResult)
                                                                },
                                                                { error -> subscriber.onError(error) }
                                                        )
                                        },
                                        { error -> subscriber.onError(error) }
                                )
                    }
            )
}