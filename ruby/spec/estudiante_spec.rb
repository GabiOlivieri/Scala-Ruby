require_relative '../lib/estudiante'

describe Estudiante do
  describe "#Persistencia relaciones entre objetos" do

    before do
      @carlos = Estudiante.new
      @carlos.full_name = "Carlos Santana"
      @carlos.grade = Curso.new
      @carlos.grade.code = "K3011"
      @carlos.grade.level = 3

      @jorge = EstudianteAvanzado.new
      @jorge.full_name = "Jorge Avanzado"
      @jorge.grades.push(Curso.new)
      @jorge.grades.last.code = "K3111"
      @jorge.grades.last.level = 3
      @jorge.grades.push(Curso.new)
      @jorge.grades.last.code = "K2011"
      @jorge.grades.last.level = 2

      @dylan = EstudianteAvanzado.new
      @dylan.full_name = "Dylan Informatico"
    end

    after do
      Estudiante.table.clear
      EstudianteAvanzado.table.clear
      Curso.table.clear
      EstudianteAvanzado.rela_tables[:EstudianteAvanzado_to_grades].clear
    end

    describe "Composicion de un unico objeto: Student y Grade" do
      it "save carlos" do
        @carlos.save!

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
        expect(!Estudiante.table.entries.any? { |x| x[:id] == id_carlos })
        # expect(!Curso.table.entries.any? { |x| x[:id] == id_curso })
      end
    end
    describe "Composicion de objetos multiples: Student y Grades" do
      it "save jorge" do
        @jorge.save!

        expect(@jorge.id != nil)
        expect(@jorge.grades.count).to eq 2
        expect(@jorge.grades[0].id != nil)
        expect(@jorge.grades[1].id != nil)
        expect(@jorge.grades_ids != nil)
        # puts @jorge.hash
      end
      it "save dylan: sin cursos" do
        @dylan.save!

        expect(@dylan.id != nil)
        expect(@dylan.grades.count).to eq 0
        expect(@dylan.grades_ids == "")
        # puts @dylan.hash
      end
      it "refresh jorge" do
        @jorge.save!

        @jorge.grades.last.level = 5
        # puts @jorge.grades.last.level
        # puts @jorge.grades.last.hash
        @jorge.refresh!

        expect(@jorge.id != nil)
        expect(@jorge.grades.count).to eq 2
        expect(@jorge.grades[0].id != nil)
        expect(@jorge.grades[1].id != nil)
        expect(@jorge.grades_ids != nil)
        expect(@jorge.grades.last.level).to eq 2
        # puts @jorge.grades.last.hash
      end
      it "forget jorge: se borra jorge y sus registros en la tabla de relaciones" do
        @jorge.save!
        expect(@jorge.id != nil)
        expect(@jorge.grades.last.id != nil)
        expect(@jorge.grades.count).to eq 2

        id_jorge = @jorge.id
        id_ultimo_curso = @jorge.grades.last.id
        # puts id_ultimo_curso
        @jorge.forget!

        expect(@jorge.id == nil)
        expect(!EstudianteAvanzado.table.entries.any? { |x| x[:id] == id_jorge })
        rela_table = EstudianteAvanzado.rela_tables[:EstudianteAvanzado_to_grades]
        expect(rela_table.entries.any? { |x| x[:fk_grades] == id_ultimo_curso }).to be false
      end
    end
  end
end

