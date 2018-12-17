<template>
  <div class="content">
    <div class="md-layout">
      <div class="md-layout-item md-medium-size-100 md-xsmall-size-100 md-size-100">
        <chart-most-popular-books/>
      </div>
    </div>
  </div>
</template>

<script>
import ChartMostPopularBooks from "@/components/Charts/chart-most-popular-books";

export default {
  components: {
    ChartMostPopularBooks
  }
};
</script>
