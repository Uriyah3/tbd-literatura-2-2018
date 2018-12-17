<template>
  <div class="content">
    <div class="md-layout">
      <div class="md-layout-item md-medium-size-100 md-xsmall-size-100 md-size-100">
        <chart-most-popular-genres />
      </div>
    </div>
  </div>
</template>

<script>
import ChartMostPopularGenres from "@/components/Charts/chart-most-popular-genres";

export default {
  components: {
    ChartMostPopularGenres,
  }
};
</script>
