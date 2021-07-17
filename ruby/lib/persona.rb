require_relative '../lib/persistible'

class Persona
  include Persistible

  has_one String, :named=>:first_name
  has_one String, :named=>:last_name
  has_one Numeric, :named=>:age
  has_one Boolean, :named=>:has_car

  attr_accessor :peso

end