require 'tadb'
require_relative '../lib/boolean'

module PersistibleClass
  attr_accessor :table
  attr_accessor :rela_tables
  attr_accessor :properties

  def inherited(subclass)
    subclass.initialize_class_table
    subclass.initialize_rela_table_hash
  end

  def has_one(type, hash)
    add_properties(type, hash, false)
  end

  def has_many(type, hash)
    add_properties(type, hash, true)
  end

  def all_instances
    descendant_instance = self.descendants.map do |dec|
      dec.all_instances
    end

    current_instance = self.table != nil ? hash_to_instance(self.table.entries) : Array.new

    if descendant_instance.any?
      current_instance = (current_instance.concat descendant_instance).flatten
      current_instance = current_instance.uniq { |c| c.id }
    end

    current_instance
  end

  def descendants
    ObjectSpace.each_object(Class).select { |c| c < self }
  end

  def find_by_id(id)
    find_by(:id, id).first
  end

  def initialize_properties
    if self.properties == nil
      self.properties = Array.new
    end
  end

  def initialize_class_table
    if self.table == nil
      self.table = TADB::DB.table(self.to_s)
    end
  end

  def initialize_rela_table_hash
    if self.rela_tables == nil
      self.rela_tables = Hash.new
    end
  end

  def is_persistible(type)
    type.include? Persistible
  end

  private

  def add_properties(type, hash, is_many)
    self.initialize_properties
    self.add_inheritance_props
    no_blank_value = hash[:no_blank] != nil ? hash[:no_blank] : false
    self.add_property(type, hash[:named], is_many, no_blank_value, hash[:from], hash[:to], hash[:validate], hash[:default])
    self.add_find_by_method(hash[:named])
  end

  def add_inheritance_props
    ancestors = self.ancestors.select { |a| a.include? Persistible }
    if ancestors.count > 1
      ancestors[1].properties.each do |prop|
        self.add_property(prop[:type], prop[:named], prop[:is_many], prop[:no_blank], prop[:from], prop[:to], prop[:block_validate], prop[:default])
        self.add_find_by_method(prop[:named])
      end
    end
  end

  def add_property(type, named, is_many, no_blank, from, to, block_validate, default)
    if type == Boolean || type == String || type == Numeric || is_persistible(type)
      new_property = { :named => named, :type => type, :is_many => is_many, :no_blank => no_blank, :from => from, :to => to, :block_validate => block_validate, :default => default }
      repeated_index = self.properties.index { |prop| prop[:named] == named }
      if repeated_index != nil
        self.properties.delete_at(repeated_index)
      end
      self.properties.push(new_property)
    else
      raise 'El tipo no es reconocido: ' + type.to_s
    end
  end

  def add_find_by_method(property_name)
    self.singleton_class.define_method(("find_by_" + property_name.to_s).to_sym) do |valueToFind|
      find_by(property_name, valueToFind)
    end
  end

  def find_by(key, value)
    found_values = Array.new
    all_instances.each do |inst|
      if inst.exist_property(key) || key == :id
        if inst.send(key) == value
          found_values.push(inst)
        end
      else
        raise "No todas las instancias tienen la propiedad: " + key.to_s
      end
    end
    found_values
  end

  def hash_to_instance (hash_entries)
    hash_entries.map do |p|
      instance = self.new
      p.each { |entry| instance.send((entry[0].to_s + '=').to_sym, entry[1]) }
      instance
    end
  end
end