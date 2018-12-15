<template>
  <div class="content">
    <div class="md-layout">
      <div class="md-layout-item md-medium-size-100 md-xsmall-size-100 md-size-50">
        <chartjs-line/>
      </div>
      <div class="md-layout-item md-medium-size-100 md-xsmall-size-100 md-size-50">
        <chart-doughnut/>
      </div>
      <div class="md-layout-item md-medium-size-100 md-xsmall-size-100 md-size-50">
        <chart-bar/>
      </div>
      <div class="md-layout-item md-medium-size-100 md-xsmall-size-100 md-size-50">
        <chart-line/>
      </div>
    </div>
  </div>
</template>

<script>
import ChartLine from "@/components/Charts/chart-line";
import ChartBar from "@/components/Charts/chart-bar";
import ChartDoughnut from "@/components/Charts/chart-doughnut";

export default {
  components: {
    ChartDoughnut,
    ChartLine,
    ChartBar
  }
};
</script>
