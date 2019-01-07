<template>
  <div>
    <h2 class="text-center" style="padding-bottom:0; margin-bottom:0px">{{ title }}</h2>
    <div class="card" style="margin-top:-40px;">
      <chartjs-doughnut v-bind:labels="labels"
      v-bind:datasets="datasets"
      v-bind:option="option"
      v-bind:bind="true"/>
    </div>
  </div>
</template>

<script>
  import axios from "axios";

  export default {
    data () {
      return {
        labels: ["Positivo", "Negativo", "Neutro"],
        datasets: [
          {
            data: [],
            backgroundColor: ["green","red","gray"]
          }
        ],
        option: {
          title: {
            display: true/*,
            position: "bottom",
            text: "Libros"*/
          },
          maintainAspectRatio: false
        },
        title: "",
      }
    },
    mounted() {
      axios({ method: "GET", "url": "http://192.168.0.21:8082/book/" + this.$route.params.id }).then(result => {
        this.datasets[0].data = [result.data.positivo, result.data.negativo, result.data.neutro];
        var hits = result.data.positivo + result.data.negativo + result.data.neutro;
        this.labels[0] += " " + (result.data.positivo / hits * 100).toFixed(2) + "%";
        this.labels[1] += " " + (result.data.negativo / hits * 100).toFixed(2) + "%";
        this.labels[2] += " " + (result.data.neutro / hits * 100).toFixed(2) + "%";
        this.title = result.data.name;
      }, error => {
        console.error(error);
      });
    }
  }
</script>
