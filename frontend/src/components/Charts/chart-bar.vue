<template>
  <div>
    <h2>Bar</h2>

    <div class="card">
      <canvas id="fooCanvas" count="1"></canvas>

      <chartjs-bar v-for="(item, index) in types"
      v-bind:key="index"
      target="fooCanvas"
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
        types: [
        {
          dataLabel: "Los 10 autores más populares",
          data: [],
          bgColor: "#79A8D8",
          borderColor: "#19A2A9"
        }
        ]
      }
    },
    mounted() {
      axios({ method: "GET", "url": "http://localhost:8082/author/top" }).then(result => {
        this.labels = result.data.labels.slice(0,10);
        this.types[0].data = result.data.data.slice(0,10);
        console.log(result);
      }, error => {
        console.error(error);
      });
    },
    methods: { }
  }
</script>
