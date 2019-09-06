package lib

object DebugMacro {

  import scala.language.experimental.macros
  import scala.reflect.macros.Context

  def debug[A](block: => A): A = macro debugImpl[A]

  def debugImpl[A: c.WeakTypeTag](c: Context)(block: c.Expr[A]): c.Expr[A] = {
    import c.universe._
    block.tree match {
      case Literal(Constant(_)) =>
        reify {
          println(s"[DEBUG LIT] ${block.splice}")
          block.splice
        }
      case _ =>
        val paramRep = show(block.tree)
        val paramRepTree = Literal(Constant(paramRep))
        val paramRepExpr = c.Expr[String](paramRepTree)
        val reified = reify {
          println(s"[DEBUG EXPR] ${paramRepExpr.splice} = ${block.splice}")
          block.splice
        }
        reified
    }
  }

}
