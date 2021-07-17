class Apuesta(val jugada : Jugada, val monto : Int) {

  def ejecutar(sucesoResultado : Suceso): ResultadoJuego ={
    jugada.obtenerResultado(sucesoResultado, monto)
  }
}
