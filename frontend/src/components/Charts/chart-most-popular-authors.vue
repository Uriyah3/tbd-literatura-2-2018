<template>
<div>
  <h2 class="text-center" style="padding-bottom:0;">Autores más populares</h2>

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
          backgroundColor: ["#C3F06D","#AADA66","#93C45E","#7DAF56","#68994D","#558444","#44703B","#345C31","#264827","#19361D"]
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
    axios({ method: "GET", "url": "http://192.168.0.21:8082/author/top" }).then(result => {
      this.labels = result.data.labels.slice(0,10);
      this.datasets[0].data = result.data.data.slice(0,10);
    }, error => {
      console.error(error);
    });
  }
};
</script>
