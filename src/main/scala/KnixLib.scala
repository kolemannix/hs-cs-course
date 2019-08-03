object KnixLib {

  def typeOf[A](a: A): String = {
    a.getClass.getTypeName
  }

}
