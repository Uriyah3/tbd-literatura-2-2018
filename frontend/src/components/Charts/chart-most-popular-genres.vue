<template>
<div>
  <h2 class="text-center" style="padding-bottom:0;">Géneros más populares</h2>

  <div class="card" style="margin-top:-50px;">
    <chartjs-pie v-bind:labels="labels"
      v-bind:datasets="datasets"
      v-bind:option="option"
      v-bind:bind="true"/>
  </div>
</div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      labels: [],
      datasets: [
        {
          data: [],
          backgroundColor: ["#F2A238","#DD9333","#C8832E","#B47429","#9F6523","#8C571E","#784919","#653C14","#52300F","#402309"]
        }
      ],
      option: {
        title: {
          display: true/*,
          position: "bottom",
          text: "Libros"*/
        }
      },
    };
  },
  mounted() {
    axios({ method: "GET", "url": "http://192.168.0.21:8082/genre/top" }).then(result => {
      this.labels = result.data.labels;
      this.datasets[0].data = result.data.data;
      var hits = 0;
      for(var i = 0; i < 9; i++) {
        hits += this.datasets[0].data[i];
      }
      for(var i = 0; i < 9; i++) {
        this.labels[i] += " " + (this.datasets[0].data[i] / hits * 100).toFixed(2) + "%";
      }
    }, error => {
      console.error(error);
    });
  }
};
</script>
