class Ruleta(val nombre : String) extends Juego {
  val suceso0: Suceso = Suceso("0")
  val suceso1: Suceso = Suceso("1")
  val suceso2: Suceso = Suceso("2")
  val suceso3: Suceso = Suceso("3")
  val suceso4: Suceso = Suceso("4")
  val suceso5: Suceso = Suceso("5")
  val suceso6: Suceso = Suceso("6")
  val suceso7: Suceso = Suceso("7")
  val suceso8: Suceso = Suceso("8")
  val suceso9: Suceso = Suceso("9")
  val suceso10: Suceso = Suceso("10")
  val suceso11: Suceso = Suceso("11")
  val suceso12: Suceso = Suceso("12")
  val suceso13: Suceso = Suceso("13")
  val suceso14: Suceso = Suceso("14")
  val suceso15: Suceso = Suceso("15")
  val suceso16: Suceso = Suceso("16")
  val suceso17: Suceso = Suceso("17")
  val suceso18: Suceso = Suceso("18")
  val suceso19: Suceso = Suceso("19")
  val suceso20: Suceso = Suceso("20")
  val suceso21: Suceso = Suceso("21")
  val suceso22: Suceso = Suceso("22")
  val suceso23: Suceso = Suceso("23")
  val suceso24: Suceso = Suceso("24")
  val suceso25: Suceso = Suceso("25")
  val suceso26: Suceso = Suceso("26")
  val suceso27: Suceso = Suceso("27")
  val suceso28: Suceso = Suceso("28")
  val suceso29: Suceso = Suceso("29")
  val suceso30: Suceso = Suceso("30")
  val suceso31: Suceso = Suceso("31")
  val suceso32: Suceso = Suceso("32")
  val suceso33: Suceso = Suceso("33")
  val suceso34: Suceso = Suceso("34")
  val suceso35: Suceso = Suceso("35")
  val suceso36: Suceso = Suceso("36")

  val sucesos : List[Suceso] = List(suceso0, suceso1, suceso2, suceso3, suceso4,
    suceso5, suceso6, suceso7, suceso8, suceso9, suceso10, suceso11, suceso12,
    suceso13, suceso14, suceso15, suceso16, suceso17, suceso18, suceso19,
    suceso20, suceso21, suceso22, suceso23, suceso24, suceso25, suceso26,
    suceso27, suceso28, suceso29, suceso30, suceso31, suceso32, suceso33,
    suceso34, suceso35, suceso36)

  val distribucion: Distribucion = DistribucionFactory.equiprobable(sucesos)

  val jugadaRojo = new Jugada(List(suceso1, suceso3, suceso5, suceso7,suceso9,
    suceso12, suceso14, suceso16, suceso18, suceso19, suceso21, suceso23,
    suceso25, suceso27, suceso30, suceso32, suceso34, suceso36),
    { _*2 }, { x => 0 })
  val jugadaNegro = new Jugada(List(suceso2, suceso4, suceso6, suceso8,
    suceso10, suceso11, suceso13, suceso15, suceso17, suceso20, suceso22,
    suceso24, suceso26, suceso28, suceso29, suceso31, suceso33, suceso35)
    , { _*2 }, { x => 0 })

