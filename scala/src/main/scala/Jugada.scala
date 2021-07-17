class Jugada(val sucesos: List[Suceso],
             operacionGanar : Function[Int, Int],
             operacionPerder : Function[Int, Int]) {

  def obtenerResultado(sucesoResultado: Suceso, monto : Int) : ResultadoJuego = {
    if(sucesos.exists(s => s.seCumple(sucesoResultado))) {
      val ganancia = operacionGanar.apply(monto)
      new ResultadoJuego(sucesoResultado, ganancia)
    }
    else {
      val ganancia = operacionPerder.apply(monto)
      new ResultadoJuego(sucesoResultado, ganancia)
    }
  }
}
