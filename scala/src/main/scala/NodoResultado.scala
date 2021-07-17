class NodoResultado(var resultado : ResultadoJuegoApuesta) {
  var montoActual : Int = 0
  var probabilidadAcumulada : Double = 1
  var childs: List[NodoResultado] = List()
}