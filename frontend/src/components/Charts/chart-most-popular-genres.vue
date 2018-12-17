<template>
<div>
  <h2 class="text-center" style="padding-bottom:0;">Géneros más populares</h2>

  <div class="card" style="margin-top:-10px;">
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
          backgroundColor: ["#EC9047","#D88440","#C4773A","#B16B33","#9E5F2D","#8C5426","#7A4820","#683D1A","#573314","#47280F"]
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
    axios({ method: "GET", "url": "http://localhost:8082/genre/top" }).then(result => {
      this.labels = result.data.labels;
      this.datasets[0].data = result.data.data;
    }, error => {
      console.error(error);
    });
  }
};
</script>
