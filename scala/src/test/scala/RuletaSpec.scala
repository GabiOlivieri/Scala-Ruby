import org.scalatest.funspec.AnyFunSpec

class RuletaSpec extends AnyFunSpec{
  var ruleta = new Ruleta("Ruleta")

  describe("Test Ruleta") {
    describe("Jugada simple") {
      it("Apuesto 10 al 1: gana 200, pierde con cualquier otro") {
        val apuesta = new Apuesta(ruleta.jugada1, 10)
        val resultados : List[ResultadoJuegoApuesta] = ruleta.jugar(List(apuesta))
        assert(resultados.length == 2)
        assert(resultados.exists(r => r.gananciaTotal == 10*36 && r.probabilidad == 0.03))
        assert(resultados.exists(r => r.gananciaTotal == 0 && r.probabilidad == 0.97))
      }

      it("Apuesto 15 al rojo: gana 30, pierde con cualquier otro color") {
        val apuesta = new Apuesta(ruleta.jugadaRojo, 15)
        val resultados : List[ResultadoJuegoApuesta] = ruleta.jugar(List(apuesta))
        assert(resultados.length == 2)
        assert(resultados.exists(r => r.gananciaTotal == 30 && r.probabilidad == 0.49))
        assert(resultados.exists(r => r.gananciaTotal == 0 && r.probabilidad == 0.51))
      }

      it("Apuesto 50 al Par: gana 100, pierde con impar o 0") {
        val apuesta = new Apuesta(ruleta.jugadaPar, 50)
        val resultados : List[ResultadoJuegoApuesta] = ruleta.jugar(List(apuesta))
        assert(resultados.length == 2)
        assert(resultados.exists(r => r.gananciaTotal == 100 && r.probabilidad == 0.49))
        assert(resultados.exists(r => r.gananciaTotal == 0 && r.probabilidad == 0.51))
      }

      it("Apuesto 30 a la primera docena: gana 90, pierde con impar o 0") {
        val apuesta = new Apuesta(ruleta.jugadaPrimeraDocena, 30)
        val resultados : List[ResultadoJuegoApuesta] = ruleta.jugar(List(apuesta))
        assert(resultados.length == 2)
        assert(resultados.exists(r => r.gananciaTotal == 90 && r.probabilidad == 0.32))
        assert(resultados.exists(r => r.gananciaTotal == 0 && r.probabilidad == 0.68))
      }
    }

    describe("Jugada Compleja"){
      it("Apuesto $10 al 1 y $50 al rojo: gana 460 si es 1 (rojo) o 100 si es rojo pero no 1 o pierde con cualquier otro") {
        val apuesta1 = new Apuesta(ruleta.jugada1, 10)
        val apuesta2 = new Apuesta(ruleta.jugadaRojo, 50)
        val resultados : List[ResultadoJuegoApuesta] = ruleta.jugar(List(apuesta1, apuesta2))
        assert(resultados.length == 3)
        assert(resultados.exists(r => r.gananciaTotal == 460 && r.probabilidad == 0.03))
        assert(resultados.exists(r => r.gananciaTotal == 100 && r.probabilidad == 0.46))
        assert(resultados.exists(r => r.gananciaTotal == 0 && r.probabilidad == 0.51))
      }
    }

    it("Apuesto $10 al 0, al negro $5, al par $10 y a $20 a la segunda docena: gana 200, pierde con cualquier otro") {
      val apuesta1 = new Apuesta(ruleta.jugada0, 10)
      val apuesta2 = new Apuesta(ruleta.jugadaNegro, 5)
      val apuesta3 = new Apuesta(ruleta.jugadaPar, 10)
      val resultados : List[ResultadoJuegoApuesta] = ruleta.jugar(List(apuesta1, apuesta2, apuesta3))
//      resultados.foreach(r => println(r.probabilidad))
      assert(resultados.length == 5)
      assert(resultados.exists(r => r.gananciaTotal == 360 && r.probabilidad == 0.03))
      assert(resultados.exists(r => r.gananciaTotal == 10 && r.probabilidad == 0.22))
      assert(resultados.exists(r => r.gananciaTotal == 30 && r.probabilidad == 0.27))
      assert(resultados.exists(r => r.gananciaTotal == 20 && r.probabilidad == 0.22))
      assert(resultados.exists(r => r.gananciaTotal == 0 && r.probabilidad == 0.27))
    }
  }

}
