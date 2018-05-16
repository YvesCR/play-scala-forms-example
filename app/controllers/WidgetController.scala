package controllers

import javax.inject.Inject

import models.Widget
import play.api.data._
import play.api.i18n._
import play.api.mvc._


import org.fluentd.logger.scala.FluentLoggerFactory
import scala.collection.mutable.HashMap

/**
 * The classic WidgetController using MessagesAbstractController.
 *
 * Instead of MessagesAbstractController, you can use the I18nSupport trait,
 * which provides implicits that create a Messages instances from
 * a request using implicit conversion.
 *
 * See https://www.playframework.com/documentation/2.6.x/ScalaForms#passing-messagesprovider-to-form-helpers
 * for details.
 */
class WidgetController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  import WidgetForm._

    val LOG = FluentLoggerFactory.getLogger("fluentd.test")

  private val widgets = scala.collection.mutable.ArrayBuffer(
    Widget("Field 1", "Shape 1", 123, 223)
  )

  // The URL to the widget.  You can call this directly from the template, but it
  // can be more convenient to leave the template commpletely stateless i.e. all
  // of the "WidgetController" references are inside the .scala file.
  private val postUrl = routes.WidgetController.createWidget()

  def index = Action {
    Ok(views.html.index())
  }

  def listWidgets = Action { implicit request: MessagesRequest[AnyContent] =>
    // Pass an unpopulated form to the template
    Ok(views.html.listWidgets(widgets, form, postUrl))
  }

  // This will be the action that handles our form post
  def createWidget = Action { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[Data] =>
      // This is the bad case, where the form had validation errors.
      // Let's show the user the form again, with the errors highlighted.
      // Note how we pass the form with errors to the template.
      BadRequest(views.html.listWidgets(widgets, formWithErrors, postUrl))
    }

    val successFunction = { data: Data =>
      // This is the good case, where the form was successfully parsed as a Data.
      val widget = Widget(field_name = data.field_name, shape = data.shape, width = data.width, height = data.height)
      widget.happyday()
      widget.mod_shape()
      widgets.append(widget)

      val data2 = new HashMap[String, String]();
          data2.put("from", data.field_name);
          data2.put("to", "userB");
          LOG.log("follow", data2);

      Redirect(routes.WidgetController.listWidgets()).flashing("info" -> "Widget added!")
    }

    val formValidationResult = form.bindFromRequest
    formValidationResult.fold(errorFunction, successFunction)
  }

}
