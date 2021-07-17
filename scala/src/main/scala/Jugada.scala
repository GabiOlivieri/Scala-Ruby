class Jugada(val sucesos: List[Suceso],
             operacionGanar : Function[Int, Int],
             operacionPerder : Function[Int, Int],
             operacionEmpate : Function[Int, Int] = { _*1 },
             val sucesosConEmpate: List[Suceso] = List()){

  def obtenerResultado(sucesoResultado: Suceso, monto : Int) : ResultadoJuego = {
    if(sucesos.exists(s => s.seCumple(sucesoResultado))) {
      val ganancia = operacionGanar.apply(monto)
      new ResultadoJuego(sucesoResultado, ganancia)
    }
    else if (sucesosConEmpate.exists(s => s.seCumple(sucesoResultado))) {
      val ganancia = operacionEmpate.apply(monto)
      new ResultadoJuego(sucesoResultado, ganancia)
    }
    else {
      val ganancia = operacionPerder.apply(monto)
      new ResultadoJuego(sucesoResultado, ganancia)
    }
  }
}
