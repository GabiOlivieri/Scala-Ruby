trait JugadorCriterioPlan {
  def elegirPlan(jugador: Jugador, monto: Int, juegoApuestas: List[JuegoApuestas]): List[JuegoApuestas] = {
    val planes : List[List[JuegoApuestas]] = juegoApuestas.permutations.toList
    seleccionarPlan(jugador, monto, planes)
  }

  protected def seleccionarPlan(jugador: Jugador, monto: Int, planes: List[List[JuegoApuestas]]): List[JuegoApuestas]
}

class JugarRacional extends JugadorCriterioPlan {
  protected override def seleccionarPlan(jugador: Jugador, monto: Int, planes: List[List[JuegoApuestas]]): List[JuegoApuestas] = {
    planes.map(p => (p, obtenerPuntaje(jugador, monto, p)))
      .maxBy(p => p._2)._1
  }

  private def obtenerPuntaje(jugador: Jugador, monto: Int, p: List[JuegoApuestas]) : Double = {
    val puntaje: Double = jugador
      .apostarSucesivamente(monto, p)
      .map(r => r.ganancia * r.probabilidad).sum
    puntaje
  }
}

class JugarArriesgado extends JugadorCriterioPlan {
  protected override def seleccionarPlan(jugador: Jugador, monto: Int, planes: List[List[JuegoApuestas]]): List[JuegoApuestas] = {
    planes.map(p => (p, obtenerMaximaGanancia(jugador, monto, p)))
      .maxBy(p => p._2)._1
  }

  private def obtenerMaximaGanancia(jugador: Jugador, monto: Int, p: List[JuegoApuestas]) : Int = {
    jugador.apostarSucesivamente(monto, p).map(r => r.ganancia).max
  }
}

class JugarCauto extends JugadorCriterioPlan {
  protected override def seleccionarPlan(jugador: Jugador, monto: Int, planes: List[List[JuegoApuestas]]): List[JuegoApuestas] = {
    planes.map(p => (p, ObtenerMayorProbabilidadDeNoPerder(jugador, monto, p)))
      .maxBy(p => p._2)._1
  }

  private def ObtenerMayorProbabilidadDeNoPerder(jugador: Jugador, monto: Int, p: List[JuegoApuestas]) : Double = {
    jugador.apostarSucesivamente(monto, p)
      .filter(r => r.ganancia >= monto)
      .map(r => r.probabilidad).sum
  }
}

class JugarUltraCauto extends JugadorCriterioPlan {
  protected override def seleccionarPlan(jugador: Jugador, monto: Int, planes: List[List[JuegoApuestas]]): List[JuegoApuestas] = {
    planes.map(p => (p, ObtenerMayorProbabilidadDeNoPerder(jugador, monto, p)))
      .maxBy(p => p._2)._1
  }

  private def ObtenerMayorProbabilidadDeNoPerder(jugador: Jugador, monto: Int, p: List[JuegoApuestas]) : Double = {
    val ganancia = jugador.apostarSucesivamente(monto, p)
      .filter(r => r.ganancia > monto)
      .map(r => r.probabilidad).sum

    val perdida = jugador.apostarSucesivamente(monto, p)
      .filter(r => r.ganancia < monto)
      .map(r => r.probabilidad).sum

    ganancia - perdida
  }
}