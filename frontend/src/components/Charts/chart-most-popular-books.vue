<template>
<div>
  <h2 class="text-center" style="padding-bottom:0;">Los 10 libros más populares</h2>

  <div class="card" style="margin-top:-20px;">
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
          backgroundColor: ["#3ECCF6","#39BAE0","#34A8CB","#2E96B6","#2985A2","#23748E","#1E647A","#185467","#134455","#0D3543"]
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
    axios({ method: "GET", "url": "http://localhost:8082/book/top" }).then(result => {
      this.labels = result.data.labels.slice(1,11);
      this.datasets[0].data = result.data.data.slice(1,11);
    }, error => {
      console.error(error);
    });
  }
};
</script>
