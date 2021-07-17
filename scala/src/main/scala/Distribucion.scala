object DistribucionFactory{
     def eventoSeguro(sucesos: List[Suceso]): Distribucion = {
      new Distribucion(sucesos.map(s => s -> 1.0).take(1).toMap)
    }

    def equiprobable(sucesos: List[Suceso]): Distribucion = {
      val probabilidad : Double = 1.0 / sucesos.length
      new Distribucion(sucesos.map(s => s -> probabilidad).toMap)
    }

    def valoresPonderados(sucesosPeso: List[SucesoPeso]): Distribucion = {
      val total : Double = sucesosPeso.map(s => s.peso).sum
      new Distribucion(sucesosPeso.map(s => s.suceso -> s.peso/total).toMap)
    }
}

class SucesoPeso(val suceso: Suceso, val peso : Int)

class Distribucion(_sucesosProbables : Map[Suceso, Double]) {

  def sucesos : List[Suceso] = _sucesosProbables.keys.toList

  def getProbabilidad(suceso: Suceso) : Option[Double] = {
    _sucesosProbables.get(suceso)
  }
}