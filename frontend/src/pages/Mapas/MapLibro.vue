<template>
  <top-navbar/>
</template>
<!--



<template>
  <div>
    <h1>Mapa coroplético</h1>
    <div id="map"></div>
  </div>
</template>
<style>
#map { height: 600px; width: 300px;}
</style>
<script>
/*
* Mapa coroplético basado en la población de las regiones de Chile
 incluyendo tres funciones distintas de creación de escala de colores
* Basado en https://leafletjs.com/examples/choropleth/
* Explicación de escalas en https://roadtolarissa.com/coloring-maps/
* Colores obtenidos de https://bl.ocks.org/emeeks/8cdec64ed6daf955830fa723252a4ab3
* Archivos GeoJson tienen esta estructura:
  {
    "type": "Feature",
    "properties": {
        "name": "Alabama",
        "density": 94.65
    },
    "geometry": ...
    ...
  }
*/
import L from 'leaflet';
import * as d3 from 'd3';
require('leaflet/dist/leaflet.css'); //css se configua en webpack.config con { test: /\.css$/, loader: "style-loader!css-loader" }
// Datos geográficos de chile desde https://github.com/jlhonora/geo
//import chileData from './geo-chile.json'; //solo como ejemplo, datos deben ser obtenidos desde servicio rest
export default{
  data(){
    return {
    }
  },
  mounted:function(){
    let colors =  ["#ffffcc","#a1dab4","#41b6c4","#2c7fb8","#253494"] ;
    //rango de la población
    let extent = d3.extent(chileData.features,d=>d.properties.POBL2010 );
    //escala lineal
    let colorScaleLinear = d3.scaleLinear()
    .domain(extent)
    .range([colors[0], colors[colors.length-1]]);
    //Cuantiles sobre el valor
    let colorScaleQuantize = d3.scaleQuantize()
    .domain(extent)
    .range(colors);
    //Cuantiles sobre el ranking
    let colorScaleQuantile = d3.scaleQuantile()
    .domain(chileData.features.map(d=>d.properties.POBL2010))
    .range(colors);
  function style(feature) {
    return {
        fillColor: colorScaleLinear(feature.properties.POBL2010),
        weight: 2,
        opacity: 1,
        color: 'white',
        fillOpacity: 0.9
    };
}
    var map = L.map('map').setView([-33, -70], 4);
    L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
    }).addTo(map);
    L.geoJson(chileData, {style: style}).addTo(map);
  }
}
</script>