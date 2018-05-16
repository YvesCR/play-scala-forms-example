package models

/**
 * Presentation object used for displaying data in a template.
 *
 * Note that it's a good practice to keep the presentation DTO,
 * which are used for reads, distinct from the form processing DTO,
 * which are used for writes.
 */
case class Widget(field_name: String, shape: String, height: Int, width: Int){

      var shape_mod = shape

      def happyday() = println("I am happy!!")

      def calculate_area() = height * width

      def mod_shape() = {
        if(shape == "rond"){shape_mod = "et puis quoi encore: carre!!"
        } else {shape_mod = "carre"}
      }
}
