package com.tc.hamie.chakib.data

data class Movies(
    val title: String,
    val synopsis: String,
    val rating: Int
) {
    companion object {
        fun fakeMovies() = listOf(
            Movies("title of the first movie", "synopsis ...", 87),
            Movies("title of the second movie", "synopsis ...", 65)
        )
    }
}
fun getMoviesWithGoodRating() = Movies.fakeMovies().filter { it.rating > 70 }