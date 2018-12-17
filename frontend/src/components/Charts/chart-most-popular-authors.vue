<template>
  <div>
    <h2 class="text-center">Los 10 autores más populares</h2>

    <div class="card">
      <chartjs-bar
      v-bind:beginzero="beginZero"
      v-bind:labels="labels"
      v-bind:datalabel="item.dataLabel"
      v-bind:data="item.data"
      v-bind:backgroundcolor="item.bgColor"
      v-bind:bordercolor="item.borderColor"
      v-bind:bind="true" />
    </div>
  </div>
</template>

<script>
  import axios from "axios";

  export default {
    data () {
      return {
        beginZero: true,
        labels: [],
        item:
        {
          dataLabel: "Los 10 autores más populares",
          data: [],
          bgColor: "#2985A2",
          borderColor: "#0D3543"
        }
      }
    },
    mounted() {
      axios({ method: "GET", "url": "http://localhost:8082/author/top" }).then(result => {
        this.labels = result.data.labels.slice(0,10);
        this.item.data = result.data.data.slice(0,10);
      }, error => {
        console.error(error);
      });
    }
  }
</script>
