require_relative '../lib/persistible'

class Curso
  include Persistible

  has_one String, :named=>:code
  has_one Numeric, :named=>:level

end

# No existe una tabla para las Personas, porque es un módulo.
module Persona
  include Persistible
  has_one String, :named=>:full_name
  has_one Numeric, :named=>:age
end

# Hay una tabla para los Alumnos con los campos id, nombre y nota.
class Estudiante
  include Persistible, Persona
  has_one Curso, :named=>:grade
end

# Hay una tabla para los Ayudantes con id, nombre, nota y tipo
class Ayudante < Estudiante
  has_one String, :named=>:type
  has_one String, :named=>:nickname
end