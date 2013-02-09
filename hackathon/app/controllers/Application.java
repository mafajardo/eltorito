package controllers;

//import play.*;
//import play.mvc.*;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import models.*;
import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {

  public static Result index() {
  	List<GiltSale> sales = new ArrayList<GiltSale>();
    return ok(index.render(sales));
  }
  
  
}