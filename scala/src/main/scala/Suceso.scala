case class Suceso(nombre : String) {

  def seCumple(suceso: Suceso): Boolean = nombre == suceso.nombre
}

/*

abstract class Suceso

case class SucesoValor(valor: String) extends Suceso

case class SucesoColor(color: String) extends Suceso

case class SucesoParidad(esPar : Boolean) extends Suceso

case class SucesoDocena(docena: String) extends Suceso

def seCumpleSuceso(sucesoResultado : Suceso, sucesos : List[Suceso]) : Boolean = {
  sucesos.exists(suceso =>
    suceso match {
      case SucesoColor(color) => sucesoResultado
    }
  )

}
*/