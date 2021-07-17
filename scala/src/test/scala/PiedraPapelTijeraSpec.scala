import org.scalatest.funspec.AnyFunSpec

class PiedraPapelTijeraSpec extends AnyFunSpec {
  val piedraPapelTijera = new PiedraPapelTijera("Piedra, Papel o Tijera Cargado")

  describe("Piedra, Papel o Tijera Cargado") {
    describe("Jugada simple") {
      it("Se apuesta a piedra: gana 40 contra tijera, pierde con papel y empata con piedra") {
        val apuesta = new Apuesta(piedraPapelTijera.jugadaPiedra, 20)
        val resultados : List[ResultadoJuegoApuesta] = piedraPapelTijera.jugar(List(apuesta))
        assert(resultados.length == 3)
        assert(resultados.exists(r => r.gananciaTotal == 40 && r.probabilidad == 0.40))
        assert(resultados.exists(r => r.gananciaTotal == 20 && r.probabilidad == 0.35))
        assert(resultados.exists(r => r.gananciaTotal == 0 && r.probabilidad == 0.25))
      }


    }
  }
}