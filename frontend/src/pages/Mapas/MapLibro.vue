<template>
  <div class="content">
    <div id="map"></div>
  </div>
</template>
<style>
#map { height: 90vh !important; margin-top:15px; border:1px solid gray;}
svg {
  width: auto;
  height: auto;
}
.info {
    padding: 6px 8px;
    font: 14px/16px Arial, Helvetica, sans-serif;
    background: white;
    background: rgba(255,255,255,0.8);
    box-shadow: 0 0 15px rgba(0,0,0,0.2);
    border-radius: 5px;
}
.info h4 {
    margin: 0 0 5px;
    color: #777;
}
.legend {
    line-height: 18px;
    color: #555;
}
.legend i {
    width: 18px;
    height: 18px;
    float: left;
    margin-right: 8px;
    opacity: 0.7;
}
</style>
<script>
import TopNavbar from "@/components/TopNavbar/NavLibro";
import * as d3 from 'd3';
import L from 'leaflet';
require('leaflet/dist/leaflet.css');
import chileData from './../geo-hispana.json'; //solo como ejemplo, datos deben ser obtenidos desde servicio rest
export default{
  components: {
    TopNavbar
  },
  data(){
    return {
    }
  },
  mounted:function(){
    let colors =  ["#ffffd9", "#edf8b1", "#c7e9b4", "#7fcdbb", "#41b6c4", "#1d91c0", "#225ea8", "#253494", "#081d58"] ;
    //rango de la población
    let extent = d3.extent(chileData.features,d=>d.properties.density );
    //escala lineal
    let colorScaleLinear = d3.scaleLinear()
    .domain(extent)
    .range([colors[0], colors[colors.length-1]]);

    function getColor(d) {
      return colorScaleLinear(d);
    }

    function style(feature) {
      return {
          fillColor: getColor(feature.properties.density),
          weight: 2,
          opacity: 1,
          color: 'white',
          dashArray: '3',
          fillOpacity: 0.7
      };
    }

    function highlightFeature(e) {
      var layer = e.target;

      layer.setStyle({
          weight: 5,
          color: '#666',
          dashArray: '',
          fillOpacity: 0.7
      });

      if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
          layer.bringToFront();
      }
      info.update(layer.feature.properties);
    }

    function resetHighlight(e) {
      geojson.resetStyle(e.target);
      info.update();
    }

    var geojson;

    function zoomToFeature(e) {
      map.fitBounds(e.target.getBounds());
    }

    function onEachFeature(feature, layer) {
      layer.on({
          mouseover: highlightFeature,
          mouseout: resetHighlight,
          click: zoomToFeature
      });
    }


    var map = L.map('map').setView([-4, -60], 3);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png ', {attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'}).addTo(map);
    geojson = L.geoJson(chileData, {
        style: style,
        onEachFeature: onEachFeature
    }).addTo(map);

    var info = L.control();

    info.onAdd = function (map) {
        this._div = L.DomUtil.create('div', 'info'); // create a div with a class "info"
        this.update();
        return this._div;
    };

    // method that we will use to update the control based on feature properties passed
    info.update = function (props) {
        this._div.innerHTML = '<h4>Popularidad de la literatura</h4>' +  (props ?
            '<b style="font-size:1.2em;">' + props.name + '</b><br />' +
            '<b>Libro más popular: </b>' + props.libro + '<br />' +
            '<b>Autor/a más popular: </b>' + props.autor + '<br />' +
            '<b>Género más popular: </b>' + props.genero + '<br />' +
            '<b>Comentarios: </b>' + props.hits + '<br />' +
            '<b>Índice popularidad: </b>' + props.density + '<br />'
            : 'Coloque el puntero sobre un país');
    };

    info.addTo(map);



    var legend = L.control({position: 'bottomright'});

    legend.onAdd = function (map) {

        var div = L.DomUtil.create('div', 'info legend'),
            grades = [0, Math.round(extent[1]/9*1), Math.round(extent[1]/9*2), Math.round(extent[1]/9*3), Math.round(extent[1]/9*4), Math.round(extent[1]/9*5), Math.round(extent[1]/9*6), Math.round(extent[1]/9*7), Math.round(extent[1]/9*8)],
            labels = [];

        // loop through our density intervals and generate a label with a colored square for each interval
        for (var i = 0; i < grades.length; i++) {
            div.innerHTML +=
                '<i style="background:' + colors[i] + '"></i> ' +
                grades[i] + (grades[i + 1] ? '&ndash;' + grades[i + 1] + '<br>' : '+');
        }

        return div;
    };

    legend.addTo(map);

  }
}
</script>