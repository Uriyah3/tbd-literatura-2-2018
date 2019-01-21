<template>
  <div class="content">
    <top-navbar/>
    <div id="map"></div>
  </div>
</template>
<style>
#map { height: 78vh !important;}
svg {
  width: auto;
  height: auto;
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

    var map = L.map('map').setView([2, -60], 3);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png ', {attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'}).addTo(map);
    L.geoJson(chileData, {style: style}).addTo(map);
  }
}
</script>