module DefaultType
  def default_value
    String.new
  end
end

class String
  extend DefaultType
end

class Boolean
  extend DefaultType

  self.singleton_class.class_eval do
    def default_value
      false
    end
  end
end

class Numeric
  extend DefaultType

  self.singleton_class.class_eval do
    def default_value
      0
    end
  end
end

# puts String.default_value
# puts Boolean.default_value
# puts Numeric.default_value
