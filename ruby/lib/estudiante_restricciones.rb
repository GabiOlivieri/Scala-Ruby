require_relative '../lib/persistible'

class Curso
  include Persistible

  has_one String, :named=>:code , :no_blank=>true
  has_one Numeric, :named=>:level, :from=>2
end

class Estudiante
  include Persistible

  has_one String, :named=>:full_name, :default=>"Señor X"
  has_one Curso, :named=>:grade , :validate=>proc { level == 3 }, :default=>Curso.new
end

class EstudianteAvanzado
  include Persistible

  has_one String, :named=>:full_name
  has_many Curso, :named=>:grades
end