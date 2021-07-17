class CaraOCruz(val nombre : String)
  extends Juego {
  val sucesoCara: Suceso = Suceso("Cara")
  val sucesoCruz: Suceso = Suceso("Cruz")
  val sucesos : List[Suceso] = List(sucesoCara, sucesoCruz)

  val distribucion: Distribucion = DistribucionFactory.equiprobable(sucesos)

  val jugadaCara = new Jugada(List(sucesoCara), { _*2 }, { x => 0 })
  val jugadaCruz = new Jugada(List(sucesoCruz), { _*2 }, { x => 0 })
}
