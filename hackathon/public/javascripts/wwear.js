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
function city_selection_listener(){
  $("#city-selection-select").change(function(){
    $("#city-selection").animate({
      "opacity":"0",
      "display":"none",
      "height": "206px"
    },3000,
    function (){
      $("#city-selected").fadeIn(3000);
    });

    // filter_and_reload_items();
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
  item_hover_listener();
}

/* Document Ready */
$(document).ready(function(){
  item_hover_listener();
  city_selection_listener();
  tag_listener();
});
