<template>
  <div class="content">
    <div class="md-layout">
      <div class="md-layout-item md-medium-size-100 md-xsmall-size-100 md-size-100">
        <chart-most-popular-authors/>
      </div>
    </div>
  </div>
</template>

<script>
import ChartMostPopularAuthors from "@/components/Charts/chart-most-popular-authors";

export default {
  components: {
    ChartMostPopularAuthors
  }
};
</script>
