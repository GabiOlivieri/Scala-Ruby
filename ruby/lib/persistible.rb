require_relative '../lib/persistible-class'
require_relative '../lib/persistible-inst'

module Persistible
  include PersistibleInstance

  def self.included(base)
    base.extend(PersistibleClass)
    base.initialize_class_table
    base.initialize_rela_table_hash
  end
end
