require_relative '../lib/herencia'

describe Estudiante do
  describe "#Persistencia de herencia de mixines y clases" do

    before do
      @carlos = Ayudante.new
      @carlos.full_name = "Carlos Santana"
      @carlos.age = 22
      @carlos.type = "Permanente"
      @carlos.nickname = "Coco"
      @carlos.grade = Curso.new
      @carlos.grade.code = "K3011"
      @carlos.grade.level = 3

      @carlos2 = Estudiante.new
      @carlos2.full_name = "Carlos Santana"
      @carlos2.age = 23
      @carlos2.grade = Curso.new
      @carlos2.grade.code = "K4111"
      @carlos2.grade.level = 4

      @dylan = Ayudante.new
      @dylan.full_name = "Dylan Informatico"
      @dylan.age = 24
      @dylan.type = "Temporal"
      @dylan.nickname = "Dylin"
      @dylan.grade = Curso.new
      @dylan.grade.code = "K4111"
      @dylan.grade.level = 4

      @jorge = Estudiante.new
      @jorge.full_name = "Jorge Cansado"
      @jorge.age = 22
      @jorge.grade = Curso.new
      @jorge.grade.code = "K4011"
      @jorge.grade.level = 4
    end

    after do
      Estudiante.table.clear
      Ayudante.table.clear
      Curso.table.clear
    end

    describe "Modificar propiedades" do
      it "modificar el first_name heredado del modulo Persona" do
        expect(@carlos.full_name).to eq "Carlos Santana"
        # puts @carlos.hash
      end
      it "modificar el nickname del Ayudante" do
        expect(@carlos.nickname).to eq "Coco"
        # puts @carlos.hash
      end
      it "modificar el nivel del curso heredado del Estudiante" do
        expect(@carlos.grade.level).to eq 3
        # puts @carlos.hash
      end
    end

    describe "Herencia de clases y mixines: Ayudante, Estudiante y Persona" do
      it "save carlos" do
        @carlos.save!

        puts @carlos.hash

        expect(@carlos.id != nil)
        expect(@carlos.grade.id != nil)
        expect(@carlos.grade_id).to eq @carlos.grade.id
      end
      it "refresh carlos: vuelve a tener el codigo de curso K3011" do
        @carlos.save!
        @carlos.grade.code = "K3111"
        @carlos.refresh!

        expect(@carlos.grade.code).to eq "K3011"
      end
      it "forget carlos y su curso" do
        @carlos.save!
        expect(@carlos.id != nil)
        expect(@carlos.grade.id != nil)
        id_carlos = @carlos.id
        id_curso = @carlos.grade.id

        @carlos.forget!

        expect(@carlos.id == nil)
        # expect(@carlos.grade.id == nil)
        expect(!Ayudante.table.entries.any? { |x| x[:id] == id_carlos })
        # expect(!Curso.table.entries.any? { |x| x[:id] == id_curso })
      end
    end

    describe "Operaciones de clases" do
      it "all instance: carlos y dylan que son Ayudantes" do
        @carlos.save!
        @dylan.save!

        # puts Ayudante.table.entries

        expect(Ayudante.all_instances.first.hash).to eq @carlos.hash
        expect(Ayudante.all_instances[1].hash).to eq @dylan.hash
      end
      it "all instance Estudiante: carlos y dylan que son Ayudantes y jorge es Estudiantes" do
        @carlos.save!
        @dylan.save!
        @jorge.save!

        expect(Estudiante.all_instances.count).to eq 3
      end
      it "all instance Persona: carlos y dylan que son Ayudantes y jorge es Estudiantes" do
        @carlos.save!
        @dylan.save!
        @jorge.save!

        expect(Persona.all_instances.count).to eq 3
      end
      it "find by id: carlos" do
        @carlos.save!

        expect(Persona.find_by_id(@carlos.id).hash).to eq @carlos.hash
        expect(Persona.find_by_id(@carlos.id).full_name).to eq @carlos.full_name
      end
      it "find by first_name: encuentra 2 carlos" do
        @carlos.save!
        @carlos2.save!

        expect(Persona.find_by_full_name("Carlos Santana").size).to be 2
      end
      it "find by type: encuentra 1" do
        @carlos.save!
        @carlos2.save!
        @dylan.save!
        @jorge.save!

        # puts Ayudante.all_instances

        expect(Ayudante.find_by_type("Temporal").size).to be 1
      end
      it "find by age: encuentra 2 con 22 años" do
        @carlos.save!
        @carlos2.save!
        @dylan.save!
        @jorge.save!

        expect(Persona.find_by_age(22).size).to be 2
      end
      it "find by nickname: falla porque no todos tienen la propiedad" do
        @carlos.save!
        @carlos2.save!
        @dylan.save!
        @jorge.save!
        # Persona.find_by_nickname("Coco")
        expect { Persona.find_by_nickname("Coco") }.to raise_exception Exception
      end
    end
  end
end


