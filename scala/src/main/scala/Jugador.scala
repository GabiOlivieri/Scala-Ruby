import scala.collection.immutable.Nil

class Jugador(val criterio : JugadorCriterioPlan = new JugarRacional) {
  def obtenerPlan(monto : Int, juegos : List[JuegoApuestas]): List[JuegoApuestas] ={
    criterio.elegirPlan(this, monto, juegos)
  }

  def apostarSucesivamente(montoInicial : Int, juegoApuestas : List[JuegoApuestas]): List[DistribucionGanancia] ={
    val hojas = obtenerHojas(jugarSucesivamente(montoInicial, 1, juegoApuestas))
    hojas.map(h => new DistribucionGanancia(h.montoActual, h.probabilidadAcumulada))
  }

  private def jugarSucesivamente(monto: Int, probabilidadAnterior: Double, juegoApuestas: List[JuegoApuestas]): List[NodoResultado] = {
    juegoApuestas match {
      case Nil => Nil
      case primerJuego :: restoJuegos if primerJuego.sePuedeApostar(monto) => {
        val montoRestante = monto - primerJuego.totalApuesta()
        val resultados = primerJuego.apostar()
        resultados.foreach(r => {
          r.montoActual = montoRestante + r.resultado.gananciaTotal
          r.probabilidadAcumulada = probabilidadAnterior * r.resultado.probabilidad
          r.childs = jugarSucesivamente(r.montoActual, r.probabilidadAcumulada, restoJuegos)
        })
        resultados
      }
      case _ :: restoJuegos => jugarSucesivamente(monto, probabilidadAnterior, restoJuegos)
    }
  }

  private def obtenerHojas(nodos : List[NodoResultado]) : List[NodoResultado] ={
    nodos.flatMap(n => {
      if (n.childs.isEmpty) List(n)
      else obtenerHojas(n.childs)
    })
  }
}



