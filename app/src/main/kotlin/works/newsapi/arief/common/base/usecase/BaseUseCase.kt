package works.newsapi.arief.common.base.usecase

interface BaseUseCase<in P, in T, out R> {
     fun execute(parameters: P, secondParameters: T): R
}
