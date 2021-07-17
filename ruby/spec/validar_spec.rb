require_relative '../lib/estudiante_restricciones'

describe Estudiante do
  describe "#Validaciones de objetos" do

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

    describe "Validaciones simples" do
      it "validacion Curso: no falla" do
        @carlos.grade.level = 3
        expect { @carlos.grade.validate! }.not_to raise_exception
      end
      it "validacion Curso: falla" do
        @carlos.grade.level = "3"
        expect { @carlos.grade.validate! }.to raise_exception Exception
      end
    end
    describe "Validaciones complejos" do
      it "validacion carlos: no falla" do
        @carlos.grade.level = 3
        expect { @carlos.validate! }.not_to raise_exception
      end
      it "validacion carlos: falla" do
        @carlos.grade.level = "3"
        expect { @carlos.validate! }.to raise_exception Exception
      end
    end
    describe "Validaciones complejos multiples" do
      it "validacion jorge: no falla" do
        @jorge.grades.first.level = 3
        expect { @jorge.validate! }.not_to raise_exception
      end
      it "validacion jorge: falla" do
        @jorge.grades.first.level = "3"
        expect { @jorge.validate! }.to raise_exception Exception
      end
    end

    describe "Save! Validaciones simples y complejas" do
      it "save! Curso: no falla" do
        @carlos.grade.level = 3
        expect { @carlos.save! }.not_to raise_exception
      end
      it "save! Curso: falla" do
        @carlos.grade.level = "3"
        expect { @carlos.save! }.to raise_exception Exception
      end
    end
    describe "Save! Validaciones complejos multiples" do
      it "save! jorge: no falla" do
        @jorge.grades.first.level = 3
        expect { @jorge.save! }.not_to raise_exception
      end
      it "save! jorge: falla" do
        @jorge.grades.first.level = "3"
        expect { @jorge.save! }.to raise_exception Exception
      end
    end
    describe "Restricciones" do
      it "restriccion no_blank true: no falla" do
        @carlos.grade.code = "K3011"
        expect { @carlos.save! }.not_to raise_exception
      end
      it "restriccion no_blank true: falla" do
        @carlos.grade.code = ""
        # @carlos.grade.save!
        expect { @carlos.save! }.to raise_exception Exception
      end

      it "restriccion from 2: no falla" do
        @carlos.grade.level = 3
        expect { @carlos.save! }.not_to raise_exception
      end
      it "restriccion from 2: falla" do
        @carlos.grade.level = 1
        # @carlos.grade.save!
        expect { @carlos.save! }.to raise_exception Exception
      end

      it "restriccion to 5: no falla" do
        @carlos.grade.code = "K3011"
        expect { @carlos.grade.save! }.not_to raise_exception
      end
      it "restriccion to 5: falla" do
        @carlos.grade.code = ""
        # @carlos.grade.save!
        expect { @carlos.grade.save! }.to raise_exception Exception
      end

      it "restriccion level validate es igual a 3: no falla" do
        @carlos.grade.level = 3
        expect{@carlos.save!}.not_to raise_exception
      end
      it "restriccion level validate es igual a 3: falla" do
        @carlos.grade.level = 4
        # @carlos.save!
        expect{@carlos.save!}.to raise_exception Exception
      end

      it "por defecto full_name" do
        juan = Estudiante.new
        expect(juan.full_name).to eq "Señor X"
      end
      it "por defecto grade" do
        juan = Estudiante.new
        expect(juan.grade).not_to eq nil
        # puts juan.grade
      end
    end
  end
end