  val jugada0 = new Jugada(List(suceso0), { _*36 }, { x => 0 })
  val jugada1 = new Jugada(List(suceso1), { _*36 }, { x => 0 })
  val jugada2 = new Jugada(List(suceso2), { _*36 }, { x => 0 })
  val jugada3 = new Jugada(List(suceso3), { _*36 }, { x => 0 })
  val jugada4 = new Jugada(List(suceso4), { _*36 }, { x => 0 })
  val jugada5 = new Jugada(List(suceso5), { _*36 }, { x => 0 })
  val jugada6 = new Jugada(List(suceso6), { _*36 }, { x => 0 })
  val jugada7 = new Jugada(List(suceso7), { _*36 }, { x => 0 })
  val jugada8 = new Jugada(List(suceso8), { _*36 }, { x => 0 })
  val jugada9 = new Jugada(List(suceso9), { _*36 }, { x => 0 })
  val jugada10 = new Jugada(List(suceso10), { _*36 }, { x => 0 })
  val jugada11 = new Jugada(List(suceso11), { _*36 }, { x => 0 })
  val jugada12 = new Jugada(List(suceso12), { _*36 }, { x => 0 })
  val jugada13 = new Jugada(List(suceso13), { _*36 }, { x => 0 })
  val jugada14 = new Jugada(List(suceso14), { _*36 }, { x => 0 })
  val jugada15 = new Jugada(List(suceso15), { _*36 }, { x => 0 })
  val jugada16 = new Jugada(List(suceso16), { _*36 }, { x => 0 })
  val jugada17 = new Jugada(List(suceso17), { _*36 }, { x => 0 })
  val jugada18 = new Jugada(List(suceso18), { _*36 }, { x => 0 })
  val jugada19 = new Jugada(List(suceso19), { _*36 }, { x => 0 })
  val jugada20 = new Jugada(List(suceso20), { _*36 }, { x => 0 })
  val jugada21 = new Jugada(List(suceso21), { _*36 }, { x => 0 })
  val jugada22 = new Jugada(List(suceso22), { _*36 }, { x => 0 })
  val jugada23 = new Jugada(List(suceso23), { _*36 }, { x => 0 })
  val jugada24 = new Jugada(List(suceso24), { _*36 }, { x => 0 })
  val jugada25 = new Jugada(List(suceso25), { _*36 }, { x => 0 })
  val jugada26 = new Jugada(List(suceso26), { _*36 }, { x => 0 })
  val jugada27 = new Jugada(List(suceso27), { _*36 }, { x => 0 })
  val jugada28 = new Jugada(List(suceso28), { _*36 }, { x => 0 })
  val jugada29 = new Jugada(List(suceso29), { _*36 }, { x => 0 })
  val jugada30 = new Jugada(List(suceso30), { _*36 }, { x => 0 })
  val jugada31 = new Jugada(List(suceso31), { _*36 }, { x => 0 })
  val jugada32 = new Jugada(List(suceso32), { _*36 }, { x => 0 })
  val jugada33 = new Jugada(List(suceso33), { _*36 }, { x => 0 })
  val jugada34 = new Jugada(List(suceso34), { _*36 }, { x => 0 })
  val jugada35 = new Jugada(List(suceso35), { _*36 }, { x => 0 })
  val jugada36 = new Jugada(List(suceso36), { _*36 }, { x => 0 })

  val jugadaPar = new Jugada(List(suceso2, suceso4, suceso6, suceso8, suceso10,
    suceso12, suceso14, suceso16, suceso18, suceso20, suceso22, suceso24,
    suceso26, suceso28, suceso30, suceso32, suceso34, suceso36),
    { _*2 }, { x => 0 })
  val jugadaImpar = new Jugada(List(suceso1, suceso3, suceso5, suceso7, suceso9,
    suceso11, suceso13, suceso15, suceso17, suceso19, suceso21, suceso23,
    suceso25, suceso27, suceso29, suceso31, suceso33, suceso35),
    { _*2 }, { x => 0 })

  val jugadaPrimeraDocena = new Jugada(List(suceso1, suceso2, suceso3, suceso4,
    suceso5, suceso6, suceso7, suceso8, suceso9, suceso10, suceso11, suceso12),
    { _*3 }, { x => 0 })
  val jugadaSegundaDocena = new Jugada(List(suceso13, suceso14, suceso15,
    suceso16, suceso17, suceso18, suceso19, suceso20, suceso21, suceso22,
    suceso23, suceso24), { _*3 }, { x => 0 })
  val jugadaTerceraDocena = new Jugada(List(suceso25, suceso26, suceso27,
    suceso28, suceso29, suceso30, suceso31, suceso32, suceso33, suceso34,
    suceso35, suceso36), { _*3 }, { x => 0 })
}

