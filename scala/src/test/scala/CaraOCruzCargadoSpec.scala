import org.scalatest.funspec.AnyFunSpec

class CaraOCruzCargadoSpec extends AnyFunSpec {
  val caraOCruzCargado = new CaraOCruzCargado("Cara o Cruz Cargado")

  describe("Test Cara o Cruz Cargado") {
    describe("Jugada simple") {
      it("Se apuesta a cara: gana 40 con cara y pierde con cruz") {
        val apuesta = new Apuesta(caraOCruzCargado.jugadaCara, 20)
        val resultados : List[ResultadoJuegoApuesta] = caraOCruzCargado.jugar(List(apuesta))
        assert(resultados.length == 2)
        assert(resultados.exists(r => r.gananciaTotal == 40 && r.probabilidad == 0.57))
        assert(resultados.exists(r => r.gananciaTotal == 0 && r.probabilidad == 0.43))
      }

      it("Se apuesta a cruz: gana 40 con cruz y pierde con cara") {
        val apuesta = new Apuesta(caraOCruzCargado.jugadaCruz, 20)
        val resultados : List[ResultadoJuegoApuesta] = caraOCruzCargado.jugar(List(apuesta))
        assert(resultados.length == 2)
        assert(resultados.exists(r => r.gananciaTotal == 0 && r.probabilidad == 0.57))
        assert(resultados.exists(r => r.gananciaTotal == 40 && r.probabilidad == 0.43))
      }
    }

    describe("Jugada compuesta") {
      it("Se apuesta dos veces a cara: gana 50 con cara y pierde con cruz") {
        val apuestaCara1 = new Apuesta(caraOCruzCargado.jugadaCara, 15)
        val apuestaCara2 = new Apuesta(caraOCruzCargado.jugadaCara, 10)

        val resultados : List[ResultadoJuegoApuesta] = caraOCruzCargado.jugar(List(apuestaCara1, apuestaCara2))
        assert(resultados.length == 2)
        assert(resultados.exists(r => r.gananciaTotal == 50 && r.probabilidad == 0.57))
        assert(resultados.exists(r => r.gananciaTotal == 0 && r.probabilidad == 0.43))
      }
      it("Se apuesta una vez cara y una vez cruz: gana 40 con cara y recibe 30 con cruz") {
        val apuestaCara = new Apuesta(caraOCruzCargado.jugadaCara, 20)
        val apuestaCruz = new Apuesta(caraOCruzCargado.jugadaCruz, 15)

        val resultados : List[ResultadoJuegoApuesta] = caraOCruzCargado.jugar(List(apuestaCara, apuestaCruz))
        assert(resultados.length == 2)
        assert(resultados.exists(r => r.gananciaTotal == 40 && r.probabilidad == 0.57))
        assert(resultados.exists(r => r.gananciaTotal == 30 && r.probabilidad == 0.43))
      }
    }
  }
}