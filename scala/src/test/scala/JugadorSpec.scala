import org.scalatest.funspec.AnyFunSpec

class JugadorSpec extends AnyFunSpec {
  val pepe = new Jugador()
  val caraOCruz = new CaraOCruz("Cara o Cruz Comun")
  val ruleta = new Ruleta("Ruleta")

  describe("Jugador") {
    describe("Apuestas Sucesivas") {
      it("Se tira una moneda apostando $10 a cara y luego a " +
        "la ruleta apostando $15 al 0, esto partiendo de un monto inicial de $15.") {
        val apuestasMoneda: List[Apuesta] = List(new Apuesta(caraOCruz.jugadaCara, 10))
        val monedaApuesta = new JuegoApuestas(caraOCruz, apuestasMoneda)

        val apuestaRuleta: List[Apuesta] = List(new Apuesta(ruleta.jugada0, 15))
        val ruletaApuesta = new JuegoApuestas(ruleta, apuestaRuleta)

        val distribucionGanancia = pepe.apostarSucesivamente(15, List(monedaApuesta, ruletaApuesta))
        assert(distribucionGanancia.length == 3)
        assert(distribucionGanancia.exists(r => r.ganancia == 550 && r.probabilidad == 0.015))
        assert(distribucionGanancia.exists(r => r.ganancia == 10 && r.probabilidad == 0.485))
        assert(distribucionGanancia.exists(r => r.ganancia == 5 && r.probabilidad == 0.5))
      }
    }

    describe("Criterios de Jugares") {
      it("Un juego de moneda y otro de ruleta con el criterio Racional") {
        val apuestasMoneda: List[Apuesta] = List(new Apuesta(caraOCruz.jugadaCara, 10))
        val monedaApuesta = new JuegoApuestas(caraOCruz, apuestasMoneda)

        val apuestaRuleta: List[Apuesta] = List(new Apuesta(ruleta.jugada0, 15))
        val ruletaApuesta = new JuegoApuestas(ruleta, apuestaRuleta)
        val plan = pepe.obtenerPlan(15, List(monedaApuesta, ruletaApuesta))
        assert(plan.head.juego.nombre == "Ruleta")
      }

      it("Un juego de moneda y otro de ruleta con el criterio Arriesgado") {
        val apuestasMoneda: List[Apuesta] = List(new Apuesta(caraOCruz.jugadaCara, 10))
        val monedaApuesta = new JuegoApuestas(caraOCruz, apuestasMoneda)

        val apuestaRuleta: List[Apuesta] = List(new Apuesta(ruleta.jugada0, 20))
        val ruletaApuesta = new JuegoApuestas(ruleta, apuestaRuleta)
        val pepeExtremo = new Jugador(new JugarArriesgado)
        val plan = pepeExtremo.obtenerPlan(10, List(monedaApuesta, ruletaApuesta))
        assert(plan.head.juego.nombre == "Cara o Cruz Comun")
      }

      it("Dos juegos de moneda con el criterio Cauto") {
        val apuestasMoneda: List[Apuesta] = List(new Apuesta(caraOCruz.jugadaCara, 10))
        val monedaApuesta = new JuegoApuestas(caraOCruz, apuestasMoneda)

        val apuestaRuleta: List[Apuesta] = List(new Apuesta(ruleta.jugada0, 20))
        val ruletaApuesta = new JuegoApuestas(ruleta, apuestaRuleta)
        val pepeTranka = new Jugador(new JugarCauto)
        val plan = pepeTranka.obtenerPlan(10, List(monedaApuesta, ruletaApuesta))
        assert(plan.head.juego.nombre == "Ruleta")
      }

      it("Dos juegos de moneda con el criterio Ultra Cauto") {
        val apuestasMoneda: List[Apuesta] = List(new Apuesta(caraOCruz.jugadaCara, 10))
        val monedaApuesta = new JuegoApuestas(caraOCruz, apuestasMoneda)

        val apuestaRuleta: List[Apuesta] = List(new Apuesta(ruleta.jugada0, 20))
        val ruletaApuesta = new JuegoApuestas(ruleta, apuestaRuleta)
        val pepeUltraTranka = new Jugador(new JugarUltraCauto)
        val plan = pepeUltraTranka.obtenerPlan(10, List(monedaApuesta, ruletaApuesta))
        assert(plan.head.juego.nombre == "Ruleta")
      }
    }
  }
}
