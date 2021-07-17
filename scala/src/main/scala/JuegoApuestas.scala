class JuegoApuestas(val juego : Juego, val apuestas : List[Apuesta]){
  def totalApuesta() : Int = {
    apuestas.map(a => a.monto).sum
  }

  def sePuedeApostar(monto : Int): Boolean ={
    totalApuesta <= monto
  }

  def apostar(): List[NodoResultado] ={
    juego.jugar(apuestas).map(r => new NodoResultado(r))
  }
}