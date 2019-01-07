<style src="vue-d3-network/dist/vue-d3-network.css"></style>
<style>

.links line {
  stroke: #999;
  stroke-opacity: 0.6;
}

.nodes circle {
  stroke: #fff;
  stroke-width: 1.5px;
}


.node:hover circle {
  fill: #000;
}

.node:hover text {
  display: inline;
}

.cell {
  fill: none;
  pointer-events: all;
}

text {
  font-family: sans-serif;
  font-size: 10px;
}

</style>

<template><svg width="960" height="600"></svg></template>

<script>
export default {
  mounted() {
    var svg = d3.select("svg"),
    width = +svg.attr("width"),
    height = +svg.attr("height");
    
var radius = 8; 

d3.json("http://192.168.0.21:8082/neo4j/graph?limit=2000", function(error, json) {
  console.log(json);
  if (error) throw error;

//set up the simulation and add forces  
var simulation = d3.forceSimulation()
          .nodes(json.nodes);
                              
var link_force =  d3.forceLink(json.links)
                        .id(function(d) { return d.id; });            
         
var charge_force = d3.forceManyBody()
    .strength(-50); 
    
var center_force = d3.forceCenter(width / 2, height / 2);  
                      
simulation
    .force("charge_force", charge_force)
    .force("center_force", center_force)
    .force("links",link_force)
    .force('link', d3.forceLink().links(json.links))
    .force('collision', d3.forceCollide().radius(function(d) {
    if(d._size) return d._size; else return radius;;
  }))
 ;

        
//add tick instructions: 
simulation.on("tick", tickActions );

//add encompassing group for the zoom 
var g = svg.append("g")
    .attr("class", "everything");

//draw lines for the links 
var link = g.append("g")
      .attr("class", "links")
    .selectAll("line")
    .data(json.links)
    .enter().append("line")
      .attr("stroke-width", 2)
      .style("stroke", linkColour);        

//draw circles for the nodes 
var node = g.append("g")
        .attr("class", "nodes") 
        .selectAll("circle")
        .data(json.nodes)
        .enter().append("g")
      .attr("class", "node")
        .append("circle")
        .attr("r", function(d) { if(d._size) return d._size; else return radius; })
        .attr("fill", circleColour);

var label = node.append("text")
      .attr("dy", ".35em")
      .text(function(d) { return d.id; });
 
 
//add drag capabilities  
var drag_handler = d3.drag()
  .on("start", drag_start)
  .on("drag", drag_drag)
  .on("end", drag_end); 
  
drag_handler(node);


//add zoom capabilities 
var zoom_handler = d3.zoom()
    .on("zoom", zoom_actions);

zoom_handler(svg);

/** Functions **/

//Function to choose what color circle we have
//Let's return blue for males and red for females
function circleColour(d){
  if(d.label =="user"){
    return "blue";
  } else if(d.label == "book") {
    return "#999900";
  } else {
    return "orange";
  }
}

//Function to choose the line colour and thickness 
//If the link type is "A" return green 
//If the link type is "E" return red 
function linkColour(d){
  if(d.type == "A"){
    return "green";
  } else if(d.typ == "B") {
    return "red";
  } else {
    return "gray";
  }
}

//Drag functions 
//d is the node 
function drag_start(d) {
 if (!d3.event.active) simulation.alphaTarget(0.3).restart();
    d.fx = d.x;
    d.fy = d.y;
}

//make sure you can't drag the circle outside the box
function drag_drag(d) {
  d.fx = d3.event.x;
  d.fy = d3.event.y;
}

function drag_end(d) {
  if (!d3.event.active) simulation.alphaTarget(0);
  d.fx = null;
  d.fy = null;
}

//Zoom functions 
function zoom_actions(){
    g.attr("transform", d3.event.transform)
}

function tickActions() {
    //update circle positions each tick of the simulation 
       node
        .attr("cx", function(d) { return d.x; })
        .attr("cy", function(d) { return d.y; });
        
    //update link positions 
    link
        .attr("x1", function(d) { return d.source.x; })
        .attr("y1", function(d) { return d.source.y; })
        .attr("x2", function(d) { return d.target.x; })
        .attr("y2", function(d) { return d.target.y; });

    label
        .attr("x", function(d) { return d.x + 8; })
        .attr("y", function(d) { return d.y; });
}  
});


}
};

</script>