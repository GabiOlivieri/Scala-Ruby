trait Juego {
  def nombre: String

  def distribucion: Distribucion

  def sucesos: List[Suceso]

  def jugar(apuestas: List[Apuesta]): List[ResultadoJuegoApuesta] = {

    val resutadosApuesta = sucesos.map(sucesoResultado => {
      val resultados = apuestas
        .map(apuesta => apuesta.ejecutar(sucesoResultado))

      new ResultadoApuesta(sucesoResultado, resultados.map(r => r.ganancia).sum)
    })

    val resultadosXGanancia = resutadosApuesta.groupBy(r => r.ganancia).toList

    resultadosXGanancia.map(g => {
      var probabilidadTotal = g._2.map(ra => distribucion.getProbabilidad(ra.suceso).getOrElse(0.0)).sum
      probabilidadTotal = BigDecimal(probabilidadTotal).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
      // verificar el redondeo
      new ResultadoJuegoApuesta(g._1, probabilidadTotal)
    })
  }
}