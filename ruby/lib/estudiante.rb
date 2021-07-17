require_relative '../lib/persistible'
class Curso
  include Persistible

  has_one String, :named=>:code
  has_one Numeric, :named=>:level
end

class Estudiante
  include Persistible

  has_one String, :named=>:full_name
  has_one Curso, :named=>:grade
end

class EstudianteAvanzado
  include Persistible

  has_one String, :named=>:full_name
  has_many Curso, :named=>:grades
end
