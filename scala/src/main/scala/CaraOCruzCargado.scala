class CaraOCruzCargado(_nombre: String)
  extends CaraOCruz(_nombre) {
  var pesoCara = new SucesoPeso(sucesoCara, 4)
  var pesoCruz = new SucesoPeso(sucesoCruz, 3)
  override val distribucion: Distribucion = DistribucionFactory.valoresPonderados(List(pesoCara, pesoCruz))
}