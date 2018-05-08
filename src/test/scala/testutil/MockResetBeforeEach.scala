package testutil

import org.mockito.Mockito.reset
import org.scalatest.{BeforeAndAfterEach, TestSuite}
import org.mockito.Mockito.mock
import scala.collection.mutable.ArrayBuffer

trait MockResetBeforeEach extends TestSuite with BeforeAndAfterEach{

  val mocks = ArrayBuffer.empty[AnyRef]

  def mockWithReset[T<:AnyRef](classToMock:Class[T]): T = {
    val newMock = mock[T](classToMock)
    mocks += newMock
    newMock
  }

  override protected def beforeEach(): Unit = mocks.foreach(reset(_))
}
