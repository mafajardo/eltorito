/* ************************************************************************** */
/* Libs                                                                       */
/* ************************************************************************** */

//+ Jonas Raoni Soares Silva
//@ http://jsfromhell.com/array/shuffle [v1.0]
function shuffle(o){ //v1.0
    for(var j, x, i = o.length; i; j = parseInt(Math.random() * i), x = o[--i], o[i] = o[j], o[j] = x);
    return o;
};

/* ************************************************************************** */
/* W|Wear                                                                     */
/* ************************************************************************** */

/* Masonry plugin */
$(function(){
  var $container = $('#container');

  $container.imagesLoaded( function(){
    $container.masonry({
      itemSelector : '.box'
    });
  });
});

/* Item hover listener */
function item_hover_listener(){
  $(".box").on("mouseenter",function(){
    var slider = $(".slider",$(this));
    slider.show().animate(
      {
        "bottom":"0",
        "background-color":"rgba(201,135,2,0.7)"
      }
    );

    var bar = $(".social-bar",$(this));
    bar.show().animate({"top":"5px"});

  });

  $(".box").on("mouseleave",function(){
    var slider = $(".slider",$(this));

    // TODO: http://jqueryui.com/switchClass/
    slider.animate(
      {
        "bottom":"-40px",
        "background-color":"rgba(0, 0, 0, 0.7)"
      }
    );

    var bar = $(".social-bar",$(this));
    bar.show().animate({"top":"-60px"});
  });
}

/* City selection listener */
var cities = {
  "NY": "New York",
  "HI": "Hawaii",
  "LO": "London"
}
function city_selection_listener(){
  $("#city-selection-select").on("change",function(){
    var val = $("#city-selection-select").val();

    if (val == ""){
      return;
    }

    $("#city-placeholder").html(cities[val]);

    $("#city-selection").fadeOut(function(){
        $("#city-selected").fadeIn();
    });
  });

  $("#activate-city-selection").click(function(){
    $("#city-selection-select").val("");
    $("#city-selected").fadeOut(function(){
      $("#city-selection").fadeIn();
    });
  });
}

/* Tag listener */
function tag_listener() {
  $(".remove-tag").on("click",function(){
    var parent = $(this).parent();
    parent.fadeOut(500, function() {
      parent.remove();
      filter_and_reload_items();
    });
  });
}

/* Item refresher */
function filter_and_reload_items() {
  var container = $("#container");
  container.html(shuffle($("#container > div")));
  container.masonry("reload");
  setup_listeners();
}

/* Modal button listener */
function modal_buttons_listener() {
  $(".modal-buttons").on("click",function(){
    var b = $(this);
    var type = b.attr("class").match(/modal-button-(.*)\b/)[1];
    var row = b.parents(".row-fluid");

    $(".modal-main-content > div",row).hide();
    $(".modal-main-content > .modal-show-" + type,row).show();
  });
}

function setup_listeners() {
  item_hover_listener();
  city_selection_listener();
  tag_listener();
  modal_buttons_listener();
}

/* Document Ready */
$(document).ready(function(){
  setup_listeners();
});
