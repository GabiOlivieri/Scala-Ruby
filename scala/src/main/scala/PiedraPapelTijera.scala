class PiedraPapelTijera (val nombre : String) extends Juego {
  val sucesoPiedra: Suceso = Suceso("Piedra")
  val sucesoPapel: Suceso = Suceso("Papel")
  val sucesoTijera: Suceso = Suceso("Tijera")

  val sucesos : List[Suceso] = List(sucesoPiedra, sucesoTijera,sucesoTijera)

  val pesoPapel = new SucesoPeso(sucesoPapel, 25)
  val pesoPiedra = new SucesoPeso(sucesoPiedra, 35)
  val pesoTijera = new SucesoPeso(sucesoTijera,  40)

  val sucesosConPeso : List[SucesoPeso] = List(pesoPiedra, pesoPapel, pesoTijera)

  val distribucion: Distribucion = DistribucionFactory.valoresPonderados(sucesosConPeso)


  val jugadaPiedra = new Jugada(List(sucesoTijera), { _*2 }, { x => 0 }, { _*1 } ,List(sucesoPiedra))

  val jugadaPapel = new Jugada(List(sucesoPiedra), { _*2 }, { x => 0 }, { _*1 } ,List(sucesoPapel))

  val jugadaTijera = new Jugada(List(sucesoPapel), { _*2 }, { x => 0}, { _*1 },List(sucesoTijera))


}
