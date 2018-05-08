package palindrome

import akka.actor.ActorSystem
import akka.http.scaladsl.server.HttpApp
import com.google.inject.{AbstractModule, Guice, Provides}

object Server extends HttpApp with App with RoutesFactory{
  private val SERVER_HOST = "localhost"
  private val SERVER_PORT = 8080
  private val SYSTEM      = ActorSystem("palindrome-system")

  startServer(SERVER_HOST, SERVER_PORT, SYSTEM)
}

trait RoutesFactory{
  def routes = {
    val applicationModule = new ApplicationModule()

    val routesProvider = Guice.createInjector(applicationModule)
      .getInstance(classOf[RoutesProvider])

    routesProvider.routes
  }
}

class ApplicationModule extends AbstractModule{
  @Provides
  def configurationProvider = PalindromeFilterConf(
    maxPalindromeAgeInMinutes = 10,
    maxPalindromesPerRequest  = 100
  )
}
