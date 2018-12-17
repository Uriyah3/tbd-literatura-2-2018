<template>
  <div class="content">
    <div class="md-layout">
      <div class="md-layout-item md-medium-size-100 md-xsmall-size-100 md-size-100">
        <chart-pie/>
      </div>
      <div class="md-layout-item md-medium-size-100 md-xsmall-size-100 md-size-100">
        <chart-bar/>
      </div>
    </div>
  </div>
</template>

<script>
import ChartLine from "@/components/Charts/chart-line";
import ChartBar from "@/components/Charts/chart-bar";
import ChartPie from "@/components/Charts/chart-pie";

export default {
  components: {
    ChartPie,
    ChartLine,
    ChartBar
  }
};
</script>
