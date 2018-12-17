<template>
<div>
  <h2 class="text-center">Los 10 libros más populares</h2>

  <div class="card">
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
          backgroundColor: ["#79A8D8","#46A7C4","#19A2A9","#169B8A","#33926A","#4B874E","#5D7A36","#6B6D27","#755E21","#7A5023"]
        }
      ],
      option: {
        title: {
          display: true/*,
          position: "bottom",
          text: "Libros"*/
        }
      }
    };
  },
  mounted() {
    axios({ method: "GET", "url": "http://localhost:8082/book/top10" }).then(result => {
      this.labels = result.data.labels;
      this.datasets[0].data = result.data.data;
      console.log(result);
    }, error => {
      console.error(error);
    });
  }
};
</script>
