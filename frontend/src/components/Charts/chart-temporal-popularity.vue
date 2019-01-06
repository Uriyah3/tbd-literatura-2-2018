<template>
<div>
  <h2 class="text-center" style="padding-bottom:0;margin-top:20px;margin-bottom:0;">Popularidad de la literatura últimamente</h2>

  <div class="card">
    <header>
      <chartjs-line v-bind:beginzero="beginZero"
        v-bind:labels="labels"
        v-bind:datalabel="dataLabel"
        v-bind:data="data"
        v-bind:option="option"
        v-bind:bind="true"/>
    </header>
  </div>
</div>
</template>

<script>
import axios from "axios";

export default {
/*
  data() {
    return {
      beginZero: true,
      labels: {
        day: [8, 10, 12, 14, 16],
        week: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"]
      },
      dataLabel: "Foo",
      radio: "day",
      data: {
        day: [1, 3, 5, 3, 1],
        week: [12, 14, 16, 18, 11, 13, 15]
      },
      bgColor: "#ff8a80",
      borderColor: "#f44336"
    };
  }
};
*/
  data() {
    return {
      beginZero: true,
      labels: [],
      data: [],
      dataLabel: "Popularidad de libros y autores",
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
    axios({ method: "GET", "url": "http://192.168.0.21:8082/elasticsearch/getDate" }).then(result => {
      this.labels = result.data.labels;
      this.data = result.data.data;
    }, error => {
      console.error(error);
    });
  }
};
</script>
