<template>
  <div class="content">
    <div class="md-layout">
      <div class="md-layout-item md-medium-size-100 md-xsmall-size-100 md-size-100">
        <chart-temporal-popularity/>
      </div>
    </div>
  </div>
</template>

<script>
import chartTemporalPopularity from "@/components/Charts/chart-temporal-popularity";

export default {
  components: {
    chartTemporalPopularity
  }
};
</script>
