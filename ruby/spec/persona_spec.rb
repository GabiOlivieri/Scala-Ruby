require_relative '../lib/persona'

describe Persona do
  describe "#Persistencia de objetos simples" do

    before do
     @carlos = Persona.new
     @carlos.first_name = "Carlos"
     @carlos.last_name = "Coco"
     @carlos.age = 27
     @carlos.has_car = true

     @dylan = Persona.new
     @dylan.first_name = "Dylan"
     @dylan.last_name = "Turromantico"
     @dylan.age = 21

     @carlos_segundo = Persona.new
     @carlos_segundo.first_name = "Carlos"
     @carlos_segundo.last_name = "Segundo"
     @carlos_segundo.age = 21
     @carlos_segundo.has_car = false

     @johny_boca_cerrada = Persona.new
     @johny_boca_cerrada.first_name = "Johny"
    end

    after do
      Persona.table.clear
    end

    describe "Modificar propiedades" do
      it "modificar el first_name" do
        expect(@carlos.first_name).to eq "Carlos"
      end

      it "modificar el last_name" do
        expect(@carlos.last_name).to eq "Coco"
      end

      it "modificar el age" do
        expect(@carlos.age).to be 27
      end

      it "modificar el has_car" do
        expect(@carlos.has_car).to be true
      end

      it "por defecto el valor de has_car es falso" do
        expect(@dylan.has_car).to be false
      end
    end

    describe "Operaciones con instancias Persona" do
      it "guardar carlos" do
        @carlos.save!
        expect_hash = {:first_name=>"Carlos", :last_name=>"Coco", :age=>27, :has_car=>true}
        expect(@carlos.hash).to eq expect_hash
        expect(@carlos.id != nil)
      end
      it "guardar johny boca cerrada: se guardan valores por defecto" do
        @johny_boca_cerrada.save!
        expect_hash = {:first_name=>"Johny", :last_name=>"", :age=>0, :has_car=>false}
        expect(@johny_boca_cerrada.hash).to eq expect_hash
        expect(@johny_boca_cerrada.id != nil)
      end
      it "refresh carlos" do
        @carlos.save!
        @carlos.first_name = "Jose"
        expect(@carlos.first_name).to eq "Jose"
        @carlos.refresh!
        expect(@carlos.first_name).to eq "Carlos"
      end
      it "forget carlos" do
        @carlos.save!
        expect_hash = {:first_name=>"Carlos", :last_name=>"Coco", :age=>27, :has_car=>true}
        expect(@carlos.hash).to eq expect_hash
        expect(@carlos.id != nil)
        id_carlos = @carlos.id
        # puts id_carlos
        # puts Persona.table.entries

        @carlos.forget!

        expect(@carlos.id == nil)
        expect(!Persona.table.entries.any? { |x| x[:id] == id_carlos })
        # puts Persona.table.entries
      end
      it "solo forget carlos, sigue dylan" do
        @dylan.save!
        @carlos.save!
        expect(@carlos.id != nil)
        expect(@dylan.id != nil)

        id_carlos = @carlos.id
        id_dylan = @dylan.id
        # puts id_carlos
        # puts id_dylan
        # puts Persona.table.entries

        @carlos.forget!

        expect(@carlos.id == nil)
        expect(@dylan.id != nil)

        expect(!Persona.table.entries.any? { |x| x[:id] == id_carlos })
        expect(Persona.table.entries.any? { |x| x[:id] == id_dylan })

        # puts Persona.table.entries
      end
    end
    describe "Operaciones con la clase Persona" do
      it "all instance: carlos y dylan" do
        @carlos.save!
        @dylan.save!

        # puts Persona.table.entries

        expect(Persona.all_instances.first.hash).to eq @carlos.hash
        expect(Persona.all_instances[1].hash).to eq @dylan.hash
      end
      it "find by id: carlos" do
        @carlos.save!

        expect(Persona.find_by_id(@carlos.id).hash).to eq @carlos.hash
        expect(Persona.find_by_id(@carlos.id).first_name).to eq @carlos.first_name
      end
      it "find by first_name: encuentra 2 carlos" do
        @carlos.save!
        @carlos_segundo.save!

        # puts Persona.table.entries

        expect(Persona.find_by_first_name("Carlos").size).to be 2
      end
      it "find by age: encuentra 2 con 21 años" do
        @carlos.save!
        @dylan.save!
        @carlos_segundo.save!

        # puts Persona.table.entries

        expect(Persona.find_by_age(21).size).to be 2
      end
      it "find by has_car: encuentra 1 con auto y es carlos" do
        @carlos.save!
        @dylan.save!
        @carlos_segundo.save!

        # puts Persona.table.entries

        expect(Persona.find_by_has_car(true).size).to be 1
        expect(Persona.find_by_has_car(true).first.hash).to eq @carlos.hash
      end
    end
  end
end
