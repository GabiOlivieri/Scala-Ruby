require 'tadb'
module PersistibleInstance
  attr_accessor :hash
  attr_accessor :id

  def initialize()
    self.hash = Hash.new

    self.class.properties.each do |p|
      self.add_instance_property(p[:type], p[:named], p[:is_many], p[:default])
    end
  end

  def method_missing(selector, *args, &block)
    selector = (selector.to_s.delete '=').to_sym

    if !exist_property(selector)
      super
    else
      if args.first != nil
        self.hash[selector] = args.first
      else
        self.hash[selector]
      end
    end
  end

  def respond_to_missing(selector,priv = false)
    exist_property(selector)
  end

  def exist_property(selector)
    self.class.properties.any? { |p| p[:named] == selector } || (self.hash.include? selector)
  end

  def save!
    self.validate!

    if self.id != nil
      self.class.table.delete(self.id)
    end
    complex_properties_ids = self.save_persistible_properties
    self.id = self.class.table.insert(hash)
    save_complex_rela_props(complex_properties_ids)
    self.id
  end

  def refresh!
    if self.id != nil
      hash_guardado = self.class.table.entries.select { |x| x[:id] == self.id }.first
      self.hash = hash_guardado
      get_persistible_properties.each do |prop|
        if prop[:is_many]
          objetos_persistible = self.send(prop[:named])
          objetos_persistible.each { |obj| obj.refresh! }
        else
          self.send(prop[:named]).refresh!
        end
      end
    else
      raise 'Este objeto no tiene id'
    end
  end

  def forget!
    if self.id != nil
      self.get_persistible_properties.each do |prop|
        if prop[:is_many]
          rela_table = self.class.rela_tables[(self.class.to_s + "_to_" + prop[:named].to_s).to_sym]
          fk_name_table = ("fk_" + self.class.to_s).to_sym
          rela_table.entries.each do |x|
            if x[fk_name_table] == self.id
              rela_table.delete(x[:id])
              self.send((prop[:named].to_s + "_ids=").to_sym, "")
            end
          end
        end
      end
      self.class.table.delete(self.id)
      self.id = nil
    else
      raise 'Este objeto no tiene id'
    end
  end

  def validate_block(&bloque)
    bloque.call
  end

  def validate!
    self.class.properties.each do |prop|
      value = send(prop[:named])

      if prop[:no_blank] && (value == nil || value == "")
        raise "La propiedad " + prop[:named].to_s + " no puede ser nula o estar vacia"
      end
      if prop[:from] != nil && value < prop[:from]
        raise "La propiedad " + prop[:named].to_s + " no puede ser menor que " + prop[:from]
      end
      if prop[:to] != nil && value > prop[:to]
        raise "La propiedad " + prop[:named].to_s + " no puede ser mayor que " + prop[:to]
      end

      # Validacion del bloque
      if prop[:block_validate] != nil
        unless value.instance_eval &prop[:block_validate]
          raise "La propiedad " + prop[:named].to_s + " no cumple la condición del bloque"
        end
        # value.instance_eval  do
        #   puts "entra a instance eval" + value.to_s
        #   condition = prop[:block_validate]
        #   unless condition.call
        #     raise "La propiedad " + prop[:named].to_s + " no cumple la condición del bloque"
        #   end
        # end
      end

      if self.class.is_persistible prop[:type]
        if prop[:is_many]
          value.each { |obj| obj.validate! }
        else
          value.validate!
        end
      else
        is_boolean = ((value.is_a? TrueClass) || (value.is_a? FalseClass)) && prop[:type] == Boolean
        unless ((value.is_a? prop[:type]) || is_boolean)
          raise "La propiedad " + prop[:named].to_s + " no es de tipo " + prop[:type].to_s
        end
      end
    end
  end

  private

  def save_complex_rela_props(complex_properties)
    complex_properties.each do |prop|
      rela_table = self.class.rela_tables[(self.class.to_s + "_to_" + prop[:rela_name].to_s).to_sym]
      rela_ids = prop[:values].map do |id|
        fk_name_table = ("fk_" + self.class.to_s).to_sym
        fk_name_obj = ("fk_" + prop[:rela_name].to_s).to_sym
        rela_table.insert({ fk_name_table => self.id, fk_name_obj => id })
      end

      self.send((prop[:rela_name].to_s + "_ids=").to_sym, rela_ids.join(","))
    end
  end

  def save_persistible_properties
    complex_properties_ids = Array.new
    self.get_persistible_properties.each do |prop|
      if prop[:is_many]
        objetos_persistible = self.send(prop[:named])
        complex_properties_ids.push({ :rela_name => prop[:named], :values => objetos_persistible.map { |obj| obj.save! } })
      else
        id = self.send(prop[:named]).save!
        self.send((prop[:named].to_s + "_id=").to_sym, id)
      end
    end
    complex_properties_ids
  end

  def get_persistible_properties
    self.class.properties.select { |p| self.class.is_persistible p[:type] }
  end

  def add_instance_property(type, name, is_many, default)
    if self.class.is_persistible type
      self.add_property_accessor(name)
      if is_many
        create_rela_table(name)
        initialize_array_property(name)
        self.hash[(name.to_s + "_ids").to_sym] = ""
      else
        self.hash[(name.to_s + "_id").to_sym] = ""
      end

      if default != nil
        self.send((name.to_s + "=").to_sym, default)
      end
    else
      self.hash[name] = default != nil ? default : type.default_value
    end
  end

  def initialize_array_property(name)
    self.send((name.to_s + "=").to_sym, Array.new)
  end

  def create_rela_table(name)
    self.class.initialize_rela_table_hash
    rela_table_name = (self.class.to_s + "_to_" + name.to_s).to_sym
    self.class.rela_tables[rela_table_name] = TADB::DB.table(rela_table_name)
  end

  def add_property_accessor(name)
    self.class.class_eval do
      attr_accessor name
    end
  end

end