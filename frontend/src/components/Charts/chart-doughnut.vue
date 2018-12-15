<template>
<div>
  <h2 class="text-center">Doughnut</h2>

  <div class="card">
    <chartjs-pie v-bind:labels="labels"
      v-bind:datasets="datasets"
      v-bind:option="option"
      v-bind:bind="true"/>
  </div>
</div>
</template>

<script>
export default {
  data() {
    return {
      labels: ["Gabriel García", "Pablo Coelho", "Pablo Neruda"],
      datasets: [
        {
          data: [100, 20, 500],
          backgroundColor: ["Blue", "Orange", "Green"]
        }
      ],
      option: {
        title: {
          display: true,
          position: "bottom",
          text: "Libros"
        }
      },
    };
  }
};
</script>
