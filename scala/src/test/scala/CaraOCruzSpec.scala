import org.scalatest.funspec.AnyFunSpec

class CaraOCruzSpec extends AnyFunSpec {
  val caraOCruz = new CaraOCruz("Cara o Cruz Común")

  describe("Test Cara o Cruz") {
    describe("Jugada simple") {
      it("Se apuesta a cara: gana 40 con cara y pierde con cruz") {
        val apuesta = new Apuesta(caraOCruz.jugadaCara, 20)
        val resultados : List[ResultadoJuegoApuesta] = caraOCruz.jugar(List(apuesta))
        assert(resultados.length == 2)
        assert(resultados.exists(r => r.gananciaTotal == 40 && r.probabilidad == 0.5))
        assert(resultados.exists(r => r.gananciaTotal == 0 && r.probabilidad == 0.5))
      }

      it("Se apuesta a cruz: gana 40 con cruz y pierde con cara") {
        val apuesta = new Apuesta(caraOCruz.jugadaCruz, 20)
        val resultados : List[ResultadoJuegoApuesta] = caraOCruz.jugar(List(apuesta))
        assert(resultados.length == 2)
        assert(resultados.exists(r => r.gananciaTotal == 0 && r.probabilidad == 0.5))
        assert(resultados.exists(r => r.gananciaTotal == 40 && r.probabilidad == 0.5))
      }
    }

    describe("Jugada compuesta") {
      it("Se apuesta dos veces a cara: gana 50 con cara y pierde con cruz") {
        val apuestaCara1 = new Apuesta(caraOCruz.jugadaCara, 15)
        val apuestaCara2 = new Apuesta(caraOCruz.jugadaCara, 10)

        val resultados : List[ResultadoJuegoApuesta] = caraOCruz.jugar(List(apuestaCara1, apuestaCara2))
        assert(resultados.length == 2)
        assert(resultados.exists(r => r.gananciaTotal == 50 && r.probabilidad == 0.5))
        assert(resultados.exists(r => r.gananciaTotal == 0 && r.probabilidad == 0.5))
      }
      it("Se apuesta una vez cara y una vez cruz: gana 40 con cara y recibe 30 con cruz") {
        val apuestaCara = new Apuesta(caraOCruz.jugadaCara, 20)
        val apuestaCruz = new Apuesta(caraOCruz.jugadaCruz, 15)

        val resultados : List[ResultadoJuegoApuesta] = caraOCruz.jugar(List(apuestaCara, apuestaCruz))
        assert(resultados.length == 2)
        assert(resultados.exists(r => r.gananciaTotal == 40 && r.probabilidad == 0.5))
        assert(resultados.exists(r => r.gananciaTotal == 30 && r.probabilidad == 0.5))
      }
    }
  }
}